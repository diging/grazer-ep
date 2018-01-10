package edu.asu.diging.grazer.core.domain;

import java.util.List;

/**
 * 
 * This class stores the file name and file data which is in byte format.
 * @author mshah18
 *
 */
public interface IFileData {

    List<byte[]> getData();

    void setData(List<byte[]> data);

    List<String> getFileNames();

    void setfileNames(List<String> fileNames);

}