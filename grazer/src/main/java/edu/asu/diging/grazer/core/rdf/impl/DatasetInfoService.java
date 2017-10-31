package edu.asu.diging.grazer.core.rdf.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.rdf.IUriCreator;

@Service
@PropertySource("classpath:config.properties")
public class DatasetInfoService implements IDatasetInfoService {
    
    @Autowired
    private IUriCreator uriCreator;
    
    @Value("${quadriga.project.id}")
    private String projectId;
    
    @Value("${quadriga.project.url}")
    private String projectUrl;

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.rdf.impl.IDatasetInfoService#getInfoStatements()
     */
    @Override
    public List<RDFStatement> getInfoStatements() {
        List<RDFStatement> statements = new ArrayList<>();
        
        {
            RDFStatement statement = new RDFStatement();
            statement.setSubject(uriCreator.getBaseUri());
            statement.setPredicate("http://purl.org/dc/elements/1.1/source");
            statement.setObject(projectUrl);
            statements.add(statement);
        }
        
       return statements;
    }
}
