package edu.asu.diging.grazer.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.document.IDocument;
import edu.asu.diging.grazer.service.IDocumentService;

@Controller
public class FileUploadController {
    
    @RequestMapping(value = "upload")
    public String listFiles(Model model) {
        
        IDocumentService documentService;
        List<IDocument> documents = documentService.getDocuments();
        model.addAttribute("documents", documents); 
        return "upload";
    }
}
