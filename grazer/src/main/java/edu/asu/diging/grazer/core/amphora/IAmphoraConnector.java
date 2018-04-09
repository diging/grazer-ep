package edu.asu.diging.grazer.core.amphora;

import org.springframework.cache.annotation.Cacheable;

import edu.asu.diging.grazer.core.exception.AmphoraException;

public interface IAmphoraConnector {

    String getURIofParentResource(String childUri) throws AmphoraException;

}