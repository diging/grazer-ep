    package edu.asu.diging.grazer.core.fileupload.service;

import java.util.HashMap;
import java.util.List;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;

/**
 * Service class that connects FileUploadController to FileUploadDatabaseConnection
 * 
 * @author mshah18
 *
 */
public interface IFileUploadService {

    /**
     * Receives a hashMap of fileNames and byte arrays of the file data which it uploads to the server.
     * 
     * @param data
     * @param fileNames
     */
    void uploadFiles(HashMap<String, byte[]> filesMap);
    
    /**
     * Saves the information about the files and sends it to the FileUploadDatabaseConnection.
     * @param files
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