package edu.asu.diging.grazer.core.domain.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Table;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFiles;

@Component
public class TransformationFilesImpl implements ITransformationFiles {
    
    private HashMap<String, byte[]> file;
    //private List<MultipartFile> transFile;

    @Override
    public HashMap<String, byte[]> getFile() {
        return file;
    }

    @Override
    public void setFile(HashMap<String, byte[]> transFile) {
       this.file = transFile;
        
    }
    
    /*private MultipartFile transformationFile;
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
        this.transformationFile = patternFile;
    }*/
}
