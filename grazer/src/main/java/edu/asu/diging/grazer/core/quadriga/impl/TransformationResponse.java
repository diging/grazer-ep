package edu.asu.diging.grazer.core.quadriga.impl;

import edu.asu.diging.grazer.core.model.impl.Graph;

public class TransformationResponse {

    private String id;
    private String status;
    private String resultUrl;
    private Graph graph;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

}
