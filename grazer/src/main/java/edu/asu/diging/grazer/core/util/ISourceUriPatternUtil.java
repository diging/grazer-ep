package edu.asu.diging.grazer.core.util;

public interface ISourceUriPatternUtil {

    String getTransformedUri(String uri);

    boolean isPatternUri(String uri);

    String getTransformedResolvedUri(String uri);

}