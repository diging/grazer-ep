package edu.asu.diging.grazer.core.graphs.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.graphs.IGraphCloner;
import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.model.impl.Node;

@Service
public class GraphCloner implements IGraphCloner {

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.impl.IGraphCloner#clone(edu.asu.diging.grazer.core.model.impl.Graph)
     */
    @Override
    public Graph clone(Graph graph) {
        Graph clone = new Graph();
        clone.setEdges(new ArrayList<>());
        clone.setNodes(new ArrayList<>());
        
        for (Edge edge : graph.getEdges()) {
            Edge edgeClone = new Edge();
            edgeClone.setConcept(edge.getConcept());
            edgeClone.setLabel(edge.getLabel());
            edgeClone.setSource(edge.getSource());
            edgeClone.setTarget(edge.getTarget());
            clone.getEdges().add(edgeClone);
        }
        
        for (Node node : graph.getNodes()) {
            Node nodeClone = new Node();
            nodeClone.setConceptId(node.getConceptId());
            nodeClone.setId(node.getId());
            nodeClone.setLabel(node.getLabel());
            nodeClone.setType(node.getType());
            nodeClone.setUri(node.getUri());
            clone.getNodes().add(nodeClone);
        }
        
        return clone;
    }
}
