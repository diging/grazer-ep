package edu.asu.diging.grazer.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;
import edu.asu.diging.grazer.web.validator.FormValidator;

@Controller
@PropertySource(value = "classpath:/validation.properties")
public class FileUploadController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FileUploadServiceImpl service;
    
    @Autowired
    private FormValidator formValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(formValidator);
    }
    
    @RequestMapping(value = "/transformation/save", method = RequestMethod.POST)
    public String uploadResources(@Valid @ModelAttribute TransformationFilesMetadataImpl transformation,
            BindingResult result, Model model, @RequestParam CommonsMultipartFile[] files) {

        if (result.hasErrors()) {
            logger.info("Error" + result);
            model.addAttribute("transformation", transformation);
            return "fileUploadForm";
        } else {
            logger.info("sucess");
            //service.save(transformation.getFiles(), transformation);
            service.save(files, transformation);
            model.addAttribute("transformation", new TransformationFilesMetadataImpl());
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
