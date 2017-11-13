package edu.asu.diging.grazer.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.FileImpl;
import edu.asu.diging.grazer.core.fileupload.db.impl.FileUploadDatabaseConnection;

@Controller
public class FileUploadController {
    
    @Autowired
    private FileUploadDatabaseConnection connection;
    
    @RequestMapping("/save-transformation")
    public String uploadResources(@ModelAttribute FileImpl transformation,
                                 Model model, @RequestParam CommonsMultipartFile[] files) {

        List<byte[]> data = new ArrayList<byte[]>();
        List<String> fileNames = new ArrayList<String>();
        for (CommonsMultipartFile multipartFile : files) {
            data.add(multipartFile.getBytes());
            fileNames.add(multipartFile.getOriginalFilename()); 
        }
        
        transformation.setfileNames(fileNames);
        transformation.setData(data);
        transformation.setDate(new Date());
        transformation.setUploader(SecurityContextHolder.getContext().getAuthentication().getName());   
        connection.save(transformation);
        
        model.addAttribute("transformation", transformation);
        return "redirect:/transformation-input-form";    
    }
     
    @RequestMapping(value = "transformation-input-form")
    public String inputFile(Model model) {
        model.addAttribute("transformation", new FileImpl());
        model.addAttribute("fileList", connection.list());
        return "fileUploadForm";
    }
    
    @RequestMapping(value = "/download/{label}/{fileName}")
    public String download(@PathVariable("label") String label, @PathVariable("fileName") String fileName, HttpServletResponse response) {
        
        fileName = fileName + ".graphml";
        FileImpl file = connection.get(label);
        List<String> fileNames = file.getfileNames();
        for(int i = 0; i < fileNames.size(); i++) {
            if(fileName.equals(fileNames.get(i))) {
                try {
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition", "attachment;filename=\"" +fileName+ "\"");
                    OutputStream out = response.getOutputStream();
                    ByteArrayInputStream in = new ByteArrayInputStream(file.getData().get(i));
                    IOUtils.copy(in, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
}
