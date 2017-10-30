package edu.asu.diging.grazer.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.grazer.core.document.IDocument;
import edu.asu.diging.grazer.core.document.impl.Document;
import edu.asu.diging.grazer.dao.IDocumentDAO;
import edu.asu.diging.grazer.service.IDocumentService;

@Service
public class DocumentService implements IDocumentService {
    
    private static int idCounter = 0;

    @Autowired
    private IDocumentDAO documentDao;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.service.impl.IDocumentService#getDocuments()
     */
    @Override
    public List<IDocument> getDocuments() {
        return documentDao.getAllDocuments();
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.service.impl.IDocumentService#getDocument(java.lang.String)
     */
    @Override
    public IDocument getDocument(String id) {
        return documentDao.get(id);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.service.impl.IDocumentService#storeDocument(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void storeDocument(String label, String description, String patternTitle, String transformationTitle, String uploader) {
        IDocument doc = new Document();
        doc.setLabel(label);
        doc.setDescription(description);
        doc.setPatternTitle(patternTitle);
        doc.setTransformationTitle(transformationTitle);
        doc.setUploader(uploader);
        doc.setDate(new Date());
        doc.setId(createID());
    }
    
    public static synchronized String createID()
    {
        return String.valueOf(idCounter++);
    }    
}
