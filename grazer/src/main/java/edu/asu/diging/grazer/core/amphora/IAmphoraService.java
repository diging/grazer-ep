package edu.asu.diging.grazer.core.amphora;

/**
 * Implemented by classes that allow connecting to Amphora.
 * @author jdamerow
 *
 */
public interface IAmphoraService {

    /**
     * This method asks Amphora for the metadata given a URI and returns the 
     * URI from the field 'uri' of the response. If the URI passed into the method
     * is that of a child resource, this method will return the URI of the parent
     * resource.
     * 
     * @param uri
     * @return
     */
    String getMappedUri(String uri);

}