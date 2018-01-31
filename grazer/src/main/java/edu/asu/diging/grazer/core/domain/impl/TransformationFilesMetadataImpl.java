package edu.asu.diging.grazer.core.domain.impl;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import edu.asu.diging.grazer.core.domain.ITransformationFilesMetadata;

@Entity
@Table(name="tbl_files")
public class TransformationFilesMetadataImpl implements ITransformationFilesMetadata
{
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String label;
    private String description;
    private String uploader;
    private OffsetDateTime date;
 
    @Transient private TransformationFilesImpl files;
     
    @Override
    public int getId() {
        return id;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#getLabel()
     */
    @Override
    public String getLabel() {
        return label;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(String label) {
        this.label = label;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TransformationFilesImpl getFiles() {
        return files;
    }

    public void setFiles(TransformationFilesImpl files) {
        this.files = files;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#getUploader()
     */
    @Override
    public String getUploader() {
        return uploader;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#setUploader(java.lang.String)
     */
    @Override
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#getDate()
     */
    @Override
    public OffsetDateTime getDate() {
        return date;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#setDate(java.util.Date)
     */
    @Override
    public void setDate(OffsetDateTime date) {
        this.date = date;
    }
}