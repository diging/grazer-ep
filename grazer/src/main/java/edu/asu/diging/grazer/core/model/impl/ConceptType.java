package edu.asu.diging.grazer.core.model.impl;

import edu.asu.diging.grazer.core.model.IConceptType;

public class ConceptType implements IConceptType {

    private String uri;
    private String id;
    private String name;
    private String description;
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getUri()
     */
    @Override
    public String getUri() {
        return uri;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setUri(java.lang.String)
     */
    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
