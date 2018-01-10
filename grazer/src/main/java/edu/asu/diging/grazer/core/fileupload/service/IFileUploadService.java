package edu.asu.diging.grazer.core.fileupload.service;

import java.util.List;

public interface IFileUploadService {

    void uploadFiles(List<byte[]> data, List<String> fileNames);

}