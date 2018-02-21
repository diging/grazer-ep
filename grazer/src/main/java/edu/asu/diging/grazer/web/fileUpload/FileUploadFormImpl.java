package edu.asu.diging.grazer.web.fileUpload;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadFormImpl {
    
    private String label;
    private String description;
    private CommonsMultipartFile transformationFile;
    private CommonsMultipartFile patternFile;
    
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
    
    public CommonsMultipartFile getTransformationFile() {
        return transformationFile;
    }
    
    public void setTransformationFile(CommonsMultipartFile transformationFile) {
        this.transformationFile = transformationFile;
    }
    
    public CommonsMultipartFile getPatternFile() {
        return patternFile;
    }
    
    public void setPatternFile(CommonsMultipartFile patternFile) {
        this.patternFile = patternFile;
    }

}
