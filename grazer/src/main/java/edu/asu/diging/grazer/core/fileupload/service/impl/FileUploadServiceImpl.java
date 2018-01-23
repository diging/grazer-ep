package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesImpl;
import edu.asu.diging.grazer.core.domain.ITransformationFiles;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileMetadataDatabaseConnection;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

@Service
@PropertySource(value = "classpath:config.properties")
public class FileUploadServiceImpl implements IFileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FileMetadataDatabaseConnection connection;
    
    @Autowired
    private TransformationFilesImpl transFile;
    
    @Autowired
    private Environment env;
    
    public void uploadFiles(HashMap<String, byte[]> filesMap) {
        
        for(String key: filesMap.keySet()) {
            try {      
                int num = 1;
                
                String dir = env.getProperty("transformation.file.dir");
                String serverFileName = dir + File.separator + key;
                String serverFileNameWithoutExt = FilenameUtils.removeExtension(serverFileName);
                String extension = FilenameUtils.getExtension(serverFileName);
                
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
                logger.error("You failed to upload " + key, e);
            } 
        }
    }

    /*public void uploadFiles(List<MultipartFile> multipartFile) {
        for(int i = 0; i < multipartFile.size(); i++) {
            try {      
                int num = 1;
                
                String dir = env.getProperty("transformation.file.dir");
                String serverFileName = dir + File.separator + multipartFile.get(i).getOriginalFilename();
                String extension = FilenameUtils.getExtension(serverFileName);
                String serverFileNameWithoutExt = FilenameUtils.removeExtension(serverFileName);
                
                File serverFile = new File(serverFileName);
                while(serverFile.exists()) {
                    serverFileName = serverFileNameWithoutExt + (num++) + "." + extension;
                    serverFile = new File(serverFileName);
                }

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(multipartFile.get(i).getBytes());
                stream.close();
                logger.info("Server File Location = " + serverFile.getAbsolutePath());
            } catch(IOException e) {
                logger.error("You failed to upload " + multipartFile.get(i).getOriginalFilename(), e);
            } 
        } 
    }*/
    
    @Override
    public void save(CommonsMultipartFile[] files, TransformationFilesMetadataImpl transformationFile) {
        
        HashMap<String, byte[]> filesMap = new HashMap<String, byte[]>();
        
        for (CommonsMultipartFile multipartFile : files) {
            filesMap.put(multipartFile.getOriginalFilename(), multipartFile.getBytes());
        }
        
        //MultipartFile mptransformationFile = files.getTransformationFile();
        //MultipartFile mppatternFile = files.getPatternFile();
        
        //List<MultipartFile> multipartFile = new ArrayList<MultipartFile>();
        //multipartFile = transFile.getFiles();
        
        uploadFiles(filesMap);
        
        transformationFile.setFiles(transFile); 
        transformationFile.setDate(OffsetDateTime.now());
        transformationFile.setUploader(SecurityContextHolder.getContext().getAuthentication().getName());
        
        connection.save(transformationFile);
    }

    @Override
    public List<TransformationFilesMetadataImpl> list() {
        return connection.list();
    }

    @Override
    public TransformationFilesMetadataImpl get(int id) {
        return connection.get(id);
    }
}
