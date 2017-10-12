package edu.asu.diging.grazer.core.conceptpower.impl;

import java.time.OffsetDateTime;

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
        IConcept storedConcept = conceptDB.getConcept(id);
        if (storedConcept.getLastUpdated() != null && storedConcept.getLastUpdated().plusDays(2).isAfter(OffsetDateTime.now())) {
            // only update a concept every 2 days
            return;
        }
        
        IConcept concept = connector.getConcept(id);
        if(concept != null) {
            conceptDB.createOrUpdate(concept);
        }
    }
}