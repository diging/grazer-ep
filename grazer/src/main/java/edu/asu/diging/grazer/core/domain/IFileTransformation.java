package edu.asu.diging.grazer.core.domain;

import java.util.Date;

/**
 * This class stores all the details about a transformation file and pattern file 
 * i.e their IDs, labels, descriptions, file names, data uploaded, and uploader.
 * 
 * @author mshah18
 *
 */
public interface IFileTransformation {

    int getId();
    
    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);
    
    IFileData getFile();
    
    void setFile(IFileData file);
    
    String getUploader();

    void setUploader(String uploader);

    Date getDate();

    void setDate(Date date);


}