package edu.asu.diging.grazer.core.fileupload.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFilesMetadata;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesImpl;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileMetadataDatabaseConnection;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;
import edu.asu.diging.grazer.web.fileUpload.FileUploadFormImpl;

@Service
@PropertySource(value = "classpath:config.properties")
public class FileUploadServiceImpl implements IFileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FileMetadataDatabaseConnection connection;

    @Autowired
    private Environment env;
    
    public String createID() {
        return UUID.randomUUID().toString();
    } 
    
    private String createDirectory() {
        String pathId = createID();
        File directory = new File(env.getProperty("transformation.file.dir") + File.separator + pathId);
        
        while(directory.exists()) {
            pathId = createID();
            directory = new File(env.getProperty("transformation.file.dir") + File.separator + pathId);
        }
        directory.mkdir();
        return pathId;
    }
    
    private String uploadFiles(CommonsMultipartFile[] multipartFiles) throws IOException {

        String pathId = createDirectory();
        String directory = env.getProperty("transformation.file.dir");
        
        for(CommonsMultipartFile file: multipartFiles) {
            String serverFileName = directory + File.separator + pathId + File.separator + file.getOriginalFilename();
            File serverFile = new File(serverFileName);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();
        } 
        return pathId;
    }

    @Override
    public void save(FileUploadFormImpl transformationMetadataAndFiles, Principal principal) throws IOException { 
        
        TransformationFilesImpl transformation = new TransformationFilesImpl();
        TransformationFilesMetadataImpl metadata = new TransformationFilesMetadataImpl();
        
        transformation.setTransformationFileContent(transformationMetadataAndFiles.getTransformationFile().getBytes());
        transformation.setPatternFileContent(transformationMetadataAndFiles.getPatternFile().getBytes());
        transformation.setTransformationFileName(transformationMetadataAndFiles.getTransformationFile().getOriginalFilename());
        transformation.setPatternFileName(transformationMetadataAndFiles.getPatternFile().getOriginalFilename());
        
        metadata.setLabel(transformationMetadataAndFiles.getLabel());
        metadata.setDescription(transformationMetadataAndFiles.getDescription());
        metadata.setDate(OffsetDateTime.now());
        metadata.setUploader(principal.getName());
        metadata.setFiles(transformation);
        
        CommonsMultipartFile[] files = new CommonsMultipartFile[2];
        files[0] = transformationMetadataAndFiles.getTransformationFile();
        files[1] = transformationMetadataAndFiles.getPatternFile();

        metadata.setPathId(uploadFiles(files));
        connection.save(metadata);
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
