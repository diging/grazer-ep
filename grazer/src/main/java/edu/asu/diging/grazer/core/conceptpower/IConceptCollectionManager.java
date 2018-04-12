package edu.asu.diging.grazer.core.conceptpower;

import edu.asu.diging.grazer.core.conceptpower.impl.ConceptpowerConcepts;

public interface IConceptCollectionManager {

    /**
     * Method is used to search the concept power rest api Input: item and pos
     * of the word Output: ConceptpowerReply which contains a list of items
     * 
     * @param item
     * @param pos
     * @return
     */
    public ConceptpowerConcepts search(String item);

}