package edu.asu.diging.grazer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileUploadController {
    
    @RequestMapping(value = "upload")
    public String listFiles(Model model) {
         
        return "upload";
    }
}
