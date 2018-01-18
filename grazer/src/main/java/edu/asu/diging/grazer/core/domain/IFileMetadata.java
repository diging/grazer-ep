package edu.asu.diging.grazer.core.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class stores all the details about a transformation file and pattern file 
 * i.e their IDs, labels, descriptions, file names, data uploaded, and uploader.
 * 
 * @author mshah18
 *
 */
public interface IFileMetadata {

    int getId();
    
    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);
    
    ITransformationFile getFiles();
    
    void setFiles(ITransformationFile file);
    
    String getUploader();

    void setUploader(String uploader);

    Date getDate();

    void setDate(Date date);


}