package edu.asu.diging.grazer.web.validator.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.ITransformationFiles;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

public class linkedListToFileConverter implements Converter<LinkedList<MultipartFile>, ITransformationFiles>{

    private IFileUploadService service;
    
    @Autowired
    private ITransformationFiles file;
    
    private MultipartFile[] files;

    @Override
    public ITransformationFiles convert(LinkedList<MultipartFile> list) {
        
        HashMap<String, byte[]> filesMap = new HashMap<String, byte[]>();
        
        if(list == null) {
            throw new ConversionFailedException(TypeDescriptor.valueOf(LinkedList.class), TypeDescriptor.valueOf(ITransformationFiles.class), list, null);
        }
        
        files[0] = list.getFirst();
        files[1] = list.getLast();
        
        for (MultipartFile multipartFile : files) {
            try {
                filesMap.put(multipartFile.getOriginalFilename(), multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
}
