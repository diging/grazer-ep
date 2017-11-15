package edu.asu.diging.grazer.core.domain.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import edu.asu.diging.grazer.core.domain.IFileImpl;

@Entity
@Table(name="tbl_files")
public class FileImpl implements IFileImpl
{
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String label;
    private String description;
    private String uploader;
    private Date date;
    
    @ElementCollection
    @CollectionTable(name="tbl_file_names", joinColumns=@JoinColumn(name="id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> fileNames;
    
    @ElementCollection
    @CollectionTable(name="tbl_uploaded_files", joinColumns=@JoinColumn(name="id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @Column(name="files")
    private List<byte[]> data;
     
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
    
    @Override
    public List<byte[]> getData() {
        return data;
    }
    
    @Override
    public void setData(List<byte[]> data) {
        this.data = data;
    }
    
    @Override
    public List<String> getfileNames() {
        return fileNames;
    }
    
    @Override
    public void setfileNames(List<String> fileNames) {
        this.fileNames = fileNames;
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