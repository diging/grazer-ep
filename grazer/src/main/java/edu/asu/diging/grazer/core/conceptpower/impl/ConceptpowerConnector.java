package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.model.IConcept;

@Service
@PropertySource("classpath:config.properties")
public class ConceptpowerConnector implements IConceptpowerConnector {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ConceptMapper conceptMapper;
    
    @Autowired
    private IConceptDatabaseConnection conceptDB;

    @Value("${conceptpower.url}")
    private String conceptpowerUrl;

    @Value("${conceptpower.concept.endpoint}")
    private String conceptEndpoint;

    @Value("${conceptpower.search.endpoint}")
    private String searchEndpoint;
    
    private RestTemplate restTemplate;

    public ConceptpowerConnector() {
        restTemplate = new RestTemplate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.grazer.core.conceptpower.impl.IConceptpowerConnector#
     * getConcept(java.lang.String)
     */
    @Override
    @Cacheable(value = "concepts")
    public IConcept getConcept(String id) {
    	
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        HttpEntity<?> entity = new HttpEntity<Object>(requestHeaders);
        ResponseEntity<ConceptpowerConcepts> response;

        try {
            response = restTemplate.exchange(String.format("%s%s%s", conceptpowerUrl, conceptEndpoint, id),HttpMethod.GET, entity, ConceptpowerConcepts.class);
        } catch(HttpClientErrorException ex) {
            logger.error("Could not retrieve concepts from Conceptpower.", ex);
            return null;
        }
        
        try {
            ConceptpowerConcepts concepts = response.getBody();
            if (concepts.getConceptEntries() != null && !concepts.getConceptEntries().isEmpty()) {
                ConceptpowerConcept cpc = concepts.getConceptEntries().get(0);
                return conceptMapper.mapConceptpowerConceptToConcept(cpc);
            }
        } catch(NullPointerException ex) {
            logger.error("Could not find any concepts", ex);
        }
        return null;
    }
    
    @Override
    @Cacheable(value = "concepts")
    public List<ConceptpowerConcept> search(String item) {
        
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        HttpEntity<?> entity = new HttpEntity<Object>(requestHeaders);

        ResponseEntity<ConceptpowerConcepts> response;
        try {
            response = restTemplate.exchange(conceptpowerUrl + searchEndpoint + item, HttpMethod.GET, entity, ConceptpowerConcepts.class);
        } catch(HttpClientErrorException ex) {
            logger.error("Could not retrieve concepts from Conceptpower.", ex);
            return null;
        }
        
        List<ConceptpowerConcept> conceptList;
        List<ConceptpowerConcept> results = new ArrayList<>();
        
        try {
            conceptList = response.getBody().getConceptEntries();  
            for (ConceptpowerConcept result : conceptList) {
                if(conceptDB.getConcept(result.getId()) != null) {
                    results.add(result);
                } 
            } 
            return results;
            
        } catch(NullPointerException ex) {
            logger.error("Could not find any concepts", ex);
        }
        return null;
    }
}
