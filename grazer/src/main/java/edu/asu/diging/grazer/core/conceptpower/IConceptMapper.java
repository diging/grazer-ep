package edu.asu.diging.grazer.core.conceptpower;

import edu.asu.diging.grazer.core.conceptpower.impl.ConceptAdapter;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.grazer.core.model.IConcept;

public interface IConceptMapper {

	IConcept mapConceptpowerConceptToConcept(ConceptpowerConcept conceptpowerConcept);
}
