package edu.asu.diging.grazer.core.fileupload.db.impl;

import java.util.List;
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
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#save(edu.asu.diging.grazer.core.domain.impl.FileImpl)
     */
    @Override
    public void save(FileImpl transformationFile) {
        sessionFactory.getCurrentSession().save(transformationFile);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#list()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FileImpl> list() {
        List<FileImpl> files = null;
        try {
            files = (List<FileImpl>) sessionFactory.getCurrentSession().createQuery("from FileImpl").list();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return files;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#get(java.lang.String)
     */
    @Override
    public FileImpl get(int id) {
        return (FileImpl)sessionFactory.getCurrentSession().get(FileImpl.class, id);
    }
}
