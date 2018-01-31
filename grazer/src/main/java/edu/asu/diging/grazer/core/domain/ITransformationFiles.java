package edu.asu.diging.grazer.core.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * This class stores the file names and file data of one pattern file and its 
 * corresponding transformation file. 
 * 
 * @author mshah18
 *
 */
public interface ITransformationFiles {
    
    CommonsMultipartFile getTransformationFile();
    
    void setTransformationFile(CommonsMultipartFile transformationFile);
    
    CommonsMultipartFile getPatternFile();
    
    void setPatternFile(CommonsMultipartFile patternFile);

}