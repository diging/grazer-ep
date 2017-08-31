package edu.asu.diging.grazer.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Graph;

@Controller
public class PersonController {
    
    //@Autowired
    // IConceptpowerConnector connector;
    
    @Autowired
    private IGraphManager graphManager;
    
    @Autowired
    private IConceptpowerCache cache;

    @RequestMapping("/person/{personId}")
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        	
    		IConcept concept = cache.getConceptByUri(personId);
        graphManager.transformGraph(concept.getUri());
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person";
    }
    
    @RequestMapping("/person/{personId}/graph")
    public String getPersonGraph(@PathVariable("personId") String personId, Model model) {
        //IConcept concept = connector.getConcept(personId);
        IConcept concept = cache.getConceptByUri(personId);
        
        Graph graph = graphManager.getTransfomationResult(concept.getUri());
        model.addAttribute("graph", graph);
        
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person/graph";
    }
}
