package edu.asu.diging.grazer.core.graphs;

import java.io.IOException;

import edu.asu.diging.grazer.core.model.impl.Graph;
import net.sf.ehcache.Element;

public interface IGraphManager {

    void transformGraph(String uri) throws IOException;

    Graph getTransfomationResult(String uri);

}