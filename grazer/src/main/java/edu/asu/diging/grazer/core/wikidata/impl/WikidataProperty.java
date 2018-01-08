package edu.asu.diging.grazer.core.wikidata.impl;

/**
 * This class represents a property in Wikidata.
 * 
 * @author jdamerow
 *
 */
public class WikidataProperty {

    private String id;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String propertyId) {
        this.id = propertyId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
