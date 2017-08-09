package edu.asu.diging.grazer.core.conceptpower.impl;

import edu.asu.diging.grazer.core.conceptpower.IConceptMapper;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Concept;

public class ConceptMapper implements IConceptMapper {
	
	public IConcept MapConceptAdapterToConcept(ConceptAdapter conceptAdapter) {
		IConcept concept = new Concept();
		
		concept.setId(conceptAdapter.getId());
		concept.setUri(conceptAdapter.getUri());
		concept.setWord(conceptAdapter.getWord());
		concept.setPos(conceptAdapter.getPos());
		concept.setDescription(conceptAdapter.getDescription());
		concept.setConceptList(conceptAdapter.getConceptList());
		concept.setTypeId(conceptAdapter.getTypeId());
		concept.setAlternativeUris(conceptAdapter.getAlternativeUris());
		concept.setCreatorId(conceptAdapter.getCreatorId());
		concept.setEqualTo(conceptAdapter.getEqualTo());
		concept.setWordnetIds(conceptAdapter.getWordnetIds());
		
		/* 

        concept.setTypeId(reply.getTypeUri() != null ? reply.getTypeUri() : "");

        // get last part of URI = id
        int idx = reply.getId().lastIndexOf("/");
        if (idx > -1) {
            concept.setId(reply.getId().substring(idx+1));
        }

        if (reply.getWordnetId() != null) {
            concept.setWordnetIds(Arrays.asList(reply.getWordnetId().split(",")));
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }
        if (reply.getEqualTo() != null) {
            concept.setEqualTo(Arrays.asList(reply.getEqualTo().split(",")));
        } else {
            concept.setEqualTo(new ArrayList<>());
        } 
		*/ 
		 
		return concept;
	}
}