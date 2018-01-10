package edu.asu.diging.grazer.core.wikidata.impl;

/**
 * This class represents a statement in Wikidata.
 * 
 * @author jdamerow
 *
 */
public class WikidataStatement {

    private WikidataConcept subject;
    private WikidataProperty predicate;
    private WikidataConcept object;
    
    public WikidataConcept getSubject() {
        return subject;
    }
    public void setSubject(WikidataConcept subject) {
        this.subject = subject;
    }
    public WikidataProperty getPredicate() {
        return predicate;
    }
    public void setPredicate(WikidataProperty predicate) {
        this.predicate = predicate;
    }
    public WikidataConcept getObject() {
        return object;
    }
    public void setObject(WikidataConcept object) {
        this.object = object;
    }
}
