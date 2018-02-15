package edu.asu.diging.grazer.core.domain;

import edu.asu.diging.grazer.core.domain.impl.FilesImpl;
import edu.asu.diging.grazer.core.domain.impl.TransformationFilesMetadataImpl;

public interface IFileUploadForm {

    TransformationFilesMetadataImpl getTransformationMetadata();

    void setTransformationMetadata(TransformationFilesMetadataImpl transformationMetadata);

    FilesImpl getFiles();

    void setFiles(FilesImpl files);

}