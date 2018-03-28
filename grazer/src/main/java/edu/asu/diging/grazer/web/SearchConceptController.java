package edu.asu.diging.grazer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.model.IConcept;

@Controller
public class SearchConceptController {
    
    @Autowired
    private IConceptDatabaseConnection conceptDatabaseConnection;
    
    @RequestMapping(value = "/searchPage")
    public String getPages(Model model) {
        return "searchPage";
    }
    
    @RequestMapping(value = "/searchPage/getTags", method = RequestMethod.GET)
    public @ResponseBody List<IConcept> getTags(@RequestParam String term) {

        List<IConcept> concepts = conceptDatabaseConnection.getConceptList(term);
        for(IConcept concept: concepts) {
            System.out.println(concept.getWord());
        }
        return concepts;

    }
}
