package edu.asu.diging.grazer.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.IFileData;
import edu.asu.diging.grazer.core.domain.impl.FileDataImpl;
import edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileUploadDatabaseConnection;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;
import edu.asu.diging.grazer.core.fileupload.service.IFileUploadService;

@Controller
public class FileUploadController {
    
    @Autowired
    private FileUploadDatabaseConnection connection;
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@ModelAttribute FileTransformationImpl transformation,
                                 Model model, @RequestParam CommonsMultipartFile[] files) {

        List<byte[]> data = new ArrayList<byte[]>();
        List<String> fileNames = new ArrayList<String>();
        for (CommonsMultipartFile multipartFile : files) {
            data.add(multipartFile.getBytes());
            fileNames.add(multipartFile.getOriginalFilename()); 
        }
        
        IFileUploadService service = new FileUploadServiceImpl();
        service.uploadFiles(data, fileNames);
        
        IFileData file = new FileDataImpl();
        file.setfileNames(fileNames);
        file.setData(data);
        
        transformation.setFile(file);
        transformation.setDate(new Date());
        transformation.setUploader(SecurityContextHolder.getContext().getAuthentication().getName());   
        connection.save(transformation);
        
        model.addAttribute("transformation", transformation);
        return "redirect:/transformation/add";    
    }
     
    @RequestMapping(value = "/transformation/add")
    public String inputFile(Model model) {
        model.addAttribute("transformation", new FileTransformationImpl());
        model.addAttribute("fileList", connection.list());
        return "fileUploadForm";
    }
    
}
