package edu.asu.diging.grazer.core.wikidata.impl;

import org.springframework.stereotype.Service;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;

import edu.asu.diging.grazer.core.wikidata.IWikidataFetcherFactory;

@Service
public class WikidataFetcherFactory implements IWikidataFetcherFactory {

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.wikidata.impl.IWikidataFetcherFactory#createFetcher()
     */
    @Override
    public WikibaseDataFetcher createFetcher() {
        return WikibaseDataFetcher.getWikidataDataFetcher();
    }
}
