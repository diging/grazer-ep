package edu.asu.diging.grazer.core.graphs;

import java.util.List;

import edu.asu.diging.grazer.core.model.impl.Graph;

public interface IGraphDBConnection {

    void store(Graph graph);

    List<Graph> getGraphs(String conceptUri);

    void removeGraphs(String conceptUri);

}