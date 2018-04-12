package edu.asu.diging.grazer.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcepts;
import edu.asu.diging.grazer.core.conceptpower.IConceptCollectionManager;
import edu.asu.diging.grazer.core.conceptpower.ISearchResult;
import edu.asu.diging.grazer.core.conceptpower.IService;
import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcept;

@Service
public class ConceptpowerService implements IService {

    @Autowired
    private IConceptCollectionManager collectionManager;
    
    private final static String SERVICE_ID = "edu.asu.conceptpower";
    private final static String SERVICE_NAME = "ConceptPower";
    
    @Override
    public String getServiceId() {

        return SERVICE_ID;
    }
    
    @Override
    public String getName() {

        return SERVICE_NAME;
    }

    @Override
    public List<ISearchResult> search(String term) {

        List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
        
        ConceptpowerConcepts cpc = collectionManager.search(term);
        if(cpc!=null)
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