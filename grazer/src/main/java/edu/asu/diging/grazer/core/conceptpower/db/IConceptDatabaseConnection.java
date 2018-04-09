package edu.asu.diging.grazer.core.conceptpower.db;

import java.util.List;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.IConceptType;

public interface IConceptDatabaseConnection {

    IConcept getConcept(String uri);
	
    void createOrUpdate(IConcept concept);

    void deleteConcept(String uri);

    IConceptType getType(String uri);

    IConcept getConceptByUri(String uri);

    List<IConcept> getSearchResults(String query);
}
