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

import edu.asu.diging.grazer.core.domain.ITransformationFilesMetadata;
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
        File directory = new File(env.getProperty("transformation.file.dir") + File.separator + createID());
        while(directory.exists()) {
            directory = new File(env.getProperty("transformation.file.dir") + File.separator + createID());
        }
        directory.mkdir();
        return directory.toString();
    }
    
    public void uploadFiles(CommonsMultipartFile[] multipartFiles) throws IOException {

        String directory = createDirectory();

        for(CommonsMultipartFile file: multipartFiles) {
            try {      
                String serverFileName = directory + File.separator + file.getOriginalFilename();
                File serverFile = new File(serverFileName);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
            } catch(IOException e) {
                logger.error("You failed to upload " + file.getOriginalFilename(), e);
                throw new IOException(e);
            } 
        } 
    }

    @Override
    public void save(ITransformationFilesMetadata transformationFile, CommonsMultipartFile[] multipartFiles) throws IOException { 
        transformationFile.setDate(OffsetDateTime.now());
        uploadFiles(multipartFiles);
        connection.save(transformationFile);
    }

    @Override
    public List<ITransformationFilesMetadata> list() {
        return connection.list();
    }

    @Override
    public ITransformationFilesMetadata get(int id) {
        return connection.get(id);
    }
}
