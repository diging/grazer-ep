package edu.asu.diging.grazer.core.quadriga.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Concept;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Service
@PropertySource("classpath:config.properties")
public class QuarigaConnector implements IQuadrigaConnector {
    
    private final String PATTERN_PREFIX = "PAT_";
    private final String TRANSFORMATION_PREFIX = "TRA_";
    private final String fileEnding = ".graphml";
    private final String transformationFolder = "transformations/";
    
    @Value("${quadriga.url}")
    private String quadrigaUrl; 
    
    @Value("${quadriga.concepts.endpoint}")
    private String conceptsEndpoint;
    
    @Value("${concepts.type.person}")
    private String personType;
    
    @Value("${quadriga.project.id}")
    private String projectId;
    
    @Value("${quadriga.transformation.endpoint}")
    private String transformationEndpoint;

    private RestTemplate restTemplate;
    
    public QuarigaConnector() {
        restTemplate = new RestTemplate();
    }
 
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.quadriga.impl.IQuadrigaConnector#getPersons()
     */
    @Override
    @Cacheable(value = "quadriga_persons")
    public List<IConcept> getPersons() {
        Concept[] concepts = restTemplate.getForObject(String.format("%s%s?types=%s&projects=%s", quadrigaUrl, conceptsEndpoint, personType, projectId), Concept[].class);
        return Arrays.asList(concepts);
    }
    
    @Override
    public TransformationResponse getTransformedNetworks(String transformationName, Map<String, String> properties) throws IOException {
        Resource pathResource = new ClassPathResource(transformationFolder + PATTERN_PREFIX + transformationName + fileEnding);
        String pattern = IOUtils.toString(pathResource.getInputStream(), Charset.forName("UTF-8"));
        if (properties != null) {
            for (String key : properties.keySet()) {
                pattern = pattern.replace(key, properties.get(key));
            }
        }
        
        Resource transResource = new ClassPathResource(transformationFolder + TRANSFORMATION_PREFIX + transformationName + fileEnding);
        String transformation = IOUtils.toString(transResource.getInputStream(), Charset.forName("UTF-8"));

        TransformationRequest request = new TransformationRequest();
        request.setPattern(pattern);
        request.setTransformation(transformation);
        
        return restTemplate.postForObject(String.format("%s%s", quadrigaUrl, transformationEndpoint), request, TransformationResponse.class);
    }
    
    @Override
    public TransformationResponse checkForResult(TransformationResponse response) {
        return restTemplate.getForObject(response.getResultUrl(), TransformationResponse.class);
    }
}
