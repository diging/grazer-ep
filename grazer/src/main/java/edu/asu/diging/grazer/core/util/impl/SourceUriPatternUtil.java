package edu.asu.diging.grazer.core.util.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.amphora.IAmphoraConnector;
import edu.asu.diging.grazer.core.amphora.IAmphoraService;
import edu.asu.diging.grazer.core.exception.AmphoraException;
import edu.asu.diging.grazer.core.util.ISourceUriPatternUtil;

@Service
@PropertySource("classpath:config.properties")
public class SourceUriPatternUtil implements ISourceUriPatternUtil {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
 
    
    @Value("${embryo.project.page.pattern}")
    private String epPagePattern;

    @Value("${handle.pattern}")
    private String handlePattern;
    
    @Value("${transformed.handle.pattern}")
    private String transformedHandlePattern;
    
    @Autowired
    private IAmphoraService amphoraService;
    
    @Override
    public String getTransformedUri(String uri) {
        Pattern pattern = Pattern.compile(handlePattern);
        Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            String handleId = matcher.group(1);
            return epPagePattern.replace("{0}", handleId);
        }
        return null;
    }
    
    @Override
    public boolean isPatternUri(String uri) {
        Pattern pattern = Pattern.compile(handlePattern);
        Matcher matcher = pattern.matcher(uri);
        return matcher.matches();
    }
    
    @Override
    public String getTransformedResolvedUri(String uri) {
        String resolvedUri = amphoraService.getMappedUri(uri);
        
        if (resolvedUri != null) {
            return getTransformedUri(resolvedUri);
        }
        return uri;
    }
}
