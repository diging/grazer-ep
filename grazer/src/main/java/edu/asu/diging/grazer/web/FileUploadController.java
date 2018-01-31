package edu.asu.diging.grazer.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;
import edu.asu.diging.grazer.web.validator.FormValidator;

@Controller
public class FileUploadController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FileUploadServiceImpl service;
    
    @Autowired
    private FormValidator formValidator;
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@ModelAttribute("transformation") @Valid TransformationFilesMetadataImpl transformation,
            BindingResult result, Model model) {
        
        formValidator.validate(transformation, result);
        
        if (result.hasErrors()) {
            logger.info("Error " + result);
            model.addAttribute("transformation", transformation);
            return "fileUploadForm";
            
        } else {
            service.save(transformation);
            return "redirect:/transformation/add";    
        }
    }
     
    @RequestMapping(value = "/transformation/add")
    public String inputFile(Model model) {
        model.addAttribute("transformation", new TransformationFilesMetadataImpl());
        model.addAttribute("fileList", service.list());
        return "fileUploadForm";
    }
    
}
