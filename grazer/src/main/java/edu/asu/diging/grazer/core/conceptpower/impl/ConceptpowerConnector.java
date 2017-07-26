package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.Arrays;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Value;
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
@PropertySource("classpath:config.properties")
public class ConceptpowerConnector implements IConceptpowerConnector {

    @Value("${conceptpower.url}")
    private String conceptpowerUrl;

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
            return concepts.getConceptEntries().get(0).getAdapter();
        }
        return null;
    }
}
