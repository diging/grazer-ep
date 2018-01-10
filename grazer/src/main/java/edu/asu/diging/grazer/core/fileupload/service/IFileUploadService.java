package edu.asu.diging.grazer.core.fileupload.service;

import java.util.List;

/**
 * 
 * This class converts the file data into bytes so that they can be uploaded to the server.
 * @author mshah18
 *
 */
public interface IFileUploadService {

    void uploadFiles(List<byte[]> data, List<String> fileNames);

}