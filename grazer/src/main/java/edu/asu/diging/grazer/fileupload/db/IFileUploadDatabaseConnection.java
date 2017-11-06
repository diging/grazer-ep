package edu.asu.diging.grazer.fileupload.db;

import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.domain.impl.Product;

public interface IFileUploadDatabaseConnection {

    void save(Product product);

}