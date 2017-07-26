package edu.asu.diging.grazer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.model.IConcept;

@Controller
public class PersonController {
    
    @Autowired
    private IConceptpowerConnector connector;

    @RequestMapping("/person/{personId}")
    public String showPerson(@PathVariable("personId") String personId, Model model) {
        
        IConcept concept = connector.getConcept(personId);
        model.addAttribute("concept", concept);
        return "person";
    }
}
