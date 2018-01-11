package edu.asu.diging.grazer.core.domain;

import java.util.HashMap;

/**
 * 
 * This class stores the file names and file data of one pattern file and its 
 * corresponding transformation file. 
 * 
 * @author mshah18
 *
 */
public interface IFile {

    HashMap<String, byte[]> getFiles();
    
    void setFiles(HashMap<String, byte[]> files);

}