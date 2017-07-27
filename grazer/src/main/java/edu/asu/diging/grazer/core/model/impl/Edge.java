package edu.asu.diging.grazer.core.model.impl;

public class Edge {

    private String source;
    private String target;
    private String label;
    private String concept;
    
    private Concept sourceConcept;
    private Concept targetConcept;
    
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
    public Concept getSourceConcept() {
        return sourceConcept;
    }
    public void setSourceConcept(Concept sourceConcept) {
        this.sourceConcept = sourceConcept;
    }
    public Concept getTargetConcept() {
        return targetConcept;
    }
    public void setTargetConcept(Concept targetConcept) {
        this.targetConcept = targetConcept;
    }
}
