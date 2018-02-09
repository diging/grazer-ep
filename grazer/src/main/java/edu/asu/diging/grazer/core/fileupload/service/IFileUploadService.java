    package edu.asu.diging.grazer.core.fileupload.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;

/**
 * Service class that connects FileUploadController to FileUploadDatabaseConnection
 * 
 * @author mshah18
 *
 */
public interface IFileUploadService {

    /**
     * Receives an array of the transformation file and pattern file which it uploads to the server.
     * 
     * @param multipartFile
     */
    void uploadFiles(CommonsMultipartFile[] multipartFile);
    
    /**
     * Saves the information about the files and sends it to the FileUploadDatabaseConnection.
     * @param transformationFile
     */
    void save(TransformationFilesMetadataImpl transformationFile);
    
    /**
     * Returns a list of the file metadata stored in the database
     * @return
     */
    List<TransformationFilesMetadataImpl> list();
    
    TransformationFilesMetadataImpl get(int id);

}