package edu.asu.diging.grazer.core.domain;

/**
 * 
 * This class stores the file names and file data of one pattern file and its 
 * corresponding transformation file. 
 * 
 * @author mshah18
 *
 */
public interface ITransformationFiles {
    
    String getTransformationFileName();
    
    void setTransformationFileName(String transformationFile);
    
    String getPatternFileName();
    
    void setPatternFileName(String patternFile);

}