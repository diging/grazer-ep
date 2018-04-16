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
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcepts;
import edu.asu.diging.grazer.core.model.IConcept;

/*@Controller
public class SearchConceptController {
    
    @Autowired
    private IConceptDatabaseConnection conceptDatabaseConnection;
    
    @RequestMapping(value = "/searchPage/getConcepts", method = RequestMethod.GET)
    public @ResponseBody List<IConcept> getTags(@RequestParam String term) {
        return conceptDatabaseConnection.getSearchResults(term);
    }
}*/

@Controller
public class SearchConceptController {

    private static final Logger logger = LoggerFactory.getLogger(SearchConceptController.class);

    @Autowired
    private IConceptpowerConnector connector;

    @RequestMapping(value = "/public/concept/search", method = RequestMethod.GET)
    public ResponseEntity<String> searchConcepts(@RequestParam("searchTerm") String searchTerm) {
        
        ConceptpowerConcepts reply = connector.search(searchTerm);
        List<ConceptpowerConcept> conceptList = reply.getConceptEntries();
        conceptList.addAll(reply.getConceptEntries());

        List<JSONObject> jsonResults = new ArrayList<JSONObject>();

        if (conceptList != null) {
            for (ConceptpowerConcept result : conceptList) {
                try {
                    JSONObject jsonResult = new JSONObject();
                    jsonResult.put("id", result.getId());
                    jsonResult.put("name", result.getLemma());
                    jsonResult.put("description", result.getDescription());
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
