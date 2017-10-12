package edu.asu.diging.grazer.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Graph;

@Controller
public class PersonController {
    
    @Autowired
    private IGraphDBConnection graphDbConnector;
    
    @Autowired
    private IConceptpowerCache cache;
    
    @RequestMapping(value = "/concept/{personId}", produces = MediaType.TEXT_HTML_VALUE)
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        
        IConcept concept = cache.getConceptById(personId);
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person";
    }
    
    @RequestMapping("/concept/{personId}/graph")
    public String getPersonGraph(@PathVariable("personId") String personId, Model model) {
        
        IConcept concept = cache.getConceptById(personId);
        
        List<Graph> graph = graphDbConnector.getGraphs(concept.getUri());
        model.addAttribute("graphs", graph);
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person/graph";
    }
}
