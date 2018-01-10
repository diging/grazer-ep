package edu.asu.diging.grazer.core.domain;

import java.util.List;

public interface IFileData {

    List<byte[]> getData();

    void setData(List<byte[]> data);

    List<String> getFileNames();

    void setfileNames(List<String> fileNames);

}