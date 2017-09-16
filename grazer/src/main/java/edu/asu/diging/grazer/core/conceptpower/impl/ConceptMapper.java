package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import edu.asu.diging.grazer.core.conceptpower.IConceptMapper;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.IConceptType;
import edu.asu.diging.grazer.core.model.impl.Concept;
import edu.asu.diging.grazer.core.model.impl.ConceptType;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;

@Component
public class ConceptMapper implements IConceptMapper {
	
	/* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.conceptpower.impl.IConceptMapper#mapConceptpowerConceptToConcept
     */
	@Override
	public IConcept mapConceptpowerConceptToConcept(ConceptpowerConcept conceptpowerConcept) {
		IConcept concept = new Concept();
		IConceptType conceptType = conceptpowerConcept.getType();
		
		//ID
		concept.setId(conceptpowerConcept.getId());
        
		//URI
        concept.setUri(conceptpowerConcept.getId());
        
		//WORD
        concept.setWord(conceptpowerConcept.getLemma());
        
		//POS
        concept.setPos(conceptpowerConcept.getPos());
        
		//DESC
        concept.setDescription(conceptpowerConcept.getDescription());
        
		//CONCEPTLIST
        concept.setConceptList(conceptpowerConcept.getConceptList());
        
		//TYPEID
        concept.setTypeId(conceptType.getUri() != null ? conceptType.getUri()  : "");
        
		//DELETED
        
		//ALTERNATIVEURIS
        concept.setAlternativeUris(conceptpowerConcept.getAlternativeIds());
        
		//CREATORID
        concept.setCreatorId(conceptpowerConcept.getCreatorId());
        
		//EQUALTO
        if (conceptpowerConcept.getEqualTo() != null) {
            concept.setEqualTo(conceptpowerConcept.getEqualTo());
        } else {
            concept.setEqualTo(new ArrayList<>());
        }
        
		//WORDNETIDS
        if (conceptpowerConcept.getWordnetId() != null) {
            concept.setWordnetIds(conceptpowerConcept.getWordnetId());
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }
        
		//SETTYPE
        IConceptType type = new ConceptType();
        type.setUri(conceptType.getUri());
        type.setId(conceptType.getId());
        type.setDescription("");
        type.setName(conceptType.getName());
        concept.setType(type);
        
        return concept;
	}
}