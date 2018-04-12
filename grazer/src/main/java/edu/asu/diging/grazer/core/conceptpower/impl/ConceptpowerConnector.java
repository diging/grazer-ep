package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcepts;

@Service
@PropertySource("classpath:config.properties")
public class ConceptpowerConnector implements IConceptpowerConnector {

    private static final Logger logger = LoggerFactory.getLogger(ConceptpowerConnector.class);
    
    @Autowired
    private ConceptMapper conceptMapper;

    @Value("${conceptpower.url}")
    private String conceptpowerUrl;
    
    @Value("${searchConceptpowerEndpoint}")
    private String searchEndpoint;

    @Value("${conceptpower.concept.endpoint}")
    private String conceptEndpoint;

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
        requestHeaders.setAccept(
                Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        HttpEntity<?> entity = new HttpEntity<Object>(requestHeaders);

        ResponseEntity<ConceptpowerConcepts> response = restTemplate.exchange(
                String.format("%s%s%s", conceptpowerUrl, conceptEndpoint, id),
                HttpMethod.GET, entity, ConceptpowerConcepts.class);
        ConceptpowerConcepts concepts = response.getBody();
        if (concepts.getConceptEntries() != null
                && !concepts.getConceptEntries().isEmpty()) {
            ConceptpowerConcept cpc = concepts.getConceptEntries().get(0);
            return conceptMapper.mapConceptpowerConceptToConcept(cpc);
        }
        return null;
    }
    
    
    @Override
    @Cacheable(value = "concepts")
    public ConceptpowerConcepts search(String searchTerm) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("searchterm", searchTerm); 

        return restTemplate.getForObject(conceptpowerUrl + searchEndpoint + "?word={searchterm}", ConceptpowerConcepts.class,
                vars);
    }
}
