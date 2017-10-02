package edu.asu.diging.grazer.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Graph;

@Controller
public class PersonController {
    
    private final String URI_PREFIX = "http://www.digitalhps.org/concepts/";
    
    @Autowired
    private IGraphManager graphManager;
    
    @Autowired
    private IConceptpowerCache cache;
    
    @RequestMapping(value = "/concept/{personId}", produces = MediaType.TEXT_HTML_VALUE)
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        	String uri = URI_PREFIX + personId;
        IConcept concept = cache.getConceptByUri(uri);
        graphManager.transformGraph(concept.getUri());
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person";
    }
    
    @RequestMapping("/concept/{personId}/graph")
    public String getPersonGraph(@PathVariable("personId") String personId, Model model) {
        String uri = URI_PREFIX + personId;
        IConcept concept = cache.getConceptByUri(uri);
        
        Graph graph = graphManager.getTransfomationResult(concept.getUri());
        model.addAttribute("graph", graph);
        
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person/graph";
    }
}
