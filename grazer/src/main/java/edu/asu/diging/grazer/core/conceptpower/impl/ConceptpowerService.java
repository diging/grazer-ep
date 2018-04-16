package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.conceptpower.IConceptpowerService;
import edu.asu.diging.grazer.core.conceptpower.ISearchResult;

public class ConceptpowerService implements IConceptpowerService {
    
    @Autowired
    private IConceptpowerConnector conceptpowerConnector;

    @Override
    public List<ISearchResult> search(String term) {

        List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
        
        ConceptpowerConcepts cpc = conceptpowerConnector.search(term);
        if(cpc != null)
        {
            List<ConceptpowerConcept> conceptEntries = cpc.getConceptEntries();
            for(ConceptpowerConcept ce : conceptEntries)
            {
                ISearchResult searchResult = new SearchResult();
                searchResult.setName(ce.getLemma());
                searchResult.setId(ce.getId());
                searchResult.setDescription(ce.getDescription());
                searchResults.add(searchResult);
            }
        }
        return searchResults;
    }
}
