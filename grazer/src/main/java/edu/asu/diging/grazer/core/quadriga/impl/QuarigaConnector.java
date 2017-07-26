package edu.asu.diging.grazer.core.quadriga.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Concept;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Service
@PropertySource("classpath:config.properties")
public class QuarigaConnector implements IQuadrigaConnector {
    
    @Value("${quadriga.url}")
    private String quadrigaUrl; 
    
    @Value("${quadriga.concepts.endpoint}")
    private String conceptsEndpoint;
    
    @Value("${concepts.type.person}")
    private String personType;

    private RestTemplate restTemplate;
    
    public QuarigaConnector() {
        restTemplate = new RestTemplate();
    }
 
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.quadriga.impl.IQuadrigaConnector#getPersons()
     */
    @Override
    public List<IConcept> getPersons() {
        Concept[] concepts = restTemplate.getForObject(String.format("%s%s?types=%s", quadrigaUrl, conceptsEndpoint, personType), Concept[].class);
        return Arrays.asList(concepts);
    }
}
