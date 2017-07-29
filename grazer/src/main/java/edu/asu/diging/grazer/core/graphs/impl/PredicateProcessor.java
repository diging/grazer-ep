package edu.asu.diging.grazer.core.graphs.impl;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.graphs.IPredicateProcessor;
import edu.asu.diging.grazer.core.model.impl.Edge;

@Service
@PropertySource("classpath:config.properties")
public class PredicateProcessor implements IPredicateProcessor {
    
    private final String represenationFolder = "representations/";
    private final String fileEnding = ".txt";
    
    @Value("${embryo.project.page.pattern}")
    private String epPagePattern;

    @Value("${handle.pattern}")
    private String handlePattern;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.impl.IPredicateProcessor#setPredicateName(edu.asu.diging.grazer.core.model.impl.Edge, java.lang.String)
     */
    @Override
    public void setPredicateName(Edge edge, String transformationName) {
        Resource transResource = new ClassPathResource(represenationFolder + transformationName + fileEnding);
        Properties props = new Properties();
        try {
            props.load(transResource.getInputStream());
        } catch (IOException e) {
            logger.error("Can't load relationship file. Returning.", e);
            return;
        }
        
        String conceptId = "";
        if (edge.getConcept() != null) {
            conceptId = edge.getConcept().substring(edge.getConcept().lastIndexOf("/") + 1);
        }
        String edgeName = props.getProperty("relationship_name_" + conceptId);
        if (edgeName != null) {
            edge.setLabel(edgeName.replace("${link}", edge.getLabel()));
            return;
        }
        
        edgeName = props.getProperty("relationship_name");
        if (edgeName != null) {
            edge.setLabel(edgeName.replace("${link}", edge.getLabel()));
        }       
    }
    
    @Override
    public void setPredicateUri(Edge edge) {
        Pattern pattern = Pattern.compile(handlePattern);
        Matcher matcher = pattern.matcher(edge.getSourceUri());
        if (matcher.matches()) {
            String handleId = matcher.group(1);
            edge.setSourceUri(epPagePattern.replace("{0}", handleId));
        }
    }
}
