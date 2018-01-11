package edu.asu.diging.grazer.core.domain.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Table;

import org.springframework.stereotype.Component;

import edu.asu.diging.grazer.core.domain.IFile;

@Component
public class FileImpl implements IFile {
    
    private HashMap<String, byte[]> files;

    @Override
    public HashMap<String, byte[]> getFiles() {
        return files;
    }

    @Override
    public void setFiles(HashMap<String, byte[]> files) {
        this.files = files;
        
    }
}
