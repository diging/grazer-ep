package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
