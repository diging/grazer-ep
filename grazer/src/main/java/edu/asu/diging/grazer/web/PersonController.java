package edu.asu.diging.grazer.web;

import java.io.IOException;
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
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.wikidata.IWikipediaConnector;
import edu.asu.diging.grazer.core.wikidata.impl.WikidataStatement;

@Controller
public class PersonController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IGraphDBConnection graphDbConnector;
    
    @Autowired
    private IConceptpowerCache cache;
    
    @Autowired
    private IWikipediaConnector wikiConnector;
    
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
