package edu.asu.diging.grazer.core.domain.impl;

import org.springframework.beans.factory.annotation.Autowired;

public class FileUploadFormImpl {

    @Autowired
    private TransformationFilesMetadataImpl transformationMetadata;
    
    @Autowired
    private FilesImpl files;

    public TransformationFilesMetadataImpl getTransformationMetadata() {
        return transformationMetadata;
    }

    public void setTransformationMetadata(TransformationFilesMetadataImpl transformationMetadata) {
        this.transformationMetadata = transformationMetadata;
    }

    public FilesImpl getFiles() {
        return files;
    }

    public void setFiles(FilesImpl files) {
        this.files = files;
    }
}
