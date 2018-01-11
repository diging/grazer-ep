package edu.asu.diging.grazer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.FileTransformationImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;

@Controller
public class FileUploadController {
    
    @Autowired
    private FileUploadServiceImpl service;
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@ModelAttribute FileTransformationImpl transformation,
                                 Model model, @RequestParam CommonsMultipartFile[] files) {

        service.save(files, transformation);
        model.addAttribute("transformation", transformation);
        return "redirect:/transformation/add";    
    }
     
    @RequestMapping(value = "/transformation/add")
    public String inputFile(Model model) {
        model.addAttribute("transformation", new FileTransformationImpl());
        model.addAttribute("fileList", service.list());
        return "fileUploadForm";
    }
    
}
