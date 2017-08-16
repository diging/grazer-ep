package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "conceptpowerConcepts")
public class ConceptpowerConcepts {

	@XmlElement(required = true, namespace="http://www.digitalhps.org/")
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
