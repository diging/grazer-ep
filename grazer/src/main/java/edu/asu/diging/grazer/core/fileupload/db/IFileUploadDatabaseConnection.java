package edu.asu.diging.grazer.core.fileupload.db;

import java.util.List;

import edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl;

/**
 * This class saves the metadata of the files to the database, 
 * displays it and retrieves the metadata from the database.
 * 
 * @author mshah18
 *
 */
public interface IFileUploadDatabaseConnection {

    /**
     * Saves the details of the files in the database
     * 
     * @param transformationFile
     */
    void save(FileTransformationImpl transformationFile);

    /**
     * Displays all the files stored in the database on the web page.
     * 
     * @return
     */
    List<FileTransformationImpl> list();

    /**
     * Retrieves the files from the database.
     * 
     * @param id
     * @return
     */
    FileTransformationImpl get(int id);

}