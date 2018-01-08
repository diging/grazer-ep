package edu.asu.diging.grazer.core.wikidata;

import java.util.List;

import edu.asu.diging.grazer.core.exception.WikidataException;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.wikidata.impl.WikidataStatement;

public interface IWikipediaConnector {

    List<String> getPersonCodes(IConcept concept);

    List<WikidataStatement> getWikidataStatements(IConcept concept) throws WikidataException;

}