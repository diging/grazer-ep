package edu.asu.diging.grazer.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.diging.grazer.core.document.Document;
import edu.asu.diging.grazer.core.document.impl.IDocument;

@Component
public class DocumentDAO {

    @Autowired
    protected SessionFactory sessionFactory;
    
    public void update(IDocument doc) {
        sessionFactory.getCurrentSession().merge(doc);
    }
    
    public void store(IDocument doc) {
        sessionFactory.getCurrentSession().persist(doc);
        sessionFactory.getCurrentSession().flush();
    }
    
    public IDocument get(String id) {
        return sessionFactory.getCurrentSession().get(Document.class, id);
    }
    
    public void delete(String id) {
        Document doc = sessionFactory.getCurrentSession().get(Document.class, id);
        if(doc != null) {
            sessionFactory.getCurrentSession().delete(doc);
        }
    }
    
    public List<IDocument> getAllDocuments() {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("SELECT d FROM Document d", IDocument.class);
        return query.getResultList();
    }
}
