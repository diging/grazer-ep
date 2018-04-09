package edu.asu.diging.grazer.core.amphora;

import edu.asu.diging.grazer.core.exception.AmphoraException;

public interface IAmphoraConnector {

    String getURIofParentResource(String childUri) throws AmphoraException;

}