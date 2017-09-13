package edu.asu.diging.grazer.core.conceptpower.impl;

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
	
	@Override
	public IConcept getConceptByUri(String uri) {
		
		IConcept concept = conceptDB.getConcept(uri);
		if(concept != null) {
			conceptUpdater.updateConcept(uri);
			return concept;
		}
		
		concept = connector.getConcept(uri);
		if(concept != null) {
			conceptDB.createOrUpdate(concept);
		}
        return concept;
        
	}
	
}