package edu.asu.diging.grazer.core.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IFile {

    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);
    
    List<byte[]> getData();

    void setData(List<byte[]> data);

    String getUploader();

    void setUploader(String uploader);

    Date getDate();

    void setDate(Date date);


}