package edu.asu.diging.grazer.core.fileupload.db.impl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.db.IFileMetadataDatabaseConnection;

@Component
@Transactional
public class FileMetadataDatabaseConnection implements IFileMetadataDatabaseConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#save(edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl)
     */
    @Override
    public void save(TransformationFilesMetadataImpl transformationFile) {
        sessionFactory.getCurrentSession().save(transformationFile);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#list()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<TransformationFilesMetadataImpl> list() throws NullPointerException{
        
        List<TransformationFilesMetadataImpl> files = (List<TransformationFilesMetadataImpl>) sessionFactory.getCurrentSession().createQuery("from TransformationFilesMetadataImpl").list();
        
        if(files != null) {
            return files;
        }
        return null;
        
        /*List<TransformationFilesMetadataImpl> files = null;
        try {
            files = (List<TransformationFilesMetadataImpl>) sessionFactory.getCurrentSession().createQuery("from TransformationFilesMetadataImpl").list();
        } catch(NullPointerException e) {
            logger.error("No files stored in database", e);
        }*/
        //return files;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#get(java.lang.String)
     */
    @Override
    public TransformationFilesMetadataImpl get(int id) {
        return (TransformationFilesMetadataImpl)sessionFactory.getCurrentSession().get(TransformationFilesMetadataImpl.class, id);
    }
}
