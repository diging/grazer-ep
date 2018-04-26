package edu.asu.diging.grazer.core.conceptpower.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Concept;

public class ConceptpowerConnectorTest {
    
    @Mock
    private RestTemplate restTemplate;
    
    @InjectMocks
    private ConceptpowerConnector serviceToTest;
    
    private final String CONCEPTPOWER_HOST = "CONCEPTPOWER_URL";
    private final String CONCEPT_ENDPOINT = "CONCEPT_ENDPOINT";
    private final String SEARCH_ENDPOINT = "SEARCH_ENDPOINT";
    
    private final String ID = "ID";
    private final String ITEM = "ITEM";
    private IConcept concept;
    
    private final String CONCEPTPOWER_URL = String.format("%s%s%s", CONCEPTPOWER_HOST,
            CONCEPT_ENDPOINT, ID);
    
    @Before
    public void setUp() {
        serviceToTest = new ConceptpowerConnector();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(serviceToTest, "conceptpowerUrl", CONCEPTPOWER_HOST);
        ReflectionTestUtils.setField(serviceToTest, "conceptEndpoint", CONCEPT_ENDPOINT);
        ReflectionTestUtils.setField(serviceToTest, "searchEndpoint", SEARCH_ENDPOINT);
        concept = new Concept();
    }
    
    @Test
    public void test_getConcept_clientError() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(CONCEPTPOWER_URL),
                ArgumentMatchers.eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>> any(),
                ArgumentMatchers.eq(ConceptpowerConcepts.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
       
        Assert.assertNull(serviceToTest.getConcept(ID));
    }
    
    @Test
    public void test_getConcept_ConceptpowerReturnsNull() {
        Assert.assertNull(serviceToTest.getConcept(ID));
    }
    
    @Test
    public void test_search_clientError() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(CONCEPTPOWER_HOST + SEARCH_ENDPOINT + ITEM),
                ArgumentMatchers.eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>> any(),
                ArgumentMatchers.eq(ConceptpowerConcepts.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assert.assertNull(serviceToTest.search(ITEM));
               
    }
    
    @Test
    public void test_search_ConceptpowerReturnsNull() {
        Assert.assertNull(serviceToTest.search(ITEM));
    }
}

