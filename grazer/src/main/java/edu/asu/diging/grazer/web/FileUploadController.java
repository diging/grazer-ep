package edu.asu.diging.grazer.web;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.FileUploadFormImpl;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesImpl;
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
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@ModelAttribute("transformationMetadataAndFiles") @Valid FileUploadFormImpl transformationMetadataAndFiles,
            BindingResult result, Principal principal, Model model) throws IOException {
        
        transformation.setTransformationFileContent(transformationMetadataAndFiles.getFiles().getTransformationFile().getBytes());
        transformation.setPatternFileContent(transformationMetadataAndFiles.getFiles().getPatternFile().getBytes());
        transformation.setTransformationFileName(transformationMetadataAndFiles.getFiles().getTransformationFile().getOriginalFilename());
        transformation.setPatternFileName(transformationMetadataAndFiles.getFiles().getPatternFile().getOriginalFilename());
        
        formValidator.validate(transformationMetadataAndFiles, result);
        
        if (result.hasErrors()) {
            model.addAttribute("transformation", transformationMetadataAndFiles);
            return "fileUploadForm";  
        } 
        
        transformationMetadataAndFiles.getTransformationMetadata().setUploader(principal.getName());
        
        CommonsMultipartFile[] files = new CommonsMultipartFile[2];
        files[0] = transformationMetadataAndFiles.getFiles().getTransformationFile();
        files[1] = transformationMetadataAndFiles.getFiles().getPatternFile();
        
        service.uploadFiles(files);
        service.save(transformationMetadataAndFiles.getTransformationMetadata());

        return "redirect:/transformation/add";    
    }
     
    @RequestMapping(value = "/transformation/add")
    public String inputFile(Model model) {
        model.addAttribute("transformationMetadataAndFiles", new FileUploadFormImpl());
        model.addAttribute("fileList", service.list());
        return "fileUploadForm";
    }
    
}
