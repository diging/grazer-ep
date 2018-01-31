package edu.asu.diging.grazer.core.domain;

import java.time.OffsetDateTime;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesImpl;

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
    
    TransformationFilesImpl getFiles();
    
    void setFiles(TransformationFilesImpl files);
    
    String getUploader();

    void setUploader(String uploader);

    OffsetDateTime getDate();

    void setDate(OffsetDateTime date);

}