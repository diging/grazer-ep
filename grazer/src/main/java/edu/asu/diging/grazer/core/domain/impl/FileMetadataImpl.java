package edu.asu.diging.grazer.core.domain.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.asu.diging.grazer.core.domain.ITransformationFile;
import edu.asu.diging.grazer.core.domain.IFileMetadata;

@Entity
@Table(name="tbl_files")
public class FileMetadataImpl implements IFileMetadata
{
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    @NotEmpty
    private String label;
    private String description;
    private String uploader;
    private Date date;
    
    @NotEmpty
    //private List<MultipartFile> file;
    @Transient private ITransformationFile file;
     
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
    
    public ITransformationFile getFiles() {
        return file;
    }

    public void setFiles(ITransformationFile file) {
        this.file = file;
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
    public Date getDate() {
        return date;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IFile#setDate(java.util.Date)
     */
    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}