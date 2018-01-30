package edu.asu.diging.grazer.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.grazer.core.domain.impl.TransformationFilesImpl;

public class FilesValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return TransformationFilesImpl.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        TransformationFilesImpl file = (TransformationFilesImpl) target;
        
        ValidationUtils.rejectIfEmpty(errors, "transformationFile", "NotEmpty.transformation.files.transformationFile");
        ValidationUtils.rejectIfEmpty(errors, "patternFile", "NotEmpty.transformation.files.patternFile");
    }
}
