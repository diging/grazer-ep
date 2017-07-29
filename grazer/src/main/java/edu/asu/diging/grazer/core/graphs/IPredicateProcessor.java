package edu.asu.diging.grazer.core.graphs;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.asu.diging.grazer.core.model.impl.Edge;

public interface IPredicateProcessor {

    void setPredicateName(Edge edge, String transformationName);

    void setPredicateUri(Edge edge);

}