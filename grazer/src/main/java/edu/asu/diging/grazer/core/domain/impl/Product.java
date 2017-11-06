package edu.asu.diging.grazer.core.domain.impl;

import java.io.Serializable;
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

import org.springframework.web.multipart.MultipartFile;

import edu.asu.diging.grazer.core.domain.IProduct;

@Entity
@Table(name="tbl_files")
public class Product implements Serializable, IProduct
{
    private static final long serialVersionUID = 74458L;
 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
    private String label;
    private String description;
    private String uploader;
    private Date date;
 
    @ElementCollection
    @CollectionTable(name="tbl_uploaded_files", joinColumns=@JoinColumn(name="id"))
    @Column(name="files")
    private List<MultipartFile> files;
     
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#getLabel()
     */
    @Override
    public String getLabel() {
        return label;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(String label) {
        this.label = label;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#getFiles()
     */
    @Override
    public List<MultipartFile> getFiles() {
        return files;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#setFiles(java.util.List)
     */
    @Override
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#getUploader()
     */
    @Override
    public String getUploader() {
        return uploader;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#setUploader(java.lang.String)
     */
    @Override
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#getDate()
     */
    @Override
    public Date getDate() {
        return date;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#setDate(java.util.Date)
     */
    @Override
    public void setDate(Date date) {
        this.date = date;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.IProduct#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
}
