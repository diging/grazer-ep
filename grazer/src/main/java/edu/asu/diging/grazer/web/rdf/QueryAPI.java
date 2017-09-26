package edu.asu.diging.grazer.web.rdf;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.grazer.core.rdf.IRDFTripleService;

@Controller
public class QueryAPI {

    @Autowired
    private IRDFTripleService tripleService;
    
    @RequestMapping(value = "/query", produces = { "application/xml", "application/json" })
    public ResponseEntity<String> getRelations(@RequestParam("q") String q, @RequestHeader("Accept") String accept) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        tripleService.runSparqlQuery(q, accept, out);
        return new ResponseEntity<String>(out.toString(), HttpStatus.OK);
    }
}
