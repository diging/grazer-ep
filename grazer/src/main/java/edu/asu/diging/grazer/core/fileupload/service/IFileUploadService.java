    package edu.asu.diging.grazer.core.fileupload.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl;

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
    void uploadFiles(HashMap<String, byte[]> fileMap);
    
    /**
     * Saves the information about the files and sends it to the FileUploadDatabaseConnection.
     * @param files
     * @param transformationFile
     */
    void save(CommonsMultipartFile[] files, FileTransformationImpl transformationFile);
    
    /**
     * Returns a list of the file metadata stored in the database
     * @return
     */
    List<FileTransformationImpl> list();
    
    FileTransformationImpl get(int id);

}