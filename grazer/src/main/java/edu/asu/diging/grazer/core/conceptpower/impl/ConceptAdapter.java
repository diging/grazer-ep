package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.IConceptType;


public class ConceptAdapter implements IConcept {
    
    private ConceptpowerConcept concept;
    
    private ConceptpowerConceptType conceptType;
    
    public  ConceptAdapter(ConceptpowerConcept concept) {
        this.concept = concept;
    }

    @Override
    public String getId() {
        return this.concept.getId();
    }

    @Override
    public void setId(String id) {
        this.concept.setId(id);
    }

    @Override
    public String getUri() {
        return this.concept.getConceptUri();
    }

    @Override
    public void setUri(String uri) {
        this.concept.setConceptUri(uri);
    }

    @Override
    public String getWord() {
        return this.concept.getLemma();
    }

    @Override
    public void setWord(String word) {
        this.concept.setLemma(word);
    }

    @Override
    public String getPos() {
        return this.concept.getPos();
    }

    @Override
    public void setPos(String pos) {
        this.concept.setPos(pos);
    }

    @Override
    public String getDescription() {
        return this.concept.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.concept.setDescription(description);
    }

    @Override
    public String getConceptList() {
        return this.concept.getConceptList();
    }

    @Override
    public void setConceptList(String conceptList) {
        this.concept.setConceptList(conceptList);
    }

    @Override
    public String getTypeId() {
        if (this.concept.getType() == null) {
            return null;
        }
        return this.concept.getType().getId();
    }

    @Override
    public void setTypeId(String typeId) {
        // do nothing here
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {
        // do nothing
    }

    @Override
    public List<String> getAlternativeUris() {
        List<ConceptpowerAlternativeId> altIds = concept.getAlternativeIds();
        List<String> ids = new ArrayList<>();
        if (altIds == null) {
            return ids;
        }
        altIds.forEach(id -> ids.add(id.getConceptUri()));
        return ids;
    }

    @Override
    public void setAlternativeUris(List<String> alternativeUris) {
        // TODO do nothing
    }

    @Override
    public String getCreatorId() {
        // TODO not yet readout
        return null;
    }

    @Override
    public void setCreatorId(String creatorId) {
        // do nothing
    }

    @Override
    public List<String> getEqualTo() {
        return this.concept.getEqualTo();
    }

    @Override
    public void setEqualTo(List<String> equalTo) {
        // do nothing for now
    }

    @Override
    public List<String> getWordnetIds() {
        // nothing
        return null;
    }

    @Override
    public void setWordnetIds(List<String> wordnetIds) {
        // do nothing
    }

    @Override
    public void setType(IConceptType type) {
        // do nothing
    }

    @Override
    public IConceptType getType() {
        return this.concept.getType();
    }

}
