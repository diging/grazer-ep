package edu.asu.diging.grazer.core.rdf;

import java.io.OutputStream;
import java.util.List;

import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;

/**
 * This class coordinates the storage and retrieval of transformation results into and from the
 * backend triple store.
 * 
 * @author jdamerow
 *
 */
public interface IRDFTripleService {

    /**
     * Adds a new transformed graph to the triple store. This method writes all relationships between
     * concepts into the triple store, with each triple being part of a named graph. If a relationship has
     * time information attached to it, those will be stored as triples for the named graphs. For each 
     * concept in a triple, label and description are also stored as part of a named graph.
     * 
     * @param graph
     */
    void addGraph(Graph graph);

    /**
     * Retrieves all statements in which the provided URI is subject or object.
     * 
     * @param conceptUri
     * @return
     */
    List<RDFStatement> getStatements(String conceptUri);

    /**
     * Rruns the provided Sparql query on the triple store and writes the results to the
     * given {@link OutputStream} in the requested format.
     * 
     * @param query
     * @param mimeType application/xml or application/json
     * @param stream
     */
    void runSparqlQuery(String query, String mimeType, OutputStream stream);

}