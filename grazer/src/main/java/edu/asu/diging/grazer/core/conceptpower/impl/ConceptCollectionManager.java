package edu.asu.diging.grazer.core.conceptpower.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.conceptpower.IConceptCollectionManager;
import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;

@Service
public class ConceptCollectionManager implements IConceptCollectionManager {

    @Autowired
    private IConceptpowerConnector conceptpowerConnector;


    /**
     * This method searches the items and its part of speech in the concept
     * power database
     * 
     * @param item
     *            - concept collection item
     * @param pos
     *            - part of speech of item word
     */
    @Override
    public ConceptpowerConcepts search(String item) {
        if (item == null || item.isEmpty())
            return null;

        return conceptpowerConnector.search(item);
    }
}