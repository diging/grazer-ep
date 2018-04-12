package edu.asu.diging.grazer.core.amphora.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.grazer.core.exception.AmphoraException;

public class AmphoraConnectorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AmphoraConnector serviceToTest;

    private final String AMPHORA_HOST = "AMPHORA_URL";
    private final String ENDPOINT = "ENDPOINT";
    private final String TOKEN = "TOKEN";

    private final String CHILD_URI = "CHILD_URI";
    private final String PARENT_URI = "PARENT_URI";
    
    private final String AMPHORA_URL = String.format("%s%s?format=json&uri=%s", AMPHORA_HOST,
            ENDPOINT, CHILD_URI);

    @Before
    public void setUp() {
        serviceToTest = new AmphoraConnector();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(serviceToTest, "amphoraUrl", AMPHORA_HOST);
        ReflectionTestUtils.setField(serviceToTest, "metadataEndpoint",
                ENDPOINT);
        ReflectionTestUtils.setField(serviceToTest, "amphoraToken", TOKEN);
        
    }

    @Test
    public void test_getURIofParentResource_success() throws AmphoraException {
        String responseJson = "{\"id\":123, \"uri\":\"" + PARENT_URI + "\"}";
        ResponseEntity<String> response = new ResponseEntity<String>(
                responseJson, HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(AMPHORA_URL),
                ArgumentMatchers.eq(HttpMethod.GET),
                Mockito.argThat(new HttpEntityMatcher()),
                ArgumentMatchers.eq(String.class))).thenReturn(response);

        Assert.assertEquals(PARENT_URI,
                serviceToTest.getURIofParentResource(CHILD_URI));
    }
    
    @Test
    public void test_getURIofParentResource_clientError() throws AmphoraException {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(AMPHORA_URL),
                ArgumentMatchers.eq(HttpMethod.GET),
                Mockito.argThat(new HttpEntityMatcher()),
                ArgumentMatchers.eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
       
        Assert.assertNull(serviceToTest.getURIofParentResource(CHILD_URI));
    }
    
    @Test(expected=AmphoraException.class)
    public void test_getURIofParentResource_notOk() throws AmphoraException {
        ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(AMPHORA_URL),
                ArgumentMatchers.eq(HttpMethod.GET),
                Mockito.argThat(new HttpEntityMatcher()),
                ArgumentMatchers.eq(String.class))).thenReturn(response);
        
        serviceToTest.getURIofParentResource(CHILD_URI);
    }
    
    @Test(expected=AmphoraException.class)
    public void test_getURIofParentResource_inproperJson() throws AmphoraException {
        String responseJson = "{\"id\":123, \"uri\":\"" + PARENT_URI + "\"";
        ResponseEntity<String> response = new ResponseEntity<String>(
                responseJson, HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(AMPHORA_URL),
                ArgumentMatchers.eq(HttpMethod.GET),
                Mockito.argThat(new HttpEntityMatcher()),
                ArgumentMatchers.eq(String.class))).thenReturn(response);
        
        serviceToTest.getURIofParentResource(CHILD_URI);
    }

    class HttpEntityMatcher implements ArgumentMatcher<HttpEntity<String>> {

        @Override
        public boolean matches(HttpEntity<String> arg0) {
            return arg0.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0)
                    .equals("Token " + TOKEN);
        }

    }
}
