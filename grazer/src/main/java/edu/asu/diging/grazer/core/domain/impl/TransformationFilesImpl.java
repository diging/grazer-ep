package edu.asu.diging.grazer.core.domain.impl;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFiles;

@Component
public class TransformationFilesImpl implements ITransformationFiles {
    
    @NotEmpty
    private MultipartFile transformationFile;
    @NotEmpty
    private MultipartFile patternFile;
    
    public MultipartFile getTransformationFile() {
        return transformationFile;
    }
    
    public void setTransformationFile(MultipartFile transformationFile) {
        this.transformationFile = transformationFile;
    }
    
    public MultipartFile getPatternFile() {
        return patternFile;
    }
    
    public void setPatternFile(MultipartFile patternFile) {
        this.patternFile = patternFile;
    }
}
