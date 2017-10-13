package edu.asu.diging.grazer.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Controller
public class HomeController {
    
    @Autowired
    private IQuadrigaConnector quadrigaConnector;
    
    @Autowired
    private IGraphDBConnection graphDbConnection;
    
    @Autowired
    private IConceptpowerCache conceptCache;

    @RequestMapping(value = "/")
    public String home(Model model) {           
        List<String> uris = graphDbConnection.getAllPersons();
        List<IConcept> concepts = new ArrayList<>();
        for (String uri : uris) {
            IConcept concept = conceptCache.getConceptByUri(uri);
            concepts.add(concept);
        }
        model.addAttribute("concepts", concepts);
        model.addAttribute("count", concepts.size());
        return "home";
    }
    
    @RequestMapping(value = "/persons")
    public ResponseEntity<List<IConcept>> checkPersons(@RequestParam("pollUrl") String pollUrl) {
        List<IConcept> concepts = quadrigaConnector.checkPersonsResult(pollUrl);
        if (concepts == null) {
            return new ResponseEntity<List<IConcept>>(HttpStatus.OK);
        }
        
        Collections.sort(concepts, new Comparator<IConcept>() {

            @Override
            public int compare(IConcept o1, IConcept o2) {
                return o1.getWord().compareTo(o2.getWord());
            }
        });
        
        return new ResponseEntity<List<IConcept>>(concepts, HttpStatus.OK);
    }
}
