package edu.asu.diging.grazer.core.graphs;

import java.io.IOException;

import edu.asu.diging.grazer.core.model.impl.Edge;

public interface IPredicateProcessor {

    void setPredicateName(Edge edge, String transformationName);

}