package edu.asu.diging.grazer.core.graphs;

import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.model.impl.Node;

public interface IGraphCloner {

    /**
     * This method will create a clone of the given {@link Graph} object. It will create new
     * {@link Edge} and {@link Node} objects for all edges and nodes in the graph. However,
     * it will not set the properties sourceNode and targetNode of an edge. Those have to
     * be set after cloning is done.
     * @param graph The graph to be cloned.
     * @return A new graph object with new edge and node objects.
     */
    Graph clone(Graph graph);

}