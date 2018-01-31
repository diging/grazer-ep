package edu.asu.diging.grazer.core.domain.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFiles;

@Component
public class TransformationFilesImpl implements ITransformationFiles {
    
    private CommonsMultipartFile transformationFile;
    private CommonsMultipartFile patternFile;
    
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
