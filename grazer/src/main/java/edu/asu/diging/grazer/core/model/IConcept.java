package edu.asu.diging.grazer.core.model;

import java.time.OffsetDateTime;
import java.util.List;

public interface IConcept {

    String getId();

    void setId(String id);

    String getUri();

    void setUri(String uri);

    String getWord();

    void setWord(String word);

    String getPos();

    void setPos(String pos);

    String getDescription();

    void setDescription(String description);

    String getConceptList();

    void setConceptList(String conceptList);

    String getTypeId();

    void setTypeId(String typeId);

    boolean isDeleted();

    void setDeleted(boolean deleted);

    List<String> getAlternativeUris();

    void setAlternativeUris(List<String> list);

    String getCreatorId();

    void setCreatorId(String creatorId);

    List<String> getEqualTo();

    void setEqualTo(List<String> equalTo);

    List<String> getWordnetIds();

    void setWordnetIds(List<String> wordnetIds);

    void setType(IConceptType type);

    IConceptType getType();

    void setLastUpdated(OffsetDateTime lastUpdated);

    OffsetDateTime getLastUpdated();

    void setSimilarTo(List<String> similarTo);

    List<String> getSimilarTo();

}