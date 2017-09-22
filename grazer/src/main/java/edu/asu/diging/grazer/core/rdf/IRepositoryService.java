package edu.asu.diging.grazer.core.rdf;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;

public interface IRepositoryService {

    void addStatements(List<RDFStatement> statements);

    List<Map<String, String>> queryRepository(String query);

    void runSparqlQuery(OutputStream stream, String query, String mimeType);

}