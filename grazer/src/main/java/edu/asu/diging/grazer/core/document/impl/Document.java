package edu.asu.diging.grazer.core.document.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.asu.diging.grazer.core.document.IDocument;

@Entity
public class Document implements IDocument {

    @Id
    private String id;
    private String label;
    private String description;
    private String patternTitle;
    private String transformationTitle;
    private String uploader;
    private Date date;
     
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getLabel()
     */
    @Override
    public String getLabel() {
        return label;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(String label) {
        this.label = label;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getPatternTitle()
     */
    @Override
    public String getPatternTitle() {
        return patternTitle;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setPatternTitle(java.lang.String)
     */
    @Override
    public void setPatternTitle(String patternTitle) {
        this.patternTitle = patternTitle;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getTransformationTitle()
     */
    @Override
    public String getTransformationTitle() {
        return transformationTitle;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setTransformationTitle(java.lang.String)
     */
    @Override
    public void setTransformationTitle(String transformationTitle) {
        this.transformationTitle = transformationTitle;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getUploader()
     */
    @Override
    public String getUploader() {
        return uploader;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setUploader(java.lang.String)
     */
    @Override
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#getDate()
     */
    @Override
    public Date getDate() {
        return date;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.document.impl.IDocument#setDate(java.util.Date)
     */
    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}
