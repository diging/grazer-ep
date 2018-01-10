package edu.asu.diging.grazer.core.domain.impl;

import java.util.List;

import javax.persistence.Table;

import edu.asu.diging.grazer.core.domain.IFileData;

@Table(name="tbl_files")
public class FileDataImpl implements IFileData {

    private List<String> fileNames;
    
    private List<byte[]> data;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.impl.IFile#getData()
     */
    @Override
    public List<byte[]> getData() {
        return data;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.impl.IFile#setData(java.util.List)
     */
    @Override
    public void setData(List<byte[]> data) {
        this.data = data;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.impl.IFile#getFileNames()
     */
    @Override
    public List<String> getFileNames() {
        return fileNames;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.domain.impl.IFile#setfileNames(java.util.List)
     */
    @Override
    public void setfileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
