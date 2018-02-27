package edu.asu.diging.grazer.core.fileupload.service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import edu.asu.diging.grazer.core.domain.ITransformationFilesMetadata;
import edu.asu.diging.grazer.web.fileUpload.FileUploadFormImpl;

/**
 * Service class that connects FileUploadController to FileUploadDatabaseConnection
 * 
 * @author mshah18
 *
 */
public interface IFileUploadService {
    
    /**
     * Saves the information about the files and sends it to the FileUploadDatabaseConnection.
     * @param transformationFile
     * @param principal
     * @throws IOException 
     */
    void save(FileUploadFormImpl transformationMetadataAndFiles, Principal principal) throws IOException;
    
    /**
     * Returns a list of the file metadata stored in the database
     * @return
     */
    List<ITransformationFilesMetadata> list();
    
    ITransformationFilesMetadata get(int id);

}