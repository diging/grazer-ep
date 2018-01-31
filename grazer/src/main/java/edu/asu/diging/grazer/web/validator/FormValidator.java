package edu.asu.diging.grazer.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;

@Component
public class FormValidator implements Validator {
    
    @Autowired
    FileUploadServiceImpl service;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return TransformationFilesMetadataImpl.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        TransformationFilesMetadataImpl fileMetadata = (TransformationFilesMetadataImpl) target;
        
        CommonsMultipartFile transformationFile = fileMetadata.getFiles().getTransformationFile();
        CommonsMultipartFile patternFile = fileMetadata.getFiles().getPatternFile();
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label", "NotEmpty.transformation.label");
        
        if(transformationFile.isEmpty()) {
            errors.rejectValue("files.transformationFile", "NotEmpty.transformation.transformationFile");
        }
        else if(transformationFile.getSize() == 0) {
            errors.rejectValue("files.transformationFile", "NotEmpty.transformation.transformationFile");
        }
        if(patternFile.isEmpty()) {
            errors.rejectValue("files.patternFile", "NotEmpty.transformation.patternFile");
        }
        else if(patternFile.getSize() == 0) {
            errors.rejectValue("files.patternFile", "NotEmpty.transformation.patternFile");
        }
          
    }
}
