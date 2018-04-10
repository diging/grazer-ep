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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotatedUri == null) ? 0 : annotatedUri.hashCode());
        result = prime * result
                + ((mappedUri == null) ? 0 : mappedUri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UriMapping other = (UriMapping) obj;
        if (annotatedUri == null) {
            if (other.annotatedUri != null)
                return false;
        } else if (!annotatedUri.equals(other.annotatedUri))
            return false;
        if (mappedUri == null) {
            if (other.mappedUri != null)
                return false;
        } else if (!mappedUri.equals(other.mappedUri))
            return false;
        return true;
    }
    
}
