package edu.asu.diging.grazer.core.document;

import java.util.Date;

public interface IDocument {

    String getId();

    void setId(String string);

    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);

    String getPatternTitle();

    void setPatternTitle(String patternTitle);

    String getTransformationTitle();

    void setTransformationTitle(String transformationTitle);

    String getUploader();

    void setUploader(String uploader);

    Date getDate();

    void setDate(Date date);


}