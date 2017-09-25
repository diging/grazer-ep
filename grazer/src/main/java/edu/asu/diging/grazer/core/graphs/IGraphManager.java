package edu.asu.diging.grazer.core.graphs;

import java.io.IOException;

import edu.asu.diging.grazer.core.model.impl.Graph;

/**
 * Implementations of this interface transform graphs and provide the transformation results.
 * 
 * @author jdamerow
 *
 */
public interface IGraphManager {

    /**
     * Start the transformation process for of all statements using the provided URI.
     * 
     * @param uri The URI that should be present in a statement.
     * @throws IOException
     */
    void transformGraph(String uri) throws IOException;

    /** 
     * Returns the graph created from all transformed statements.
     * @param uri URI that was used to start the transformation process.
     * @return
     */
    Graph getTransfomationResult(String uri);

}