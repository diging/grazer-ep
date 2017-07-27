package edu.asu.diging.grazer.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Controller
public class PersonController {
    
    @Autowired
    private IConceptpowerConnector connector;
    
    @Autowired
    private IQuadrigaConnector quadrigaConnector;

    @RequestMapping("/person/{personId}")
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        
        IConcept concept = connector.getConcept(personId);
        
        Map<String, String> props = new HashMap<>();
        props.put("${person_uri}", concept.getUri());
        Graph graph = quadrigaConnector.getTransformedNetworks("person_simple_triple", props);
        model.addAttribute("concept", concept);
        model.addAttribute("graph", graph);
        return "person";
    }
}
