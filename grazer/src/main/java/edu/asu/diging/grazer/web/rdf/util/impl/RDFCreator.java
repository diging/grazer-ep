package edu.asu.diging.grazer.web.rdf.util.impl;

import java.io.StringWriter;
import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;
import edu.asu.diging.grazer.web.rdf.util.IRDFCreator;

@Service
public class RDFCreator implements IRDFCreator {
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.api.util.impl.IRDFCreator#getRDF(java.util.List)
     */
    @Override
    public String getRDF(List<RDFStatement> statements, String format) {
        ValueFactory factory = SimpleValueFactory.getInstance();

        Model model = new LinkedHashModel();
        for (RDFStatement statement : statements) {
            // subject and predicate should never be a literal
            // FIXME: if subject is not a URI this should throw an exception
            IRI subject = factory.createIRI(statement.getSubject());
            IRI predicate = factory.createIRI(statement.getPredicate());
            Value object = createValue(statement.getObject());
            if (statement.getContext() != null) {
                IRI context = factory.createIRI(statement.getContext());
                model.add(subject, predicate, object, context);
            }
            model.add(subject, predicate, object);
        }

        StringWriter writer = new StringWriter();
        RDFFormat rdfFormat = RDFFormat.RDFXML;
        if (format.equals(TURTLE)) {
            rdfFormat = RDFFormat.TURTLE;
        }
        Rio.write(model, writer, rdfFormat);
        return writer.toString();
    }

    protected Value createValue(String value) {
        ValueFactory factory = SimpleValueFactory.getInstance();
        if (value.startsWith("http://")) {
            return factory.createIRI(value);
        }
        return factory.createLiteral(value);
    }
}
