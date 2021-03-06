package edu.asu.diging.grazer.core.conceptpower.db.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.IConceptType;
import edu.asu.diging.grazer.core.model.impl.Concept;
import edu.asu.diging.grazer.core.model.impl.ConceptType;

@Component
@Transactional
public class ConceptDatabaseConnection implements IConceptDatabaseConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public IConcept getConcept(String id) {

        Object objConcept = null;
        Query query = sessionFactory.getCurrentSession()
                .createQuery("SELECT c from Concept c WHERE c.id = :id");
        query.setParameter("id", id);
        List<?> results = query.list();
        if (results != null && !results.isEmpty()) {
            // there shouldn't be more than one, but if there is just take the
            // first one
            objConcept = results.get(0);
        }

        if (objConcept == null) {
            return null;
        }

        IConcept concept = (IConcept) objConcept;
        if (concept.getTypeId() != null) {
            Object objType = sessionFactory.getCurrentSession()
                    .get(ConceptType.class, concept.getTypeId());
            if (objType != null) {
                concept.setType((IConceptType) objType);
            }
        }
        return concept;
    }

    @Override
    public IConcept getConceptByUri(String uri) {
        Object objConcept = sessionFactory.getCurrentSession()
                .get(Concept.class, uri);

        // let's check if concept uses a different main id
        if (objConcept == null) {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "SELECT c from Concept c WHERE :uri in elements(c.alternativeUris)");
            query.setParameter("uri", uri);
            List<?> results = query.list();
            if (results != null && !results.isEmpty()) {
                // there shouldn't be more than one, but if there is just take
                // the first one
                objConcept = results.get(0);
            }
        }

        if (objConcept == null) {
            return null;
        }

        IConcept concept = (IConcept) objConcept;
        if (concept.getTypeId() != null) {
            Object objType = sessionFactory.getCurrentSession()
                    .get(ConceptType.class, concept.getTypeId());
            if (objType != null) {
                concept.setType((IConceptType) objType);
            }
        }

        return concept;
    }

    @Override
    public void createOrUpdate(IConcept concept) {
        Object objConcept = sessionFactory.getCurrentSession()
                .get(Concept.class, concept.getId());
        // if concept exists, let's update it
        if (objConcept == null || isDifferent(concept, (IConcept) objConcept)) {
            logger.debug((objConcept == null ? "Adding " : "Updating: ")
                    + concept.getUri());
            concept.setLastUpdated(OffsetDateTime.now());

            if (objConcept != null) {
                sessionFactory.getCurrentSession().evict(objConcept);
            }
            sessionFactory.getCurrentSession().saveOrUpdate(concept);
            deleteConcept(concept.getId());
        }

        // update type if there is one
        if (concept.getTypeId() != null
                && !concept.getTypeId().trim().isEmpty()) {
            IConceptType type = getType(concept.getTypeId());
            if (type == null || isDifferent(concept.getType(), type)) {
                if (type != null) {
                    sessionFactory.getCurrentSession().evict(type);
                }
                if (concept.getType() != null) {
                    sessionFactory.getCurrentSession()
                            .saveOrUpdate(concept.getType());
                }
            }
        }
    }

    @Override
    public void deleteConcept(String id) {
        Object concept = sessionFactory.getCurrentSession().get(Concept.class,
                id);
        if (concept != null) {
            sessionFactory.getCurrentSession().delete(concept);
        }
    }

    @Override
    public IConceptType getType(String id) {
        Object objType = sessionFactory.getCurrentSession()
                .get(ConceptType.class, id);
        if (objType != null) {
            return (IConceptType) objType;
        }
        return null;
    }

    private boolean isDifferent(IConcept concept1, IConcept concept2) {
        if (!concept1.getAlternativeUris()
                .equals(concept2.getAlternativeUris())) {
            return true;
        }
        if (!concept1.getConceptList().equals(concept2.getConceptList())) {
            return true;
        }
        if (!concept1.getDescription().equals(concept2.getDescription())) {
            return true;
        }
        if (!concept1.getEqualTo().equals(concept2.getEqualTo())) {
            return true;
        }
        if (!concept1.getId().equals(concept2.getId())) {
            return true;
        }
        if (!concept1.getPos().equals(concept2.getPos())) {
            return true;
        }
        if (!concept1.getTypeId().equals(concept2.getTypeId())) {
            return true;
        }
        if (!concept1.getUri().equals(concept2.getUri())) {
            return true;
        }
        if (!concept1.getWord().equals(concept2.getWord())) {
            return true;
        }
        if (!concept1.getWordnetIds().equals(concept2.getWordnetIds())) {
            return true;
        }
        return false;
    }

    private boolean isDifferent(IConceptType type1, IConceptType type2) {
        if (type1 == null && type2 == null) {
            return false;
        }
        if (type1 == null || type2 == null) {
            return true;
        }
        if (!type1.getId().equals(type2.getId())) {
            return true;
        }
        if (!type1.getDescription().equals(type2.getDescription())) {
            return true;
        }
        if (!type1.getName().equals(type2.getName())) {
            return true;
        }
        if (!type1.getUri().equals(type2.getUri())) {
            return true;
        }
        return false;
    }
}
