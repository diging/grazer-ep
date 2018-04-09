package edu.asu.diging.grazer.core.amphora.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.amphora.IAmphoraConnector;
import edu.asu.diging.grazer.core.amphora.IAmphoraService;
import edu.asu.diging.grazer.core.exception.AmphoraException;
import edu.asu.diging.grazer.core.model.impl.UriMapping;

/**
 * Class to connect to Amphora using the DB as a cache to store data retrieved from Amphora
 * that won't change to reduce load times.
 * 
 * @author jdamerow
 *
 */
@Service
public class AmphoraService implements IAmphoraService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAmphoraConnector amphoraConnector;
    
    @Autowired
    private MappingDBConnector dbConnector;
    
    /**
     * This method first checks the DB if a mapped URI has already been stored
     *  for the passed URI. If so, it returns it, otherwise it'll request the 
     *  mapped URI from Amphor.
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
