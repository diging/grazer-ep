package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import edu.asu.diging.grazer.core.conceptpower.IConceptMapper;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.IConceptType;
import edu.asu.diging.grazer.core.model.impl.Concept;
import edu.asu.diging.grazer.core.model.impl.ConceptType;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConceptType;

@Component
public class ConceptMapper implements IConceptMapper {
	
	@Override
	public IConcept mapConceptpowerConceptToConcept(ConceptpowerConcept conceptpowerConcept) {
		IConcept concept = new Concept();
		ConceptpowerConceptType conceptType = conceptpowerConcept.type;
		
		//ID -- get last part of URI = id
        int idx = conceptpowerConcept.getId().lastIndexOf("/");
        if (idx > -1) {
            concept.setId(conceptpowerConcept.getId().substring(idx+1));
        }
        
		//URI
        concept.setUri(conceptpowerConcept.getId());
        
		//WORD
        concept.setWord(conceptpowerConcept.getLemma());
        if (conceptpowerConcept.getWordnetId() != null) {
            concept.setWordnetIds(Arrays.asList(((String) conceptpowerConcept.getWordnetId()).split(",")));
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }
        
		//POS
        concept.setPos(conceptpowerConcept.getPos());
        
		//DESC
        concept.setDescription(conceptpowerConcept.getDescription());
        
		//CONCEPTLIST
        concept.setConceptList(conceptpowerConcept.getConceptList());
        
		//TYPEID
        concept.setTypeId(conceptType.getTypeUri() != null ? conceptType.getTypeUri()  : "");
        
		//DELETED
        
		//ALTERNATIVEURIS
        concept.setAlternativeUris(conceptpowerConcept.getAlternativeIds());
        
		//CREATORID
        concept.setCreatorId(conceptpowerConcept.getCreatorId());
        
		//EQUALTO
        if (conceptpowerConcept.getEqualTo() != null) {
            //concept.setEqualTo(Arrays.asList(conceptpowerConcept.getEqualTo().split(",")));
        } else {
            concept.setEqualTo(new ArrayList<>());
        }
        
		//WORDNETIDS
        if (conceptpowerConcept.getWordnetId() != null) {
            //concept.setWordnetIds(Arrays.asList(conceptpowerConcept.getWordnetId().split(",")));
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }
        
		//SETTYPE
        IConceptType type = conceptpowerConcept.getType();
        type.setUri(conceptType.getTypeUri());
        type.setId(conceptType.getTypeId());
        type.setDescription("");
        type.setName(conceptType.getTypeName());
        concept.setType(type);
        
        return concept;
		
	}
}