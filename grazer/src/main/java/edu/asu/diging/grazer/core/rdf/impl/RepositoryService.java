package edu.asu.diging.grazer.core.rdf.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.rdf.IRepositoryService;

@Service
@PropertySource(value = "classpath:config.properties")
public class RepositoryService implements IRepositoryService {

    @Autowired
    private Environment env;

    private Repository repository;
    private ValueFactory valueFactory;

    @PostConstruct
    public void init() {
        String storeDir = env.getProperty("rdf.store.dir");
        String indexes = "spoc,posc,cosp";
        repository = new SailRepository(
                new NativeStore(new File(storeDir), indexes));
        repository.initialize();
        valueFactory = repository.getValueFactory();
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.rdf.impl.IRepositoryService#addStatements(java.util.List)
     */
    @Override
    public void addStatements(List<RDFStatement> statements) {
        try (RepositoryConnection con = repository.getConnection()) {
            for (RDFStatement statement : statements) {
                if (statement.getContext() != null) {
                con.add(valueFactory.createIRI(statement.getSubject()),
                        valueFactory.createIRI(statement.getPredicate()),
                        statement.getObject().startsWith("http://") ? valueFactory.createIRI(statement.getObject()) : valueFactory.createLiteral(statement.getObject()),
                        valueFactory.createIRI(statement.getContext()));
                } else {
                    con.add(valueFactory.createIRI(statement.getSubject()),
                            valueFactory.createIRI(statement.getPredicate()),
                            statement.getObject().startsWith("http://") ? valueFactory.createIRI(statement.getObject()) : valueFactory.createLiteral(statement.getObject()));
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.rdf.impl.IRepositoryService#queryRepository(java.lang.String)
     */
    @Override
    public List<Map<String, String>> queryRepository(String query) {
        List<Map<String, String>> results = new ArrayList<>();
        try (RepositoryConnection con = repository.getConnection()) {
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL,
                    query);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) { // iterate over the result
                    Map<String, String> values = new HashMap<>();
                    BindingSet bindingSet = result.next();
                    Set<String> bindings = bindingSet.getBindingNames();
                    for (String binding : bindings) {
                        values.put(binding, bindingSet.getValue(binding).stringValue());
                    }
                    results.add(values);
                }
            }
            return results;
        }
    }

    @PreDestroy
    public void shutdown() {
        repository.shutDown();
    }

}
