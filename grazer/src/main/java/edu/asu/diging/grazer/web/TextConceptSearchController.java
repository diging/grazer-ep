package edu.asu.diging.grazer.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.model.IConcept;

@Controller
public class TextConceptSearchController {
    
    @Autowired
    private IConceptpowerCache cpCache;

    @RequestMapping(value = "search/texts")
    public String search(@RequestParam(defaultValue = "") String conceptId, Model model) throws Exception {

        String conceptUri = conceptId;

        if (!conceptId.isEmpty()) {
            IConcept concept = cpCache.getConceptByUri(conceptId);
            
            if (concept != null) {
                concept.setId(conceptUri);
            }

            if (conceptId.startsWith("http://")) {
                int lastIdx = conceptId.lastIndexOf("/");
                conceptId = conceptId.substring(lastIdx + 1);
            }          
            
            model.addAttribute("concept", concept);
        }

        return "search/texts";
    }
}
