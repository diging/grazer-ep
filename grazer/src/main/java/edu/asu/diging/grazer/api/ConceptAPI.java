package edu.asu.diging.grazer.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.rdf.IRDFTripleService;
import edu.asu.diging.grazer.core.rdf.IUriCreator;
import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;

@Controller
public class ConceptAPI {
    
    public final static String ID_PLACEHOLDER = "{id}";
    public final static String CONCEPT_PREFIX = "/concept/" + ID_PLACEHOLDER;
    
    @Autowired
    private IRDFTripleService tripleService;
    
    @Autowired
    private IUriCreator uriCreator;
    
    @RequestMapping(CONCEPT_PREFIX)
    public ResponseEntity<List<RDFStatement>> getRelations(@PathVariable("id") String id) {

        return new ResponseEntity<List<RDFStatement>>(tripleService.getStatements(uriCreator.getUri(id)), HttpStatus.OK);
    }
}
