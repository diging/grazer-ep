package edu.asu.diging.grazer.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.exception.WikidataException;
import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.wikidata.IWikidataConnector;
import edu.asu.diging.grazer.core.wikidata.impl.WikidataStatement;

@Controller
public class PersonController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IGraphDBConnection graphDbConnector;
    
    @Autowired
    private IConceptpowerCache cache;
    
    @Autowired
    private IWikidataConnector wikiConnector;
    
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
                    return o1.getPredicate().getLabel().compareTo(o2.getPredicate().getLabel());
                }
            });
        }
        model.addAttribute("wikipedia", statements);
        return "person";
    }
    
    public boolean sameEdge(Edge edge1, Edge edge2){
        return edge1.getSourceNode().getLabel().equals(edge2.getSourceNode().getLabel()) && 
                edge1.getTargetNode().getLabel().equals(edge2.getTargetNode().getLabel());
     }
    
    @RequestMapping("/concept/{conceptId}/graph")
    public String getPersonGraph(@PathVariable("conceptId") String conceptId, Model model) {
        
        IConcept concept = cache.getConceptById(conceptId);
        List<Edge> edgeList = graphDbConnector.getEdges(concept.getUri());
        List<Edge> duplicates = new ArrayList<Edge>();
        for(int i = 0; i < edgeList.size()-1; i++) {
            for(int j = i+1; j < edgeList.size(); j++) {
                if(sameEdge(edgeList.get(i), edgeList.get(j))) {
                    duplicates.add(edgeList.get(j));
                }
            }
        }
        edgeList.removeAll(duplicates);
        
        model.addAttribute("edges", edgeList);
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person/graph";
    }
}
