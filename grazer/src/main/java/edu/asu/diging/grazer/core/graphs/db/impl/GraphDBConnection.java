package edu.asu.diging.grazer.core.graphs.db.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.print.DocFlavor.STRING;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
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
        return query.list();
    }
    
    @Override
    public List<String> getAllPersons() {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT g.conceptUri from Graph g");
        return query.list();
    }
    
    @Override
    public List<Graph> getNonPeopleGraphs(String conceptUri) {
        //Query query = sessionFactory.getCurrentSession().createQuery("SELECT g from Graph g WHERE g.conceptUri IN SELECT n1.uri from Node n1 WHERE n1.id IN SELECT e.source from Edge e INNER JOIN Node n WHERE n.uri = :uri AND n.dbId=e.targetNode");
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT g from Graph g JOIN g.nodes n WHERE n.uri = :uri");
        query.setParameter("uri", conceptUri);
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
