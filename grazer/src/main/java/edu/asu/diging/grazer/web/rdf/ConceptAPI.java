package edu.asu.diging.grazer.web.rdf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.rdf.IRDFTripleService;
import edu.asu.diging.grazer.core.rdf.IUriCreator;
import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;
import edu.asu.diging.grazer.web.rdf.util.IRDFCreator;
import edu.asu.diging.grazer.web.rdf.util.impl.RDFCreator;

@Controller
public class ConceptAPI {
    
    public final static String ID_PLACEHOLDER = "{id}";
    public final static String CONCEPT_PREFIX = "/concept/" + ID_PLACEHOLDER;
    
    @Autowired
    private IRDFTripleService tripleService;
    
    @Autowired
    private IUriCreator uriCreator;
    
    @Autowired
    private IRDFCreator rdfCreator;
    
    @RequestMapping(value = CONCEPT_PREFIX, produces = { IRDFCreator.RDFXML, IRDFCreator.TURTLE })
    public ResponseEntity<String> getRelations(@PathVariable("id") String id, @RequestHeader("Accept") String accept) {
        List<RDFStatement> statements = tripleService.getStatements(uriCreator.getUri(id));
        return new ResponseEntity<String>(rdfCreator.getRDF(statements, accept), HttpStatus.OK);
    }
}
