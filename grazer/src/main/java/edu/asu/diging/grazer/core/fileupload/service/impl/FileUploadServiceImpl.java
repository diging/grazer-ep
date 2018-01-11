package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;
import edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileUploadDatabaseConnection;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

@Service
@PropertySource(value = "classpath:config.properties")
public class FileUploadServiceImpl implements IFileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FileUploadDatabaseConnection connection;
    
    @Autowired
    private FileImpl file;
    
    @Autowired
    private Environment env;
    
    public void uploadFiles(HashMap<String, byte[]> filesMap) {
        
        for(String key: filesMap.keySet()) {
            try {      
                int num = 1;
                
                String dir = env.getProperty("transformation.file.dir");
                String serverFileName = dir + File.separator + key;
                String extension = FilenameUtils.getExtension(serverFileName);
                String serverFileNameWithoutExt = FilenameUtils.removeExtension(serverFileName);
                
                File serverFile = new File(serverFileName);
                while(serverFile.exists()) {
                    serverFileName = serverFileNameWithoutExt + (num++) + "." + extension;
                    serverFile = new File(serverFileName);
                }

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(filesMap.get(key));
                stream.close();
                logger.info("Server File Location = " + serverFile.getAbsolutePath());
            } catch(IOException e) {
                logger.error("You failed to upload " + key + e.getMessage());
            } 
        }
    }

    @Override
    public void save(CommonsMultipartFile[] files, FileTransformationImpl transformationFile) {
        
        HashMap<String, byte[]> filesMap = new HashMap<String, byte[]>();
        
        for (CommonsMultipartFile multipartFile : files) {
            filesMap.put(multipartFile.getOriginalFilename(), multipartFile.getBytes());
        }
        
        uploadFiles(filesMap);
        
        transformationFile.setFile(file); 
        transformationFile.setDate(new Date());
        transformationFile.setUploader(SecurityContextHolder.getContext().getAuthentication().getName());
        
        connection.save(transformationFile);
    }

    @Override
    public List<FileTransformationImpl> list() {
        return connection.list();
    }

    @Override
    public FileTransformationImpl get(int id) {
        return connection.get(id);
    }
}
