package edu.asu.diging.grazer.core.conceptpower;

import edu.asu.diging.grazer.core.model.IConcept;

public interface IConceptpowerCache {

    IConcept getConceptById(String uri);

    IConcept getConceptByUri(String uri);
}
