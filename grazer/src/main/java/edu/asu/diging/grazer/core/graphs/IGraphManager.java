package edu.asu.diging.grazer.core.graphs;

import java.io.IOException;

import edu.asu.diging.grazer.core.model.impl.Graph;

public interface IGraphManager {

    Graph getTransformedGraph(String uri) throws IOException;

}