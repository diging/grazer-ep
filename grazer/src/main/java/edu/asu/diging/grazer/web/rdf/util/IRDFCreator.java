package edu.asu.diging.grazer.web.rdf.util;

import java.util.List;

import edu.asu.diging.grazer.core.rdf.impl.RDFStatement;

public interface IRDFCreator {

    public final static String RDFXML = "application/rdf+xml";
    public final static String TURTLE = "text/turtle";

    String getRDF(List<RDFStatement> statements, String format);

}