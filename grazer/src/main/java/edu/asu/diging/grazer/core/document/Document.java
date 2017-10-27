package edu.asu.diging.grazer.core.document;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.asu.diging.grazer.core.document.impl.IDocument;

@Entity
public class Document implements IDocument {

    @Id
    private String id;
    private String label;
    private String description;
    private String patternTitle;
    private String transformationTitle;
    private String uploader;
    private Date date;
    private String pathToFile; 
     
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
    public String getPatternTitle() {
        return patternTitle;
    }
    public void setPatternTitle(String patternTitle) {
        this.patternTitle = patternTitle;
    }
    public String getTransformationTitle() {
        return transformationTitle;
    }
    public void setTransformationTitle(String transformationTitle) {
        this.transformationTitle = transformationTitle;
    }
    public String getUploader() {
        return uploader;
    }
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getPathToFile() {
        return pathToFile;
    }
    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
