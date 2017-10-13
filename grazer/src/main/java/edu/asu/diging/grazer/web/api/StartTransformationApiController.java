package edu.asu.diging.grazer.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.grazer.core.transform.impl.TransformationCronJob;

@Controller
public class StartTransformationApiController {
    
    @Autowired
    private TransformationCronJob cronJob;

    @RequestMapping("/api/transformations/start")
    public ResponseEntity<String> kickoffTransformations() {
        cronJob.retrieveTransformations();
        return new ResponseEntity<String>("Transformations started.", HttpStatus.OK);
    }
}
