package edu.asu.diging.grazer.core.model.impl;

import java.util.List;

public class Graph {

    private List<Edge> edges;
    private List<Node> nodes;
    
    public List<Edge> getEdges() {
        return edges;
    }
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
    public List<Node> getNodes() {
        return nodes;
    }
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
