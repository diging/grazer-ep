package edu.asu.diging.grazer.core.fileupload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFilesMetadata;

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
     * @throws FileUploadException 
     * @throws IOException 
     */
    void uploadFiles(CommonsMultipartFile[] multipartFile) throws IOException;
    
    /**
     * Saves the information about the files and sends it to the FileUploadDatabaseConnection.
     * @param transformationFile
     * @throws IOException 
     */
    void save(ITransformationFilesMetadata transformationFile, CommonsMultipartFile[] multipartFile) throws IOException;
    
    /**
     * Returns a list of the file metadata stored in the database
     * @return
     */
    List<ITransformationFilesMetadata> list();
    
    ITransformationFilesMetadata get(int id);

}