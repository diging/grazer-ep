package edu.asu.diging.grazer.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

@Controller
public class FileUploadController {
    
    @Autowired
    private IFileUploadService service;
    
    @RequestMapping("/save-transformation")
    public String uploadResources(@ModelAttribute FileImpl transformation,
                                 Model model, @RequestParam CommonsMultipartFile[] files) {

        List<byte[]> data = new ArrayList<byte[]>();
        List<String> fileNames = new ArrayList<String>();
        for (CommonsMultipartFile multipartFile : files) {
 
            data.add(multipartFile.getBytes());
            String fileName = multipartFile.getOriginalFilename();
            fileNames.add(fileName);    
            File testFile = new File("/Users/mshah18/Desktop/test/" + fileName); 
            try {
                multipartFile.transferTo(testFile);
            } catch (IOException e) {
                e.printStackTrace();
            }      
        }
        transformation.setData(data);
        service.save(transformation);
        
        // Here, you can save the file details in database  
        model.addAttribute("transformation", transformation);
        return "fileUploadSuccess";    
    }
     
    @RequestMapping(value = "transformation-input-form")
    public String inputFile(Model model) {
        model.addAttribute("transformation", new FileImpl());
        return "fileUploadForm";
    }
}
