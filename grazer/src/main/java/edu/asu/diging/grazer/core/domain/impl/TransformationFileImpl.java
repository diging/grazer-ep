package edu.asu.diging.grazer.core.domain.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Table;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFile;

@Component
public class TransformationFileImpl implements ITransformationFile {
    
    //private HashMap<String, byte[]> files;
    //private List<MultipartFile> files;

    //@Override
    //public List<MultipartFile> getFiles() {
    //    return files;
    //}

    //@Override
    //public void setFiles(List<MultipartFile> files) {
    //   this.files = files;
        
    //}
    
    private MultipartFile transformationFile;
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
    }
}
