package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.List;

public class ConceptpowerConcepts {

    private List<ConceptpowerConcept> conceptEntries;
    
    private String pagination;

    public List<ConceptpowerConcept> getConceptEntries() {
        return conceptEntries;
    }

    public void setConceptEntries(List<ConceptpowerConcept> conceptEntries) {
        this.conceptEntries = conceptEntries;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }
}
