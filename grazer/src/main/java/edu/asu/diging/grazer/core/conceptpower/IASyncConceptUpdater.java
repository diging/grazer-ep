package edu.asu.diging.grazer.core.conceptpower;

import org.springframework.scheduling.annotation.Async;

public interface IASyncConceptUpdater {
	
	void updateConcept(String uri);
	
}