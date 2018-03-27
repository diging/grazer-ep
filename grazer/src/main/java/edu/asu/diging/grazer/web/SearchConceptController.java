package edu.asu.diging.grazer.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Concept;

@Controller
public class SearchConceptController {
    
    @Autowired
    private IConceptDatabaseConnection conceptDatabaseConnection;
    
    @RequestMapping(value = "/searchPage")
    public ModelAndView getPages() {
        ModelAndView model = new ModelAndView("searchPage");
        return model;
    }
    
    @RequestMapping(value = "/searchPage/getTags", method = RequestMethod.GET)
    public String getTags(@RequestParam String term, Model model) {

        List<IConcept> concepts = conceptDatabaseConnection.getConceptList(term);
        model.addAttribute("concepts", concepts);
        for(IConcept concept: concepts) {
            System.out.println(concept.getWord());
        }
        return "searchPage/getTags";

    }
}
