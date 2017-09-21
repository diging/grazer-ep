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
    
    /**
     * Endpoint to retrieve information about a concept. This method will query the triple store and return
     * named graphs with stored statements. The following formats can be requested:
     * <ul>
     *  <li> Trix: application/xml, application/trix</li>
     *  <li> Trig: application/trig </li>
     *  <li> NQuads: application/n-quads </li>
     * </ul>
     * @param id
     * @param accept
     * @return
     */
    @RequestMapping(value = CONCEPT_PREFIX, produces = { IRDFCreator.XML, IRDFCreator.TRIX, IRDFCreator.TRIG, IRDFCreator.NQUADS })
    public ResponseEntity<String> getRelations(@PathVariable("id") String id, @RequestHeader("Accept") String accept) {
        List<RDFStatement> statements = tripleService.getStatements(uriCreator.getUri(id));
        return new ResponseEntity<String>(rdfCreator.getRDF(statements, accept), HttpStatus.OK);
    }
}
