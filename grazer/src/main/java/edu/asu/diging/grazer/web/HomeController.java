package edu.asu.diging.grazer.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.grazer.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.grazer.core.graphs.IGraphDBConnection;
import edu.asu.diging.grazer.core.model.IConcept;
import edu.asu.diging.grazer.core.model.impl.Edge;
import edu.asu.diging.grazer.core.model.impl.Graph;
import edu.asu.diging.grazer.core.model.impl.Node;
import edu.asu.diging.grazer.core.quadriga.IQuadrigaConnector;
import edu.asu.diging.grazer.web.cytoscape.Data;
import edu.asu.diging.grazer.web.cytoscape.EdgeData;
import edu.asu.diging.grazer.web.cytoscape.GraphElement;

@Controller
@PropertySource(value="classpath:/config.properties")
public class HomeController {
    
    @Autowired
    private IQuadrigaConnector quadrigaConnector;
    
    @Autowired
    private IGraphDBConnection graphDbConnection;
    
    @Autowired
    private IConceptpowerCache conceptCache;
    
    @Value("${concepts.type.person}")
    private String personType;

    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home(Model model) {           
        List<String> uris = graphDbConnection.getAllPersons();
        List<IConcept> concepts = new ArrayList<>();
        for (String uri : uris) {
            IConcept concept = conceptCache.getConceptByUri(uri);
            concepts.add(concept);
        }
        model.addAttribute("concepts", concepts);
        model.addAttribute("count", concepts.size());
        return "home";
    }
    
    @RequestMapping(value = "/persons")
    public ResponseEntity<List<IConcept>> checkPersons(@RequestParam("pollUrl") String pollUrl) {
        List<IConcept> concepts = quadrigaConnector.checkPersonsResult(pollUrl);
        if (concepts == null) {
            return new ResponseEntity<List<IConcept>>(HttpStatus.OK);
        }
        
        Collections.sort(concepts, new Comparator<IConcept>() {

            @Override
            public int compare(IConcept o1, IConcept o2) {
                return o1.getWord().compareTo(o2.getWord());
            }
        });
        
        return new ResponseEntity<List<IConcept>>(concepts, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/persons/network")
    public ResponseEntity<Collection<GraphElement>> getPersonNetwork() {
        List<String> uris = graphDbConnection.getAllPersons();
        Map<String, GraphElement> elements = new HashMap<>();
        for (String uri : uris) {
            List<Graph> graphs = graphDbConnection.getGraphs(uri);
            for (Graph graph : graphs) {
                for (Edge edge : graph.getEdges()) {
                    Node sourceNode = edge.getSourceNode();
                    Node targetNode = edge.getTargetNode();
                    if (!(sourceNode.getType().equals(personType) && targetNode.getType().equals(personType))) {
                        continue;
                    }
                    GraphElement sourceElem = elements.get(sourceNode.getConceptId());
                    if (sourceElem == null) {
                        IConcept concept = conceptCache.getConceptById(sourceNode.getConceptId());
                        sourceElem = new GraphElement(new Data(concept.getId(), sourceNode.getLabel()));
                        elements.put(sourceNode.getConceptId(), sourceElem);
                    }
                    GraphElement targetElem = elements.get(targetNode.getConceptId());
                    if (targetElem == null) {
                        IConcept concept = conceptCache.getConceptById(targetNode.getConceptId());
                        targetElem = new GraphElement(new Data(concept.getId(), targetNode.getLabel()));
                        elements.put(targetNode.getConceptId(), targetElem);
                    }
                    elements.put(edge.getId() + "", new GraphElement(new EdgeData(sourceElem.getData().getId(), targetElem.getData().getId(), edge.getId() + "", "")));
                }
            }
        }
        
        return new ResponseEntity<Collection<GraphElement>>(elements.values(), HttpStatus.OK);
    }
}
