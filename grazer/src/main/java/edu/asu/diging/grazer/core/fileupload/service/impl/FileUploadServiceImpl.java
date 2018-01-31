package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
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

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileMetadataDatabaseConnection;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

@Service
@PropertySource(value = "classpath:config.properties")
public class FileUploadServiceImpl implements IFileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String DATE_PATTERN = "yyyyMMddhhmmss";
    
    @Autowired
    private FileMetadataDatabaseConnection connection;

    @Autowired
    private Environment env;
    
    public void uploadFiles(CommonsMultipartFile[] multipartFile) {
        
        DateFormat df = new SimpleDateFormat(DATE_PATTERN); 
        
        for(int i = 0; i < multipartFile.length; i++) {
            try {      
                String serverFileName = env.getProperty("transformation.file.dir") + File.separator + multipartFile[i].getOriginalFilename();
                serverFileName = FilenameUtils.removeExtension(serverFileName) + df.format(new Date()) + "." + FilenameUtils.getExtension(serverFileName);
                File serverFile = new File(serverFileName);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(multipartFile[i].getBytes());
                stream.close();
            } catch(IOException e) {
                logger.error("You failed to upload " + multipartFile[i].getOriginalFilename(), e);
            } 
        } 
    }

    @Override
    public void save(TransformationFilesMetadataImpl transformationFile) {
        
        CommonsMultipartFile[] files = new CommonsMultipartFile[2];
        HashMap<String, byte[]> filesMap = new HashMap<String, byte[]>();
        
        files[0] = transformationFile.getFiles().getTransformationFile();
        files[1] = transformationFile.getFiles().getPatternFile();
          
        for (CommonsMultipartFile multipartFile : files) {
            filesMap.put(multipartFile.getOriginalFilename(), multipartFile.getBytes());
        }
        
        uploadFiles(files);
        
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