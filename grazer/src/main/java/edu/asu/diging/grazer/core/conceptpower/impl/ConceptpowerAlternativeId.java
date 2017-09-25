package edu.asu.diging.grazer.core.conceptpower.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.asu.diging.grazer.core.conceptpower.IConceptAlternativeId;

public class ConceptpowerAlternativeId implements IConceptAlternativeId {

    @JsonProperty("concept_id")
    private String conceptId;
    @JsonProperty("concept_uri")
    private String conceptUri;
    
    public String getConceptId() {
        return conceptId;
    }
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }
    public String getConceptUri() {
        return conceptUri;
    }
    public void setConceptUri(String conceptUri) {
        this.conceptUri = conceptUri;
    }
}
