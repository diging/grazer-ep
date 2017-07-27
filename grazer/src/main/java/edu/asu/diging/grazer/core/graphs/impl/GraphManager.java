package edu.asu.diging.grazer.core.graphs.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.model.impl.Node;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Service
public class GraphManager implements IGraphManager {

    @Autowired
    private IQuadrigaConnector quadrigaConnector;

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.impl.IGraphManager#getTransformedPersonGraph(java.lang.String)
     */
    @Override
    public Graph getTransformedGraph(String uri) throws IOException {
        Map<String, String> props = new HashMap<>();
        props.put("${person_uri}", uri);
        Graph graph = quadrigaConnector.getTransformedNetworks("person_simple_triple", props);
        Graph graphPersonHave = quadrigaConnector.getTransformedNetworks("person_has_someone", props);
        Graph graphSomeoneHas = quadrigaConnector.getTransformedNetworks("someone_has_person", props);

        Map<String, Node> nodeIdMap = new HashMap<>();
        graph.getNodes().forEach(n -> nodeIdMap.put(n.getId(), n));
        
        graphPersonHave.getNodes().forEach(n -> {
            if (!nodeIdMap.containsKey(n.getId())) {
                graph.getNodes().add(n);
                nodeIdMap.put(n.getId(), n);
            }
        });
        graph.getEdges().addAll(graphPersonHave.getEdges());
        
        graphSomeoneHas.getNodes().forEach(n -> {
            if (!nodeIdMap.containsKey(n.getId())) {
                graph.getNodes().add(n);
                nodeIdMap.put(n.getId(), n);
            }
        });
        graph.getEdges().addAll(graphSomeoneHas.getEdges());
        
        graph.getEdges().forEach(e -> {
            e.setSourceNode(nodeIdMap.get(e.getSource()));
            e.setTargetNode(nodeIdMap.get(e.getTarget()));
        });
        
        return graph;
    }
}
