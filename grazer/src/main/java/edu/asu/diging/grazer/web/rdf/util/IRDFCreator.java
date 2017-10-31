package edu.asu.diging.grazer.web.rdf.util;

import java.util.List;

import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;

public interface IRDFCreator {

    public final static String RDFXML = "application/rdf+xml";
    public final static String TURTLE = "text/turtle";
    public final static String XML = "application/xml";
    public final static String TRIX = "application/trix";
    public final static String TRIG = "application/trig";
    public final static String NQUADS = "application/n-quads";

    String getRDF(List<RDFStatement> statements, String format);

    String createList(String subject, List<String> list, String format);

}