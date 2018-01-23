package edu.asu.diging.grazer.core.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;
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
public interface ITransformationFilesMetadata {

    int getId();
    
    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);
    
    ITransformationFiles getFiles();
    
    void setFiles(ITransformationFiles files);
    
    String getUploader();

    void setUploader(String uploader);

    OffsetDateTime getDate();

    void setDate(OffsetDateTime date);


}