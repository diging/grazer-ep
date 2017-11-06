package edu.asu.diging.grazer.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import edu.asu.diging.grazer.core.domain.IProduct;
import edu.asu.diging.grazer.core.domain.impl.Product;

@Controller
public class FileUploadController {
    
    @RequestMapping("/save-transformation")
    public String uploadResources(@ModelAttribute Product transformation,
                                 Model model)
    {
        //Get the uploaded files and store them
        List<MultipartFile> files = transformation.getFiles();
        List<String> fileNames = new ArrayList<String>();
        if (files != null && !files.get(0).isEmpty() & !files.get(1).isEmpty() )
        {
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                
                File testFile = new File("/Users/mshah18/Desktop/test/" + fileName); 
                try
                {
                    multipartFile.transferTo(testFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        }
        else {
            return "error";
        }
        
        
        // Here, you can save the product details in database  
        model.addAttribute("transformation", transformation);
        return "viewProductDetail";
        
    }
     
    @RequestMapping(value = "transformation-input-form")
    public String inputProduct(Model model) {
        model.addAttribute("transformation", new Product());
        return "productForm";
    }
}
