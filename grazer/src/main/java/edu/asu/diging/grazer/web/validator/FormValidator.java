package edu.asu.diging.grazer.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.grazer.web.fileUpload.FileUploadFormImpl;

@Component
public class FormValidator implements Validator {
        
    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadFormImpl.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        FileUploadFormImpl transformationFiles = (FileUploadFormImpl) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label", "NotEmpty.transformation.label");
         
        if(transformationFiles.getTransformationFile().isEmpty() || transformationFiles.getTransformationFile().getSize() == 0) {
            errors.rejectValue("transformationFile", "NotEmpty.transformation.files.transformationFile");
        }
        if(transformationFiles.getPatternFile().isEmpty() || transformationFiles.getPatternFile().getSize() == 0) {
            errors.rejectValue("patternFile", "NotEmpty.transformation.files.patternFile");
        }
    }
}
