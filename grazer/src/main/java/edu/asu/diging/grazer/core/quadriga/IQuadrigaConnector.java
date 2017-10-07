package edu.asu.diging.grazer.core.quadriga;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.quadriga.impl.PollResponse;
import edu.asu.diging.grazer.core.quadriga.impl.TransformationResponse;

public interface IQuadrigaConnector {

    PollResponse getPersons();

    TransformationResponse getTransformedNetworks(String transformationName, Map<String, String> properties) throws IOException;

    TransformationResponse checkForResult(TransformationResponse response);

    List<IConcept> checkPersonsResult(String url);

}