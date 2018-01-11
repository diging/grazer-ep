package edu.asu.diging.grazer.core.domain;

import java.util.List;

/**
 * 
 * This class stores the file names and file data of one pattern file and its 
 * corresponding transformation file. 
 * 
 * @author mshah18
 *
 */
public interface IFileData {

    List<byte[]> getData();

    void setData(List<byte[]> data);

    List<String> getFileNames();

    void setfileNames(List<String> fileNames);

}