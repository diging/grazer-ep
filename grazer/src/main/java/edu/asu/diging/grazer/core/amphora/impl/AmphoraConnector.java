package edu.asu.diging.grazer.core.amphora.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.asu.diging.grazer.core.amphora.IAmphoraConnector;
import edu.asu.diging.grazer.core.exception.AmphoraException;

@Service
@PropertySource("classpath:/config.properties")
public class AmphoraConnector implements IAmphoraConnector {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${amphora.base.url}")
    private String amphoraUrl;

    @Value("${amphora.metadata.endpoint}")
    private String metadataEndpoint;

    @Value("${amphora.token}")
    private String amphoraToken;

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.amphora.impl.IAmphoraConnector#getURIofParentResource(java.lang.String)
     */
    @Override
    @Cacheable("amphoraUris")
    public String getURIofParentResource(String childUri) throws AmphoraException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Token " + amphoraToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = String.format("%s%s?format=json&uri=%s", amphoraUrl, metadataEndpoint, childUri);
        ResponseEntity<String> metadata;
        try {
            metadata = restTemplate.exchange(url,
                HttpMethod.GET, entity, String.class);
        } catch(HttpClientErrorException ex) {
            logger.error("Could not retrieve metadata from Amphora.", ex);
            return null;
        }
        
        if (metadata.getStatusCode() != HttpStatus.OK) {
            throw new AmphoraException("Could not retrieve metadata from resource with URI: " + childUri);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode metadataNode = null;
        try {
            metadataNode = mapper.readTree(metadata.getBody());
        } catch (IOException e) {
            throw new AmphoraException("Could not parse metadata from resource with URI: " + childUri);
        }
        
        return metadataNode.get("uri").asText();
    }
}
