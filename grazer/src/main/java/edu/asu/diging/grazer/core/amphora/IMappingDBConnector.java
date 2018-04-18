package edu.asu.diging.grazer.core.amphora;

import edu.asu.diging.grazer.core.model.impl.UriMapping;

public interface IMappingDBConnector {

    void store(UriMapping mapping);

    UriMapping get(String uri);

}