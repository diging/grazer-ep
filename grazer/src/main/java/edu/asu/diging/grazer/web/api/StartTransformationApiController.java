package edu.asu.diging.grazer.web.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.grazer.core.transform.impl.TransformationCronJob;

@Controller
public class StartTransformationApiController {
    
    @Autowired
    private TransformationCronJob cronJob;

    @RequestMapping("/admin/api/transformations/start")
    public ResponseEntity<String> kickoffTransformations(@RequestParam(value="excludes", defaultValue="") String excludes) {
        List<String> excludeConcepts = Arrays.asList(excludes.split(","));
        excludeConcepts = excludeConcepts.stream().map(c -> c.trim()).collect(Collectors.toList());
        cronJob.retrieveTransformations(excludeConcepts);
        return new ResponseEntity<String>("Transformations started.", HttpStatus.OK);
    }
}
