package edu.asu.diging.grazer.core.fileupload.db;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;

public interface IFileUploadDatabaseConnection {

    void save(FileImpl transformationFile);

}