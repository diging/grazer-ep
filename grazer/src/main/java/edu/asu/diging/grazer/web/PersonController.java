package edu.asu.diging.grazer.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.exception.WikidataException;
import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Node;
import edu.asu.diging.grazer.core.util.ISourceUriPatternUtil;
import edu.asu.diging.grazer.core.wikidata.IWikidataConnector;
import edu.asu.diging.grazer.core.wikidata.impl.WikidataStatement;
import edu.asu.diging.grazer.web.cytoscape.Data;
import edu.asu.diging.grazer.web.cytoscape.EdgeData;
import edu.asu.diging.grazer.web.cytoscape.GraphElement;

@Controller
@PropertySource(value="classpath:/config.properties")
public class PersonController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IGraphDBConnection graphDbConnector;
    
    @Autowired
    private IConceptpowerCache cache;
    
    @Autowired
    private IWikidataConnector wikiConnector;
    
    @Autowired
    private ISourceUriPatternUtil patternUtil;

    @Value("${concepts.type.person}")
    private String personType;
    
    @RequestMapping(value = "/concept/{personId}", produces = MediaType.TEXT_HTML_VALUE)
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        
        IConcept concept = cache.getConceptById(personId);
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        
        List<WikidataStatement> statements = null;
        try {
            statements = wikiConnector.getWikidataStatements(concept);
        } catch (WikidataException e) {
            logger.error("Couldn't retrieve statements from Wikidata.", e);
            model.addAttribute("wikidata_error", true);
        }
        
        if (statements != null) {
            Collections.sort(statements, new Comparator<WikidataStatement>() {
    
                @Override
                public int compare(WikidataStatement o1, WikidataStatement o2) {
                    if (o1.getPredicate() == null || o1.getPredicate().getLabel() == null) {
                        return 1;
                    } 
                    if (o2.getPredicate() == null || o2.getPredicate().getLabel() == null) {
                        return -1;
                    } 
                    return o1.getPredicate().getLabel().compareTo(o2.getPredicate().getLabel());
                }
            });
        }
        model.addAttribute("wikipedia", statements);
        return "person";
    }
    
    @RequestMapping("/concept/{conceptId}/statements")
    public String getConceptStatements(@PathVariable("conceptId") String conceptId, Model model) {
        
        IConcept concept = cache.getConceptById(conceptId);
        List<Edge> uniqueEdges = getEdges(concept.getUri());
        
        model.addAttribute("edges", uniqueEdges);
        uniqueEdges.forEach(e -> {
            e.setPresentationUri(e.getSourceUri());
            if (!patternUtil.isPatternUri(e.getSourceUri())) {
                String resolvedUri = patternUtil.getTransformedResolvedUri(e.getSourceUri());
                if (resolvedUri != null) {
                    e.setPresentationUri(resolvedUri);
                } 
            }
            
        });
        
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person/statements";
    }
    
    @RequestMapping(value = "/concept/{personId}/network")
    public ResponseEntity<Collection<GraphElement>> getPersonNetwork(@PathVariable("personId") String personId) {
        
        IConcept sourceConcept = cache.getConceptById(personId);
        Map<String, GraphElement> elements = new HashMap<>();
        
        List<Edge> uniqueEdges = getEdges(sourceConcept.getUri());
        
        for (Edge edge : uniqueEdges) {
            Node sourceNode = edge.getSourceNode();
            Node targetNode = edge.getTargetNode();
            GraphElement sourceElem = elements.get(sourceNode.getConceptId());
            if (sourceElem == null) {
                IConcept concept = cache.getConceptById(sourceNode.getConceptId());
                sourceElem = new GraphElement(new Data(concept.getId(), sourceNode.getLabel()));
                elements.put(sourceNode.getConceptId(), sourceElem);
            }
            GraphElement targetElem = elements.get(targetNode.getConceptId());
            if (targetElem == null) {
                IConcept concept = cache.getConceptById(targetNode.getConceptId());
                targetElem = new GraphElement(new Data(concept.getId(), targetNode.getLabel()));
                elements.put(targetNode.getConceptId(), targetElem);
            }
            elements.put(edge.getId() + "", new GraphElement(new EdgeData(sourceElem.getData().getId(), targetElem.getData().getId(), edge.getId() + "", "")));
        }
        return new ResponseEntity<Collection<GraphElement>>(elements.values(), HttpStatus.OK);
    }
    
    private List<Edge> getEdges(String uri) {
        List<Edge> edgeList = graphDbConnector.getEdges(uri);
        List<Edge> uniqueEdges = new ArrayList<Edge>();
        HashSet<String> uniqueEdgeStrings = new HashSet<>();
        
        for(int i = 0; i < edgeList.size(); i++) {
            String sourceAndTarget = edgeList.get(i).getSourceNode().getUri() + "-" + edgeList.get(i).getTargetNode().getUri();
            if(!uniqueEdgeStrings.contains(sourceAndTarget)) {
                uniqueEdgeStrings.add(sourceAndTarget);
                uniqueEdges.add(edgeList.get(i));
            } 
        }
        
        return uniqueEdges;
    }
}
