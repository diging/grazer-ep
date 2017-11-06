package edu.asu.diging.grazer.core.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IProduct {

    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);

    List<MultipartFile> getFiles();

    void setFiles(List<MultipartFile> files);

    String getUploader();

    void setUploader(String uploader);

    Date getDate();

    void setDate(Date date);

    String getId();

    void setId(String id);

}