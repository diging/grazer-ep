package edu.asu.diging.grazer.core.conceptpower.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.asu.diging.grazer.core.model.IConceptType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptpowerConceptType implements IConceptType {

    @JsonProperty("type_id")
    private String typeId;
    @JsonProperty("type_uri")
    private String typeUri;
    @JsonProperty("type_name")
    private String typeName;
    
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getTypeUri() {
        return typeUri;
    }
    public void setTypeUri(String typeUri) {
        this.typeUri = typeUri;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    // methods from interface
    @Override
    public String getId() {
        return typeId;
    }
    @Override
    public void setId(String id) {
        this.typeId = id;
    }
    @Override
    public String getUri() {
        return typeUri;
    }
    @Override
    public void setUri(String uri) {
        this.typeUri = uri;
    }
    @Override
    public String getName() {
        return typeName;
    }
    @Override
    public void setName(String name) {
        this.typeName = name;
    }
    @Override
    public String getDescription() {
        // nothing 
        return null;
    }
    @Override
    public void setDescription(String description) {
        // nothing to do
    }  
}
