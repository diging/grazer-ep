package edu.asu.diging.grazer.core.domain;

/**
 * 
 * This class stores the file name and file content of one pattern file and its 
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
    
    byte[] getTransformationFileContent();
    
    void setTransformationFileContent(byte[] transformationFileContent);
    
    byte[] getPatternFileContent();
    
    void setPatternFileContent(byte[] patternFileContent);

}