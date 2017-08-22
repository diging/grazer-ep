package edu.asu.diging.grazer.core.rdf.impl;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "classpath:config.properties")
public class RepositoryService {

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

    public void addStatements(List<RDFStatement> statements) {
        try (RepositoryConnection con = repository.getConnection()) {
            for (RDFStatement statement : statements) {
                con.add(valueFactory.createIRI(statement.getSubject()),
                        valueFactory.createIRI(statement.getPredicate()),
                        valueFactory.createIRI(statement.getObject()),
                        valueFactory.createIRI(statement.getContext()));
            }
        }
    }

}
