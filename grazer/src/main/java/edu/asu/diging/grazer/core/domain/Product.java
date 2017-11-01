package edu.asu.diging.grazer.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class Product implements Serializable
{
    private static final long serialVersionUID = 74458L;
 
    @NotNull
    @Size(min=1, max=10)
    private String label;
    private String description;
 
    private List<MultipartFile> files;
     
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
    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
