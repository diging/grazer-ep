package edu.asu.diging.grazer.core.wikidata.impl;

/**
 * This class represents a concept in Wikidata.
 * 
 * @author jdamerow
 *
 */
public class WikidataConcept {

    private String id;
    private String label;
    private String description;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
