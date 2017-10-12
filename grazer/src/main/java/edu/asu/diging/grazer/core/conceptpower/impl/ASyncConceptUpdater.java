package edu.asu.diging.grazer.core.conceptpower.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.conceptpower.IASyncConceptUpdater;
import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.grazer.core.model.IConcept;

@Service
public class ASyncConceptUpdater implements IASyncConceptUpdater {
	
    @Autowired
    private IConceptDatabaseConnection conceptDB;
	
    @Autowired
    private IConceptpowerConnector connector;
	
    @Override
    @Async
    public void updateConcept(String id) {
        IConcept concept = connector.getConcept(id);
        if(concept != null) {
            conceptDB.createOrUpdate(concept);
        }
    }
}