package edu.asu.diging.grazer.core.wikidata.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Snak;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StringValue;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import edu.asu.diging.grazer.core.exception.WikidataException;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.wikidata.IWikidataFetcherFactory;
import edu.asu.diging.grazer.core.wikidata.IWikidataConnector;

/**
 * Implementation of {@link IWikidataConnector} using the Wikidata Toolkit. See
 * https://github.com/Wikidata/Wikidata-Toolkit and
 * https://www.mediawiki.org/wiki/Wikidata_Toolkit
 * 
 * @author jdamerow
 *
 */
@Service
@PropertySource("classpath:/config.properties")
public class WikidataConnector implements IWikidataConnector {

    private final String LANGUAGE = "en";

    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${wikipedia.api.pageinfo}")
    private String wikidataApiPageinfo;

    @Value("${wikipedia.url.pattern}")
    private String wikipediaUrlPattern;

    @Value("${wikipedia.wikibaseitem.pattern}")
    private String wikipediaWikibaseItemPattern;

    @Value("${wikidata.entry.url}")
    private String wikidataEntryUrl;

    @Autowired
    private IWikidataFetcherFactory fetcherFactory;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.grazer.core.wikidata.impl.IWikipediaConnector#
     * getPersonCodes(edu.asu.diging.grazer.core.model.IConcept)
     */
    @Override
    public List<String> getPersonCodes(IConcept concept) {
        List<String> similarTos = concept.getSimilarTo();
        List<String> personCodes = new ArrayList<>();
        for (String similarTo : similarTos) {
            Pattern pattern = Pattern.compile(wikipediaUrlPattern);
            Matcher matcher = pattern.matcher(similarTo);

            String title = null;
            if (matcher.find()) {
                title = matcher.group(1);
            }

            if (title != null) {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(
                            new URI(wikidataApiPageinfo.replace("{0}", title)),
                            String.class);
                    String responseString = response.getBody();
                    Pattern patternWikibaseItem = Pattern
                            .compile(wikipediaWikibaseItemPattern);
                    Matcher matcherWikibaseItem = patternWikibaseItem
                            .matcher(responseString);

                    String wikibaseItem = null;
                    if (matcherWikibaseItem.find()) {
                        wikibaseItem = matcherWikibaseItem.group(1);
                    }
                    if (wikibaseItem != null) {
                        personCodes.add(wikibaseItem);
                    }
                } catch (RestClientException | URISyntaxException e) {
                    logger.error("Could not get wiki page info.", e);
                }
            }

        }

        return personCodes;
    }

    @Override
    @Cacheable(value = "wikidata")
    public List<WikidataStatement> getWikidataStatements(IConcept concept)
            throws WikidataException {
        List<String> personCodes = getPersonCodes(concept);

        List<WikidataStatement> statements = new ArrayList<>();

        WikibaseDataFetcher wbdf = fetcherFactory.createFetcher();
        wbdf.getFilter().setLanguageFilter(Collections.singleton(LANGUAGE));

        for (String code : personCodes) {
            EntityDocument entity = null;
            try {
                entity = wbdf.getEntityDocument(code);
            } catch (MediaWikiApiErrorException e1) {
                throw new WikidataException(e1);
            }

            if (entity instanceof ItemDocument) {
                WikidataConcept wdConcept = createWikidataConcept(entity);

                Iterator<Statement> itemStatements = ((ItemDocument) entity)
                        .getAllStatements();
                List<String> idsToFetch = new ArrayList<>();

                while (itemStatements.hasNext()) {
                    statements.add(createWikidataStatement(wdConcept,
                            itemStatements, idsToFetch));
                }

                Map<String, EntityDocument> entityResults = null;
                try {
                    entityResults = wbdf.getEntityDocuments(idsToFetch);
                } catch (MediaWikiApiErrorException e) {
                    throw new WikidataException(e);
                }

                if (entityResults != null) {
                    for (WikidataStatement statement : statements) {
                        fillStatements(entityResults, statement);
                    }
                }
            }
        }

        return statements;
    }

    protected void fillStatements(Map<String, EntityDocument> entityResults,
            WikidataStatement statement) {
        EntityDocument predicateDoc = entityResults
                .get(statement.getPredicate().getId());
        if (predicateDoc instanceof PropertyDocument) {
            statement.getPredicate().setLabel(((PropertyDocument) predicateDoc)
                    .getLabels().get(LANGUAGE).getText());
        }

        if (statement.getObject() != null) {
            EntityDocument objectDoc = entityResults
                    .get(statement.getObject().getId());
            if (objectDoc instanceof ItemDocument) {
                statement.getObject().setLabel(((ItemDocument) objectDoc)
                        .getLabels().get(LANGUAGE).getText());
            }
        }
    }

    protected WikidataStatement createWikidataStatement(
            WikidataConcept wdConcept, Iterator<Statement> itemStatements,
            List<String> idsToFetch) {
        WikidataStatement statement = new WikidataStatement();
        statement.setSubject(wdConcept);

        Statement st = itemStatements.next();
        Snak relation = st.getClaim().getMainSnak();
        String propertyId = relation.getPropertyId().getId();

        WikidataProperty wdProperty = new WikidataProperty();
        wdProperty.setId(propertyId);
        idsToFetch.add(propertyId);
        statement.setPredicate(wdProperty);

        org.wikidata.wdtk.datamodel.interfaces.Value value = relation
                .getValue();
        if (value != null) {
            WikidataConcept wdObject = new WikidataConcept();
            statement.setObject(wdObject);
            if (value instanceof EntityIdValue) {
                wdObject.setId(((EntityIdValue) value).getId());
                idsToFetch.add(wdObject.getId());
            } else if (value instanceof StringValue) {
                wdObject.setLabel(((StringValue) value).getString());
            }
        }

        return statement;
    }

    protected WikidataConcept createWikidataConcept(EntityDocument entity) {
        WikidataConcept wdConcept = new WikidataConcept();
        wdConcept.setId(((ItemDocument) entity).getItemId().getId());
        wdConcept.setLabel(
                ((ItemDocument) entity).getLabels().get(LANGUAGE).getText());
        Map<String, MonolingualTextValue> descriptions = ((ItemDocument) entity)
                .getDescriptions();
        if (descriptions != null && descriptions.get(LANGUAGE) != null) {
            wdConcept.setDescription(descriptions.get(LANGUAGE).getText());
        }
        return wdConcept;
    }
}
