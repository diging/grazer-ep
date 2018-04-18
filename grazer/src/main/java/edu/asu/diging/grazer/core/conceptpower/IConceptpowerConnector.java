package edu.asu.diging.grazer.core.conceptpower;

import java.util.List;

import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcepts;
import edu.asu.diging.grazer.core.model.IConcept;

public interface IConceptpowerConnector {

    IConcept getConcept(String id);

    ConceptpowerConcepts search(String searchTerm, String pos);
}