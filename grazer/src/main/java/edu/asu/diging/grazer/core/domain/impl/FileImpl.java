package edu.asu.diging.grazer.core.domain.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import edu.asu.diging.grazer.core.domain.IFile;

@Entity
@Table(name="tbl_files")
public class FileImpl implements IFile
{
 
    @Id private String label;
    private String description;
    private String uploader;
    private Date date;
    
    @ElementCollection
    @CollectionTable(name="tbl_uploaded_files", joinColumns=@JoinColumn(name="label"))
    @Column(name="files")
    private List<byte[]> data;
     
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
    
    @Override
    public List<byte[]> getData() {
        return data;
    }
    
    @Override
    public void setData(List<byte[]> data) {
        this.data = data;
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