package edu.asu.diging.grazer.core.model.impl;

public class Edge {

    private String source;
    private String target;
    private String label;
    private String concept;
    
    private Node sourceNode;
    private Node targetNode;
    
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getConcept() {
        return concept;
    }
    public void setConcept(String concept) {
        this.concept = concept;
    }
    public Node getSourceNode() {
        return sourceNode;
    }
    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }
    public Node getTargetNode() {
        return targetNode;
    }
    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }
}
