package edu.asu.diging.grazer.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;

@Controller
public class HomeController {
    
    @Autowired
    private IQuadrigaConnector quadrigaConnector;

    @RequestMapping(value = "/")
    public String home(Model model) {
        List<IConcept> concepts = quadrigaConnector.getPersons();
        Collections.sort(concepts, new Comparator<IConcept>() {

            @Override
            public int compare(IConcept o1, IConcept o2) {
                return o1.getWord().compareTo(o2.getWord());
            }
        });
        model.addAttribute("concepts", concepts);
        return "home";
    }
}
