package edu.asu.diging.grazer.core.rdf;

public interface IUriCreator {

    String getUri(String conceptId);

    String getContextUri(String id);

}