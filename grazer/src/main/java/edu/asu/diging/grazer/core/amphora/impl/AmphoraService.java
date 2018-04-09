package edu.asu.diging.grazer.core.amphora.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.amphora.IAmphoraConnector;
import edu.asu.diging.grazer.core.amphora.IAmphoraService;
import edu.asu.diging.grazer.core.exception.AmphoraException;
import edu.asu.diging.grazer.core.model.impl.UriMapping;

@Service
public class AmphoraService implements IAmphoraService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAmphoraConnector amphoraConnector;
    
    @Autowired
    private MappingDBConnector dbConnector;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.amphora.impl.IAmphoraService#getMappedUri(java.lang.String)
     */
    @Override
    public String getMappedUri(String uri) {
        UriMapping uriMapping = dbConnector.get(uri);
        if (uriMapping != null) {
            return uriMapping.getMappedUri();
        }
        
        String mappedUri;
        try {
            mappedUri = amphoraConnector.getURIofParentResource(uri);
        } catch (AmphoraException e) {
            logger.error("Could not retrieve metadata for: " + uri, e);
            return null;
        }
        if (mappedUri != null) {
            dbConnector.store(new UriMapping(uri, mappedUri));
            return mappedUri;
        }
        
        return null;
    }
}
