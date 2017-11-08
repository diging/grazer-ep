package edu.asu.diging.grazer.web.rdf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.rdf.impl.IDatasetInfoService;
import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;
import edu.asu.diging.grazer.web.rdf.util.IRDFCreator;

@Controller
public class InfoAPI {
    
    @Autowired
    private IRDFCreator rdfCreator;
    
    @Autowired
    private IDatasetInfoService infoService;

    @RequestMapping(value = "/info")
    public ResponseEntity<String> getInfo(@RequestHeader("Accept") String accept) {
        List<RDFStatement> statements = infoService.getInfoStatements();
        String rdf = rdfCreator.getRDF(statements, accept);
        return new ResponseEntity<>(rdf, HttpStatus.OK);
    }
}
