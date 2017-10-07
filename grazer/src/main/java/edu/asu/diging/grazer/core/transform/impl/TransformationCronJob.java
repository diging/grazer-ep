package edu.asu.diging.grazer.core.transform.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.asu.diging.grazer.core.graphs.IGraphManager;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;
import edu.asu.diging.grazer.core.quadriga.impl.PollResponse;
import edu.asu.diging.grazer.core.rdf.IRDFTripleService;

@Component
@EnableScheduling
@PropertySource("classpath:config.properties")
public class TransformationCronJob {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IQuadrigaConnector quadrigaConnector;
    
    @Autowired
    private IGraphManager graphManager;
    
    @Autowired
    private IRDFTripleService tripleService;
    
    @PostConstruct
    public void init() {
        logger.info("TransformationCronJob is up and running.");
    }
    
    @Scheduled(cron = "${cron_schedule}")
//    @Scheduled(fixedDelay=360000)
    public void retrieveTransformations() {
        logger.info("Updating triples...");
        PollResponse response = quadrigaConnector.getPersons();
        List<IConcept> concepts = null;
        while (concepts == null) {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                logger.error("Could not sleep.", e);
                break;
            }
            concepts = quadrigaConnector.checkPersonsResult(response.getPollUrl());
        }
        
        for (IConcept concept : concepts) {
            logger.info("Retrieving graph for " + concept.getUri());
            try {
                graphManager.transformGraph(concept.getUri());
            } catch (IOException e1) {
                logger.error("Could not start transformation.", e1);
                continue;
            }
            Graph graph = null;
            while (graph == null) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    logger.error("Could not sleep.", e);
                    break;
                }
                graph = graphManager.getTransfomationResult(concept.getUri());
            }
            if (graph != null) {
                tripleService.addGraph(graph);
            }
        }
        logger.info("Updating triples done.");
    }
}
