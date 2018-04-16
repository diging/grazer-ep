package edu.asu.diging.grazer.core.conceptpower;

import java.util.List;

public interface IConceptpowerService {
    public abstract List<ISearchResult> search(String word);
}
