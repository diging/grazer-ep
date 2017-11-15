package edu.asu.diging.grazer.core.fileupload.db;

import java.util.List;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;

public interface IFileUploadDatabaseConnection {

    void save(FileImpl transformationFile);

    List<FileImpl> list();

    FileImpl get(int id);

}