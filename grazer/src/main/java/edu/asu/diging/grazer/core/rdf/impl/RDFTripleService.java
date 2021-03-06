package edu.asu.diging.grazer.core.rdf.impl;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.rdf.IRDFTripleService;
import edu.asu.diging.grazer.core.rdf.IRepositoryService;
import edu.asu.diging.grazer.core.rdf.IUriCreator;

/**
 * This class coordinates the storage and retrieval of transformation results into and from the
 * backend triple store.
 * 
 * @author jdamerow
 *
 */
@Service
@PropertySource(value = "classpath:/relationships.properties")
public class RDFTripleService implements IRDFTripleService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final String START_DATE = "http://schema.org/startDate";
    private final String END_DATE = "http://schema.org/endDate";
    private final String OCCUR_DATE = "http://dbpedia.org/ontology/date";
    private final String LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
    private final String COMMENT = "http://www.w3.org/2000/01/rdf-schema#comment";

    @Autowired
    private IRepositoryService repoService;
    
    @Autowired
    private IConceptpowerConnector conceptpowerConnector;
    
    @Autowired
    private IUriCreator uriCreator;

    @Autowired
    private Environment env;

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.rdf.impl.IRDFTripleService#addGraph(edu.asu.diging.grazer.core.model.impl.Graph)
     */
    @Override
    public void addGraph(Graph graph) {
        List<RDFStatement> statements = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            
            IConcept edgeConcept = conceptpowerConnector.getConcept(getConceptId(edge.getConcept()));
            IConcept subjectConcept = conceptpowerConnector.getConcept(getConceptId(edge.getSourceNode().getConceptId()));
            IConcept objectConcept = conceptpowerConnector.getConcept(getConceptId(edge.getTargetNode().getConceptId()));
            
            String relationship = env
                    .getProperty(edgeConcept.getId());
            if (relationship == null || relationship.trim().isEmpty()) {
                continue;
            }

            String contextUri = uriCreator.getContextUri(UUID.randomUUID().toString());
            String subjectUri = uriCreator.getUri(getConceptId(subjectConcept.getUri()));
            String objectUri = uriCreator.getUri(getConceptId(objectConcept.getUri()));
            
            List<String> contextUris = doesTripleExistInGraphs(subjectUri, relationship, objectUri);
            // if triple already exists, let's use the existing context uri
            if (contextUris.size() > 0) {
                contextUri = contextUris.get(0);
            } else {
                // if not, add triple
                statements.add(createStatement(subjectUri, relationship, objectUri, contextUri));
            }
            
            // add labels and description of concept
            if (!doesTripleExistInGraphs(subjectUri, LABEL, subjectConcept.getWord()).contains(contextUri)) {
                statements.add(createStatement(subjectUri, LABEL, subjectConcept.getWord(), contextUri));
            }
            if (!doesTripleExistInGraphs(subjectUri, COMMENT, subjectConcept.getDescription()).contains(contextUri)) {
                statements.add(createStatement(subjectUri, COMMENT, subjectConcept.getDescription(), contextUri));
            }
            if (!doesTripleExistInGraphs(objectUri, LABEL, objectConcept.getWord()).contains(contextUri)) {
                statements.add(createStatement(objectUri, LABEL, objectConcept.getWord(), contextUri));
            }
            if (!doesTripleExistInGraphs(objectUri, COMMENT, objectConcept.getDescription()).contains(contextUri)) {
                statements.add(createStatement(objectUri, COMMENT, objectConcept.getDescription(), contextUri));
            }

            if (edge.getStartTime() != null
                    && !edge.getStartTime().trim().isEmpty()) {
                List<String> objects = getObjects(contextUri, START_DATE);
                if (!objects.contains(edge.getStartTime())) {
                    statements.add(createStatement(contextUri,
                            START_DATE, edge.getStartTime(),
                            null));
                }
            }

            if (edge.getEndTime() != null
                    && !edge.getEndTime().trim().isEmpty()) {
                List<String> objects = getObjects(contextUri, END_DATE);
                if (!objects.contains(edge.getEndTime())) {
                    statements.add(createStatement(contextUri,
                            "http://schema.org/endDate", edge.getEndTime(), null));
                }
            }

            if (edge.getOccurred() != null
                    && !edge.getOccurred().trim().isEmpty()) {
                List<String> objects = getObjects(contextUri, OCCUR_DATE);
                if (!objects.contains(edge.getOccurred())) {
                    statements.add(createStatement(contextUri,
                            OCCUR_DATE, edge.getOccurred(),
                            null));
                }
            }
        }
        repoService.addStatements(statements);
    }
    
    private List<String> doesTripleExistInGraphs(String subject, String predicate, String object) {
        String objectPart;
        try {
            objectPart = object.startsWith("http://") ? "<" + object + ">" : "\"" + URLEncoder.encode(object, "UTF-8") + "\"";
        } catch (UnsupportedEncodingException e) {
            logger.error("Could not encode string.", e);
            objectPart = object.startsWith("http://") ? "<" + object + ">" : "\"" + URLEncoder.encode(object) + "\"";
        }
        String query = "SELECT ?c { GRAPH ?c { <" + subject + "> <" + predicate + "> " + objectPart + " } }";
        List<Map<String, String>> results = repoService.queryRepository(query);
        List<String> contexts = new ArrayList<>();
        results.forEach(map -> contexts.add(map.get("c")));
        return contexts;
    }
    
    private List<String> getObjects(String subject, String predicate) {
        String query = "SELECT ?obj { <" + subject + "> <" + predicate + "> ?obj }";
        List<Map<String, String>> results = repoService.queryRepository(query);
        List<String> objects = new ArrayList<>();
        results.forEach(map -> objects.add(map.get("c")));
        return objects;
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.rdf.impl.IRDFTripleService#getStatements(java.lang.String)
     */
    @Override
    public List<RDFStatement> getStatements(String conceptUri) {
        List<RDFStatement> statements = new ArrayList<>();
        
        // find statements where concept is subject
        String query = "SELECT ?pred ?obj ?c { GRAPH ?c { <" + conceptUri + "> ?pred ?obj } }";
        List<Map<String, String>> results = repoService.queryRepository(query);
        results.forEach(map -> map.put("subj", conceptUri));
        query = "SELECT ?subj ?pred ?c { GRAPH ?c { ?subj ?pred <" + conceptUri + "> } }";
        List<Map<String, String>> resultsContext = repoService.queryRepository(query);
        resultsContext.forEach(map -> map.put("obj", conceptUri));
        results.addAll(resultsContext);
        
        List<String> completedContexts = new ArrayList<>();
        results.forEach(map -> {
            RDFStatement statement = new RDFStatement();
            statements.add(statement);
            statement.setSubject(conceptUri);
            createStatement(map, statement);
            
            if (statement.getContext() != null && !completedContexts.contains(statement.getContext())) {
                // add context statements
                String contextQuery = "SELECT ?pred ?obj { <" + statement.getContext() + "> ?pred ?obj }";
                List<Map<String, String>> contextResults = repoService.queryRepository(contextQuery);
                contextResults.forEach(contextMap -> {
                    RDFStatement contextStatement = new RDFStatement();
                    statements.add(contextStatement);
                    contextStatement.setSubject(statement.getContext());
                    createStatement(contextMap, contextStatement);
                });
                completedContexts.add(statement.getContext());
            }
        });
        
        return statements;
    }
    
    @Override
    public void runSparqlQuery(String query, String mimeType, OutputStream stream) {
        repoService.runSparqlQuery(stream, query, mimeType);
    }

    protected void createStatement(Map<String, String> map,
            RDFStatement statement) {
        for (String key : map.keySet()) {
            if (key.equals("subj")) {
                statement.setSubject(map.get(key));
            } else if (key.equals("pred")) {
                statement.setPredicate(map.get(key));
            } else if (key.equals("obj")) {
                statement.setObject(map.get(key));
            } else {
                statement.setContext(map.get(key));
            }
        }
    }

    private RDFStatement createStatement(String subject, String predicate,
            String object, String context) {
        RDFStatement statement = new RDFStatement();
        statement.setSubject(subject);
        statement.setObject(object);
        statement.setPredicate(predicate);
        statement.setContext(context);
        return statement;
    }

    private String getConceptId(String conceptUri) {
        if (conceptUri == null) {
            return null;
        }
        return conceptUri.substring(conceptUri.lastIndexOf("/") + 1);
    }
}
