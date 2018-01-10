package edu.asu.diging.grazer.core.fileupload.db.impl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl;
import edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection;

@Component
@Transactional
public class FileUploadDatabaseConnection implements IFileUploadDatabaseConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#save(edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl)
     */
    @Override
    public void save(FileTransformationImpl transformationFile) {
        sessionFactory.getCurrentSession().save(transformationFile);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#list()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FileTransformationImpl> list() {
        List<FileTransformationImpl> files = null;
        try {
            files = (List<FileTransformationImpl>) sessionFactory.getCurrentSession().createQuery("from FileTransformationImpl").list();
        } catch(NullPointerException e) {
            logger.error("No files stored in database", e);
        }
        return files;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#get(java.lang.String)
     */
    @Override
    public FileTransformationImpl get(int id) {
        return (FileTransformationImpl)sessionFactory.getCurrentSession().get(FileTransformationImpl.class, id);
    }
}
