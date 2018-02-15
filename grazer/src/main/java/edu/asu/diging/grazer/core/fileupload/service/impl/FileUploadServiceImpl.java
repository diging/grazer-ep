package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
    private Environment env;
    
    public static synchronized String createID() {
        return UUID.randomUUID().toString();
    } 
    
    public String createDirectory() {
        String directoryName = createID();
        File directory = new File(env.getProperty("transformation.file.dir") + File.separator + directoryName);
        while(directory.exists()) {
            createDirectory();
        }
        directory.mkdirs();
        return directory.toString();
    }
    
    public void uploadFiles(CommonsMultipartFile[] multipartFiles) {

        String directory = createDirectory();

        for(int i = 0; i < multipartFiles.length; i++) {
            try {      
                String serverFileName = directory + File.separator + multipartFiles[i].getOriginalFilename();
                File serverFile = new File(serverFileName);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(multipartFiles[i].getBytes());
                stream.close();
            } catch(IOException e) {
                logger.error("You failed to upload " + multipartFiles[i].getOriginalFilename(), e);
            } 
        } 
    }

    @Override
    public void save(TransformationFilesMetadataImpl transformationFile) { 
        transformationFile.setDate(OffsetDateTime.now());
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
