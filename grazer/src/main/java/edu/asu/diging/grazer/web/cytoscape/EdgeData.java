package edu.asu.diging.grazer.web.cytoscape;

public class EdgeData extends Data {

    private String source;
    private String target;
    
    public EdgeData(String source, String target, String id, String label) {
        super(id, label);
        this.source = source;
        this.target = target;
    }
    
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
}
