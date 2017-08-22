package edu.asu.diging.grazer.core.rdf.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Graph;

@Service
@PropertySource(value = "classpath:relationships.properties")
public class RDFTripleService {

    @Autowired
    private RepositoryService repoService;
    
    @Autowired
    private Environment env;
    
    public void addGraph(Graph graph, String conceptUri) {
        List<RDFStatement> statements = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            String relationship = env.getProperty(edge.getConcept());
            if (relationship == null || relationship.trim().isEmpty()) {
                continue;
            }
            
            RDFStatement statement = new RDFStatement();
            statement.setSubject(edge.getSourceNode().getUri());
            statement.setObject(edge.getTargetNode().getUri());
            statement.setPredicate(relationship);
            statement.setContext(conceptUri);
            statements.add(statement);
        }
        repoService.addStatements(statements);
    }
}
