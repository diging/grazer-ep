package edu.asu.diging.grazer.core.amphora.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.grazer.core.amphora.IAmphoraConnector;
import edu.asu.diging.grazer.core.exception.AmphoraException;
import edu.asu.diging.grazer.core.model.impl.UriMapping;


public class AmphoraServiceTest {

    @Mock
    private IAmphoraConnector amphoraConnector;
    
    @Mock
    private MappingDBConnector dbConnector;
    
    @InjectMocks
    private AmphoraService serviceToTest;
    
    private final String URI = "uri";
    private final String MAPPED_URI = "mapped_uri";
    private UriMapping mapping;
    
    @Before
    public void init() {
        serviceToTest = new AmphoraService();
        MockitoAnnotations.initMocks(this);
        
        mapping = new UriMapping();
        mapping.setAnnotatedUri(URI);
        mapping.setMappedUri(MAPPED_URI);
    }
    
    @Test
    public void test_getMappedUri_inDb() {
        
        Mockito.when(dbConnector.get(URI)).thenReturn(mapping);
        
        Assert.assertEquals(MAPPED_URI, serviceToTest.getMappedUri(URI));
    }
    
    @Test
    public void test_getMappedUri_notInDb() throws AmphoraException {
        Mockito.when(amphoraConnector.getURIofParentResource(URI)).thenReturn(MAPPED_URI);
        
        Assert.assertEquals(MAPPED_URI, serviceToTest.getMappedUri(URI));
        Mockito.verify(dbConnector).store(ArgumentMatchers.eq(mapping));
    }
    
    @Test
    public void test_getMappedUri_AmphoraException() throws AmphoraException {
        Mockito.when(amphoraConnector.getURIofParentResource(URI)).thenThrow(new AmphoraException());
        Assert.assertNull(serviceToTest.getMappedUri(URI));
    }
    
    @Test
    public void test_getMappedUri_AmphoraReturnsNull() {
        Assert.assertNull(serviceToTest.getMappedUri(URI));
    }
}
