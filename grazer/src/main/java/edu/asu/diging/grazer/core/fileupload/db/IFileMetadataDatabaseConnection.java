package edu.asu.diging.grazer.core.fileupload.db;

import java.util.List;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;

/**
 * This class saves the metadata of the files to the database, 
 * and retrieves the metadata from the database.
 * 
 * @author mshah18
 *
 */
public interface IFileMetadataDatabaseConnection {

    /**
     * Saves the details of the files in the database
     * 
     * @param transformationFile
     */
    void save(TransformationFilesMetadataImpl transformationFile);

    /**
     * Displays all the files stored in the database on the web page.
     * 
     * @return
     */
    List<TransformationFilesMetadataImpl> list();

    /**
     * Retrieves the file from the database.
     * 
     * @param id
     * @return
     */
    TransformationFilesMetadataImpl get(int id);

}