package edu.asu.diging.grazer.core.domain;

import java.util.Date;
import java.util.List;

public interface IFileImpl {

    int getId();
    
    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);
    
    List<byte[]> getData();

    void setData(List<byte[]> data);
    
    List<String> getfileNames();

    void setfileNames(List<String> fileNames);

    String getUploader();

    void setUploader(String uploader);

    Date getDate();

    void setDate(Date date);


}