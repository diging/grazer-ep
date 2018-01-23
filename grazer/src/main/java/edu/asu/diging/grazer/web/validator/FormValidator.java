package edu.asu.diging.grazer.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;

@Component
@PropertySource(value = "classpath:validation.properties")
public class FormValidator implements Validator {

    @Autowired
    FileUploadServiceImpl service;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return TransformationFilesMetadataImpl.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        TransformationFilesMetadataImpl fileMetadata = (TransformationFilesMetadataImpl) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label", "NotEmpty.fileUploadForm.label");
    }
}
