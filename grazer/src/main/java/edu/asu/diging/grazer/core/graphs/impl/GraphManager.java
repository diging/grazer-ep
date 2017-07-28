package edu.asu.diging.grazer.core.graphs.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.graphs.IGraphCloner;
import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.graphs.IPredicateProcessor;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.model.impl.Node;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Service
public class GraphManager implements IGraphManager {

    private final String PERSON_SIMPLE_TRIPLE = "person_simple_triple";
    private final String PERSON_OBJECT_SIMPLE_TRIPLE = "person_object_simple_triple";
    private final String PERSON_HAS_SOMEONE = "person_has_someone";
    private final String SOMEONE_HAS_PERSON = "someone_has_person";
    
    @Autowired
    private IQuadrigaConnector quadrigaConnector;
    
    @Autowired
    private IPredicateProcessor predicateProcessor;
    
    @Autowired
    private IGraphCloner graphCloner;
    
    private List<String> transformationNames;
    
    @PostConstruct
    public void init() {
        transformationNames = new ArrayList<>();
        transformationNames.add(PERSON_SIMPLE_TRIPLE);
        transformationNames.add(PERSON_OBJECT_SIMPLE_TRIPLE);
        transformationNames.add(PERSON_HAS_SOMEONE);
        transformationNames.add(SOMEONE_HAS_PERSON);
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.impl.IGraphManager#getTransformedPersonGraph(java.lang.String)
     */
    @Override
    public Graph getTransformedGraph(String uri) throws IOException {
        Map<String, String> props = new HashMap<>();
        props.put("${person_uri}", uri);
        
        // due to caching, we can't change the graph we get from the connector
        // so we need to clone it first, before we change it
        Map<String, Graph> graphs = new HashMap<>();
        for (String tName : transformationNames) {
            Graph retrievedGraph = quadrigaConnector.getTransformedNetworks(tName, props);
            Graph graph = graphCloner.clone(retrievedGraph);
            graphs.put(tName, graph);
        }
        
        Graph compoundGraph = new Graph();
        compoundGraph.setEdges(new ArrayList<>());
        compoundGraph.setNodes(new ArrayList<>());
        
        Map<String, Node> nodeIdMap = new HashMap<>();
        for (String tName : graphs.keySet()) {
            Graph g = graphs.get(tName);
            g.getNodes().forEach(n -> {
                if (!nodeIdMap.containsKey(n.getId())) {
                    nodeIdMap.put(n.getId(), n);
                    compoundGraph.getNodes().add(n);
                }
            });
            g.getEdges().forEach(e -> {
                predicateProcessor.setPredicateName(e, tName);
                compoundGraph.getEdges().add(e);
            });
        }
        
        compoundGraph.getEdges().forEach(e -> {
            e.setSourceNode(nodeIdMap.get(e.getSource()));
            e.setTargetNode(nodeIdMap.get(e.getTarget()));
        });
        
        compoundGraph.getNodes().forEach(n -> {
            n.setConceptId(n.getUri().substring(n.getUri().lastIndexOf("/")+1));
        });
        
        return compoundGraph;
    }
}
