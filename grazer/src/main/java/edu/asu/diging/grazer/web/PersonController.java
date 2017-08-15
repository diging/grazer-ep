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
    
    @Autowired
    private IConceptpowerConnector connector;
    
    @Autowired
    private IGraphManager graphManager;
    
    private IConceptpowerCache cache;

    @RequestMapping("/person/{personId}")
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        	
        //IConcept concept = connector.getConcept(personId);
    		IConcept concept = cache.getConceptByUri(personId);
        Graph graph = graphManager.getTransformedGraph(concept.getUri());
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        model.addAttribute("conceptAltIds", concept.getAlternativeUris());
        model.addAttribute("graph", graph);
        return "person";
    }
}
