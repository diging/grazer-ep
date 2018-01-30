package edu.asu.diging.grazer.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;
import edu.asu.diging.grazer.core.fileupload.service.impl.FileUploadServiceImpl;

@Component
public class FormValidator implements Validator {

    @Autowired
    private FilesValidator filesValidator;
    
    @Autowired
    FileUploadServiceImpl service;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return TransformationFilesMetadataImpl.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        TransformationFilesMetadataImpl fileMetadata = (TransformationFilesMetadataImpl) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label", "NotEmpty.transformation.label");
        
        try {
            errors.pushNestedPath("files");
            ValidationUtils.invokeValidator(this.filesValidator, fileMetadata.getFiles(), errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
