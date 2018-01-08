package edu.asu.diging.grazer.core.wikidata;

import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;

public interface IWikidataFetcherFactory {

    WikibaseDataFetcher createFetcher();

}