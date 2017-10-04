package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.asu.diging.grazer.core.model.IConceptType;

public class ConceptpowerConcept { 
	
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConceptpowerConcept other = (ConceptpowerConcept) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    private String id;
    @JsonProperty("concept_uri")
    private String conceptUri;
    private String lemma;
    private String pos;
    private String description;
    private String conceptList;
    private boolean deleted;
    @JsonProperty("equal_to")
    private String equalTo;
    @JsonProperty("similar_to")
    private String similarTo;
    @JsonProperty("synonym_ids")
    private String synonymIds;
    private List<String> wordnetIds;
    
    private ConceptpowerConceptType type;
    private List<ConceptpowerAlternativeId> alternativeIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConceptUri() {
        return conceptUri;
    }

    public void setConceptUri(String conceptUri) {
        this.conceptUri = conceptUri;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConceptList() {
        return conceptList;
    }

    public void setConceptList(String conceptList) {
        this.conceptList = conceptList;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<String> getEqualTo() {
        if (equalTo == null) {
            return null;
        }
        return Arrays.asList(equalTo.split(","));
    }

    public void setEqualTo(String equalTo) {
        this.equalTo = equalTo;
    }

    public String getSimilarTo() {
        return similarTo;
    }

    public void setSimilarTo(String similarTo) {
        this.similarTo = similarTo;
    }

    public String getSynonymIds() {
        return synonymIds;
    }

    public void setSynonymIds(String synonymIds) {
        this.synonymIds = synonymIds;
    }

    public IConceptType getType() {
        return type;
    }

    public void setType(ConceptpowerConceptType type) {
        this.type = type;
    }
    
    public List<ConceptpowerAlternativeId> getAlternativeIds() {
        return alternativeIds;
    }

    public void setAlternativeIds(List<ConceptpowerAlternativeId> alternativeIds) {
        this.alternativeIds = alternativeIds;
    }

    public List<String> getWordnetId() {
        return wordnetIds;
    }
	
    public void setWordnetId(List<String> wordnetIds) {
        this.wordnetIds = wordnetIds;
    }

    public String getCreatorId() {
        // TODO Auto-generated method stub
        return null;
    }
}
