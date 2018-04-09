package edu.asu.diging.grazer.core.model.impl;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UriMapping {

    @Id
    private String annotatedUri;
    private String mappedUri;
    
    public UriMapping() {}
    
    public UriMapping(String annotatedUri, String mappedUri) {
        super();
        this.annotatedUri = annotatedUri;
        this.mappedUri = mappedUri;
    }
    
    public String getAnnotatedUri() {
        return annotatedUri;
    }
    public void setAnnotatedUri(String annotatedUri) {
        this.annotatedUri = annotatedUri;
    }
    public String getMappedUri() {
        return mappedUri;
    }
    public void setMappedUri(String mappedUri) {
        this.mappedUri = mappedUri;
    }
    
}
