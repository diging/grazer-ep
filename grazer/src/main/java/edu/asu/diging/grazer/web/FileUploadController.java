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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.web.fileUpload.FileUploadFormImpl;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesImpl;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;
import edu.asu.diging.grazer.web.validator.FormValidator;

@Controller
public class FileUploadController {
    
    @Autowired
    private FileUploadServiceImpl service;
    
    @Autowired
    private FormValidator formValidator;
    
    @Autowired
    private TransformationFilesImpl transformation;
    
    @Autowired
    private TransformationFilesMetadataImpl metadata;
    
    @InitBinder(value="transformationMetadataAndFiles")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(formValidator);
    }
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@ModelAttribute("transformationMetadataAndFiles") @Valid FileUploadFormImpl transformationMetadataAndFiles,
            BindingResult result, Principal principal, Model model) throws IOException {
        
        transformation.setTransformationFileContent(transformationMetadataAndFiles.getTransformationFile().getBytes());
        transformation.setPatternFileContent(transformationMetadataAndFiles.getPatternFile().getBytes());
        transformation.setTransformationFileName(transformationMetadataAndFiles.getTransformationFile().getOriginalFilename());
        transformation.setPatternFileName(transformationMetadataAndFiles.getPatternFile().getOriginalFilename());
        metadata.setLabel(transformationMetadataAndFiles.getLabel());
        metadata.setDescription(transformationMetadataAndFiles.getDescription());
        metadata.setUploader(principal.getName());
        
        if (result.hasErrors()) {
            model.addAttribute("transformationMetadataAndFiles", transformationMetadataAndFiles);
            return "fileUploadForm";  
        } 
        
        CommonsMultipartFile[] files = new CommonsMultipartFile[2];
        files[0] = transformationMetadataAndFiles.getTransformationFile();
        files[1] = transformationMetadataAndFiles.getPatternFile();
        
        service.save(metadata, files);

        return "redirect:/transformation/add";    
    }
     
    @RequestMapping(value = "/transformation/add")
    public String inputFile(Model model) {
        model.addAttribute("transformationMetadataAndFiles", new FileUploadFormImpl());
        model.addAttribute("fileList", service.list());
        return "fileUploadForm";
    }
    
}
