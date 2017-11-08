package edu.asu.diging.grazer.core.fileupload.db.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;
import edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection;

@Component
@Transactional
public class FileUploadDatabaseConnection implements IFileUploadDatabaseConnection {

    @Autowired
    protected SessionFactory sessionFactory;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.fileupload.db.impl.IFileUploadDatabaseConnection#save(edu.asu.diging.grazer.core.domain.impl.FileImpl)
     */
    @Override
    public void save(FileImpl transformationFile) {
        sessionFactory.getCurrentSession().save(transformationFile);
    }
}
