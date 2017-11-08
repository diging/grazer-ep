package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileUploadDatabaseConnection;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

@Service
public class FileUploadService implements IFileUploadService {

    @Autowired
    private FileUploadDatabaseConnection connection;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.fileupload.service.impl.IFileUploadService#save(edu.asu.diging.grazer.core.domain.impl.FileImpl)
     */
    @Override
    @Transactional
    public void save(FileImpl transformationFile) {
        
        String uploader = SecurityContextHolder.getContext().getAuthentication().getName();
        
        transformationFile.setDate(new Date());
        transformationFile.setUploader(uploader);
        connection.save(transformationFile);
    }
}
