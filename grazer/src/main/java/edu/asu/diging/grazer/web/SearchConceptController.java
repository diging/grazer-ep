package edu.asu.diging.grazer.web;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;

@Controller
public class SearchConceptController {

    private final Logger logger = LoggerFactory.getLogger(SearchConceptController.class);

    @Autowired
    private IConceptpowerConnector connector;
    
    @RequestMapping(value = "search/texts")
    public String search() {
        return "search/texts";
    }

    @RequestMapping(value = "search/searchConcepts", method = RequestMethod.GET)
    public ResponseEntity<String> searchConcepts(@RequestParam("searchTerm") String searchTerm) {

        List<ConceptpowerConcept> conceptList = connector.search(searchTerm);  
        
        List<JSONObject> jsonResults = new ArrayList<JSONObject>();

        if (conceptList != null) {
            for (ConceptpowerConcept result : conceptList) {
                try {
                    JSONObject jsonResult = new JSONObject();
                    jsonResult.put("id", result.getId());
                    jsonResult.put("name", result.getLemma());
                    jsonResult.put("description", result.getDescription());
                    jsonResult.put("pos", result.getPos());
                    jsonResult.put("type", result.getType());
                    jsonResults.add(jsonResult);
                } catch (JSONException e) {
                    logger.error("Json exception while adding the results", e);
                }
            }
        } 

        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("terms", jsonResults);
        } catch (JSONException e) {
            return new ResponseEntity<String>("{'error':'" + e.getMessage() + "'}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
    }
}
