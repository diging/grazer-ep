package edu.asu.diging.grazer.core.rdf.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.rdf.IUriCreator;
import edu.asu.diging.grazer.web.rdf.ConceptAPI;

@Service
@PropertySource("classpath:/config.properties")
public class UriCreator implements IUriCreator {

    @Autowired
    private Environment env;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.rdf.impl.IUriCreator#getUri(java.lang.String)
     */
    @Override
    public String getUri(String conceptId) {
        String prefix = env.getProperty("grazer.base.url");
        String uri = prefix + ConceptAPI.CONCEPT_PREFIX.replace(ConceptAPI.ID_PLACEHOLDER, conceptId);
        return uri;        
    }
    
    @Override
    public String getContextUri(String id) {
        String prefix = env.getProperty("grazer.base.url");
        return prefix + "context/" + id;
    }
    
    @Override
    public String getBaseUri() {
        return env.getProperty("grazer.base.url");
    }
}
