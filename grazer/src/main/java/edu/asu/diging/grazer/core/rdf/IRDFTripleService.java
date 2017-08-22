package edu.asu.diging.grazer.core.rdf;

import java.util.List;

import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;

public interface IRDFTripleService {

    void addGraph(Graph graph);

    List<RDFStatement> getStatements(String conceptUri);

}