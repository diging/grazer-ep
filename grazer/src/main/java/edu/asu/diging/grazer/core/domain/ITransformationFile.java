package edu.asu.diging.grazer.core.domain;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * This class stores the file names and file data of one pattern file and its 
 * corresponding transformation file. 
 * 
 * @author mshah18
 *
 */
public interface ITransformationFile {

    
    MultipartFile getTransformationFile();
    
    void setTransformationFile(MultipartFile transformationFile);
    
    MultipartFile getPatternFile();
    
    void setPatternFile(MultipartFile patternFile);

}