package edu.asu.diging.grazer.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.grazer.core.domain.impl.FileUploadFormImpl;

@Component
public class FormValidator implements Validator {
        
    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadFormImpl.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        FileUploadFormImpl transformationFiles = (FileUploadFormImpl) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "transformationMetadata.label", "NotEmpty.transformation.label");
         
        if(transformationFiles.getFiles().getTransformationFile().isEmpty() || transformationFiles.getFiles().getTransformationFile().getSize() == 0) {
            errors.rejectValue("files.transformationFile", "NotEmpty.transformation.files.transformationFile");
        }
        if(transformationFiles.getFiles().getPatternFile().isEmpty() || transformationFiles.getFiles().getPatternFile().getSize() == 0) {
            errors.rejectValue("files.patternFile", "NotEmpty.transformation.files.patternFile");
        }
    }
}
