package edu.asu.diging.grazer.core.fileupload.db.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.grazer.core.domain.ITransformationFilesMetadata;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.db.IFileMetadataDatabaseConnection;

@Component
@Transactional
public class FileMetadataDatabaseConnection implements IFileMetadataDatabaseConnection {
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#save(edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl)
     */
    @Override
    public void save(ITransformationFilesMetadata transformationFilesMetadata) {
        sessionFactory.getCurrentSession().save(transformationFilesMetadata);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#list()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ITransformationFilesMetadata> list() {
        return (List<ITransformationFilesMetadata>)sessionFactory.getCurrentSession().createQuery("from TransformationFilesMetadataImpl").list();
       
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.db.IFileUploadDatabaseConnection#get(java.lang.String)
     */
    @Override
    public ITransformationFilesMetadata get(int id) {
        return (ITransformationFilesMetadata)sessionFactory.getCurrentSession().get(TransformationFilesMetadataImpl.class, id);
    }
}
