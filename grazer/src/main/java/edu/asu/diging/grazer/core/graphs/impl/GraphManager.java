package edu.asu.diging.grazer.core.graphs.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.graphs.IGraphCloner;
import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.graphs.IPredicateProcessor;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.model.impl.Node;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;
import edu.asu.diging.grazer.core.quadriga.impl.TransformationResponse;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class GraphManager implements IGraphManager {
    
    private final String FILE_EXTENSION = ".graphml";
    private final String PREFIX = "PAT_";
    private final String FOLDER_NAME = "/transformations";
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IQuadrigaConnector quadrigaConnector;
    
    @Autowired
    private IPredicateProcessor predicateProcessor;
    
    @Autowired
    private IGraphCloner graphCloner;
    
    @Autowired
    @Qualifier("ehcache")
    private CacheManager cacheManager;
    
    private List<String> transformationNames;
    private Cache cache;
    
    private File[] files;
    
    @PostConstruct
    public void init() {
        
        File folder = new File(getClass().getResource(FOLDER_NAME).getFile());
    		transformationNames = new ArrayList<>();        
        
    		FileFilter filter = new FileFilter() {
    			public boolean accept(File file) {
    				if (file.getName().endsWith(FILE_EXTENSION) && file.getName().startsWith(PREFIX)) {
    					return true;
    				}
    				return false;
    			}
    		};
    		
    		if(folder.exists()) {
    			if(folder.isDirectory()) {
    			    files = folder.listFiles(filter);
	        		if(files != null && files.length > 0) {
	        			for(int i = 0; i < files.length; i++) {
	        				// Removing prefixes PAT_ and TRA_ and .graphml at the end 
		        			String file = files[i].getName().substring(4).replaceFirst("[.][^.]+$", "");
		        			transformationNames.add(file);
	        			}
	        		}
	        	}
        }
        cache = cacheManager.getCache("quadriga_graphs");
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.impl.IGraphManager#getTransformedPersonGraph(java.lang.String)
     */
    @Override
    @Async
    public void transformGraph(String uri) throws IOException {
    		   		
        // if there is already a transformation running or a result cached, let's not
        // start the transformation again
        if (cache.isKeyInCache(uri) && !cache.get(uri).isExpired()) {
            return;
        }
        cache.put(new Element(uri, null));
        Map<String, String> props = new HashMap<>();
        props.put("${person_uri}", uri);
        
        // due to caching, we can't change the graph we get from the connector
        // so we need to clone it first, before we change it
        Map<String, Graph> graphs = new HashMap<>();
        for (String tName : transformationNames) {
            Graph retrievedGraph = null;
            // TODO: eventually we want to do this in parallel but for now let's not overwhelm Quadriga
            TransformationResponse response = quadrigaConnector.getTransformedNetworks(tName, props);
            while (true) {
                response = quadrigaConnector.checkForResult(response);
                if (!response.getStatus().equals("IN_PROGRESS")) {
                    retrievedGraph = response.getGraph();
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e1) {
                    logger.error("Could not slep.", e1);
                }
            }
            if (retrievedGraph != null) {
                Graph graph = graphCloner.clone(retrievedGraph);
                graphs.put(tName, graph);
            }
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
                predicateProcessor.setPredicateUri(e);
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
        
        cache.put(new Element(uri, compoundGraph));
    }
    
    @Override
    public Graph getTransfomationResult(String uri) {
        Element element = cache.get(uri);
        if (element == null) {
            return null;
        }
        Object result = element.getObjectValue();
        if (result == null) {
            return null;
        }
        return (Graph) result;
    }
}
