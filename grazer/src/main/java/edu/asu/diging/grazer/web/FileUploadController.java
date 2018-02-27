package edu.asu.diging.grazer.web;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.grazer.web.fileUpload.FileUploadFormImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;
import edu.asu.diging.grazer.web.validator.FormValidator;

@Controller
public class FileUploadController {
    
    @Autowired
    private FileUploadServiceImpl service;
    
    @Autowired
    private FormValidator formValidator;
    
    @InitBinder(value="transformationMetadataAndFiles")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(formValidator);
    }
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@ModelAttribute("transformationMetadataAndFiles") @Valid FileUploadFormImpl transformationMetadataAndFiles,
            BindingResult result, Principal principal, Model model) throws IOException {
        
        if (result.hasErrors()) {
            model.addAttribute("transformationMetadataAndFiles", transformationMetadataAndFiles);
            return "fileUploadForm";  
        }        
        
        service.save(transformationMetadataAndFiles, principal);
        return "redirect:/transformation/add";    
    }
     
    @RequestMapping(value = "/transformation/add")
    public String inputFile(Model model) {
        model.addAttribute("transformationMetadataAndFiles", new FileUploadFormImpl());
        model.addAttribute("fileList", service.list());
        return "fileUploadForm";
    }
    
}
