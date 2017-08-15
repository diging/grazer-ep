package edu.asu.diging.grazer.core.conceptpower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.model.IConcept;

@Service
public class ASyncConceptUpdater implements IASyncConceptUpdater {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IConceptDatabaseConnection conceptDB;
	
	@Autowired
	private IConceptpowerConnector connector;
	
	@Override
	@Async
	public void updateConcept(String uri) {
		
		IConcept concept = connector.getConcept(uri);
		if(concept != null) {
			logger.debug("Inside async updateConcept");
			conceptDB.createOrUpdate(concept);
		}
	}
	
}