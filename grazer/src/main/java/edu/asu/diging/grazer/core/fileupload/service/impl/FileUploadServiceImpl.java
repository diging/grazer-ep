package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

public class FileUploadServiceImpl implements IFileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.fileupload.service.impl.IFileUploadService#uploadFiles(java.util.List, java.util.List)
     */
    @Override
    public void uploadFiles(List<byte[]> data, List<String> fileNames) {
        
        for(int i = 0; i < fileNames.size(); i++) {
            try {
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "transformationFiles");
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                File serverFile = new File(dir.getAbsolutePath() + File.separator + fileNames.get(i));
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(data.get(i));
                stream.close();
                logger.info("Server File Location = " + serverFile.getAbsolutePath());
            } catch(IOException e) {
                logger.info("You failed to upload " + fileNames.get(i) + e.getMessage());
            } 
        }
    }
}
