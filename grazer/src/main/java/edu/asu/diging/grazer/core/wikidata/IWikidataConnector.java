package edu.asu.diging.grazer.core.wikidata;

import java.util.List;

import edu.asu.diging.grazer.core.exception.WikidataException;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.wikidata.impl.WikidataStatement;

/**
 * Connector class for Wikidata. This class provides functionality to retrieve statements from
 * Wikidata about a specific concept.
 * 
 * See https://www.wikidata.org/wiki/Wikidata:Data_access for Wikidata info.
 * 
 * @author jdamerow
 *
 */
public interface IWikidataConnector {

    /**
     * This method returns the Wikidata code for a given concept. To work correctly, the concept
     * should have a Wikipedia URL.
     * 
     * @param concept The concept for which a Wikidata code should be retrieved.
     * @return
     */
    List<String> getPersonCodes(IConcept concept);

    /**
     * This method retrieves all statements for a given concept. To work correctly, the concept
     * should have a Wikipedia URL.
     * 
     * @param concept The concept for which a Wikidata code should be retrieved.
     * @return
     * @throws WikidataException
     */
    List<WikidataStatement> getWikidataStatements(IConcept concept) throws WikidataException;

}