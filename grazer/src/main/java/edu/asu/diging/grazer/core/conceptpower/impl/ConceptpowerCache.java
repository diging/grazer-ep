package edu.asu.diging.grazer.core.conceptpower.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.conceptpower.IASyncConceptUpdater;
import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.model.IConcept;

@Service
public class ConceptpowerCache implements IConceptpowerCache {
	
	@Autowired
	private IConceptDatabaseConnection conceptDB;
	
	@Autowired
	private IConceptpowerConnector connector;
	
	@Autowired
	private IASyncConceptUpdater conceptUpdater;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public IConcept getConceptByUri(String uri) {
		
		logger.debug("Inside getconceptbyuri in cache");
		IConcept concept = conceptDB.getConcept(uri);
		
		if(concept != null) {
			logger.debug("Inside cache through db");
			conceptUpdater.updateConcept(uri);
			return concept;
		}
		
		logger.debug("Inside cache  after db fails");
		
		concept = connector.getConcept(uri);
		logger.debug("Inside cache through connector");
		conceptDB.createOrUpdate(concept);
        return concept;
        
		/*IConcept concept = connector.getConcept(uri);
        logger.debug("Inside getconceptbyuri in cache"); 
        return concept;*/
	}
	
}