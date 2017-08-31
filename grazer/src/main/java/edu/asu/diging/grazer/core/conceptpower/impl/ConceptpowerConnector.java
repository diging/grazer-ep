package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
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
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.model.IConcept;

@Service
public class ConceptpowerConnector implements IConceptpowerConnector {

	@Autowired
	ConceptMapper conceptMapper;
	
    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    @Value("${conceptpower.url}")
    private String conceptpowerUrl;

    @Autowired
    @Value("${conceptpower.concept.endpoint}")
    private String conceptEndpoint;

    //@Autowired
    //@Named("restTemplate")
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
        		IConcept concept = conceptMapper.mapConceptpowerConceptToConcept(cpc);
        		return concept;
            //return concepts.getConceptEntries().get(0).getAdapter();
        }
        return null;
    }
}
