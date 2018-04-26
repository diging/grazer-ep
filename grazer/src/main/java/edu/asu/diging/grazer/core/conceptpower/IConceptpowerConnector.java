package edu.asu.diging.grazer.core.conceptpower;

import java.util.List;

import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.grazer.core.model.IConcept;

public interface IConceptpowerConnector {

    IConcept getConcept(String id);

    List<ConceptpowerConcept> search(String searchTerm);
}