package edu.asu.diging.grazer.core.conceptpower.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConceptpowerAlternativeId {

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
