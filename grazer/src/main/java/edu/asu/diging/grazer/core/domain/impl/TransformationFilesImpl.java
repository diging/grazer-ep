package edu.asu.diging.grazer.core.domain.impl;

import edu.asu.diging.grazer.core.domain.ITransformationFiles;

public class TransformationFilesImpl implements ITransformationFiles {
        
    private String transformationFileName;
    private String patternFileName;
    private byte[] transformationFileContent;
    private byte[] patternFileContent;
    
    public String getTransformationFileName() {
        return transformationFileName;
    }
    
    public void setTransformationFileName(String transformationFileName) {
        this.transformationFileName = transformationFileName;
    }
    
    public String getPatternFileName() {
        return patternFileName;
    }
    
    public void setPatternFileName(String patternFileName) {
        this.patternFileName = patternFileName;
    }
    
    public byte[] getTransformationFileContent() {
        return transformationFileContent;
    }
    
    public void setTransformationFileContent(byte[] transformationFileContent) {
        this.transformationFileContent = transformationFileContent;
    }
    
    public byte[] getPatternFileContent() {
        return patternFileContent;
    }
    
    public void setPatternFileContent(byte[] patternFileContent) {
        this.patternFileContent = patternFileContent;
    }
}
