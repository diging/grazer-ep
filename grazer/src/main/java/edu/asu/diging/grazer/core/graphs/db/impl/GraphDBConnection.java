package edu.asu.diging.grazer.core.graphs.db.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.print.DocFlavor.STRING;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Graph;

@Transactional
@Service
public class GraphDBConnection implements IGraphDBConnection {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected SessionFactory sessionFactory;

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.db.impl.IGraphDBConnection#store(edu.asu.diging.grazer.core.model.impl.Graph)
     */
    @Override
    public void store(Graph graph) {
        sessionFactory.getCurrentSession().save(graph);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.db.impl.IGraphDBConnection#getGraphs(java.lang.String)
     */
    @Override
    public List<Graph> getGraphs(String conceptUri) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT g from Graph g WHERE g.conceptUri = :uri");
        query.setParameter("uri", conceptUri);
        List<Graph> results = query.list();
        
        if(results.isEmpty()) {
            query = sessionFactory.getCurrentSession().createQuery("SELECT g from Graph g JOIN g.nodes n WHERE n.uri = :uri");
            query.setParameter("uri", conceptUri);
            results = query.list();
        }
        return results;
    }
    
    @Override
    public List<Edge> getEdges(String conceptUri) {
        
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Edge.class, "edge");
        c.createAlias("edge.sourceNode", "sourceNode");
        c.createAlias("edge.targetNode", "targetNode");
        c.add(Restrictions.disjunction().add(Restrictions.eq("targetNode.uri", conceptUri))
                .add(Restrictions.like("sourceNode.uri", conceptUri)));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("source"), "source")
                .add(Projections.property("target"), "target")
                .add(Projections.property("label"), "label")
                .add(Projections.property("concept"), "concept")
                .add(Projections.property("sourceUri"), "sourceUri")
                .add(Projections.property("startTime"), "startTime")
                .add(Projections.property("endTime"), "endTime")
                .add(Projections.property("occurred"), "occurred")
                .add(Projections.property("sourceNode"), "sourceNode")
                .add(Projections.property("targetNode"), "targetNode");
        c.setProjection(Projections.distinct(projList));
        c.setResultTransformer(Transformers.aliasToBean(Edge.class));
        return c.list();
        /*List<Edge> results ;
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT e from Graph g JOIN g.edges e JOIN g.nodes n WHERE n.uri = :uri and (n.id=source or n.id=target)");
        //select e.* from tbl_conceptpower_alternativeuris a INNER JOIN Edge e INNER JOIN Node n where a.id='http://www.digitalhps.org/concepts/WID-13512725-N-01-meiosis' and (n.id=e.source or n.id=e.target) and a.alternativeUris=n.uri;
        query.setParameter("uri", conceptUri);
        results = query.list();
        return results;*/
    }
    
    @Override
    public List<String> getAllPersons() {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT g.conceptUri from Graph g");
        return query.list();
    }
    
    @Override
    public void removeGraphs(String conceptUri) {
        List<Graph> graphs = getGraphs(conceptUri);
        if (graphs == null) {
            return;
        }
        for (Graph graph : graphs) {
            sessionFactory.getCurrentSession().delete(graph);
        }
    }
}
