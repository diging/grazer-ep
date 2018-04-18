package edu.asu.diging.grazer.core.amphora.impl;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.amphora.IMappingDBConnector;
import edu.asu.diging.grazer.core.model.impl.UriMapping;

@Service
@Transactional
public class MappingDBConnector implements IMappingDBConnector {

    @Autowired
    protected SessionFactory sessionFactory;

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.amphora.impl.IMappingDBConnector#store(edu.asu.diging.grazer.core.model.impl.UriMapping)
     */
    @Override
    public void store(UriMapping mapping) {
        sessionFactory.getCurrentSession().save(mapping);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.amphora.impl.IMappingDBConnector#get(java.lang.String)
     */
    @Override
    public UriMapping get(String uri) {
        return sessionFactory.getCurrentSession().get(UriMapping.class, uri);
    }
}
