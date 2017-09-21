package edu.asu.diging.grazer.core.rdf.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.grazer.core.rdf.IRDFTripleService;
import edu.asu.diging.grazer.core.rdf.IRepositoryService;
import net.bytebuddy.implementation.bytecode.collection.ArrayFactory;

public class RDFTripleServiceTest {

    @Mock
    private IRepositoryService mockedRepoService;

    @InjectMocks
    private IRDFTripleService serviceToTest;

    @Before
    public void setup() {
        serviceToTest = new RDFTripleService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getStatements_withResults() {
        // setup
        String URL = "TEST_URL";
        String SUBJ = "SUBJ";
        String PRED1 = "PRED1";
        String PRED2 = "PRED2";
        String OBJ = "OBJ";
        String CONTEXT1 = "CONTEXT1";
        String CONTEXT2 = "CONTEXT2";

        {
            List<Map<String, String>> results1 = new ArrayList<>();
            Map<String, String> triples1 = new HashMap<>();
            triples1.put("pred", PRED1);
            triples1.put("obj", OBJ);
            triples1.put("c", CONTEXT1);
            results1.add(triples1);

            Mockito.when(mockedRepoService
                    .queryRepository("SELECT ?pred ?obj ?c { GRAPH ?c { <" + URL
                            + "> ?pred ?obj } }"))
                    .thenReturn(results1);
        }

        {
            List<Map<String, String>> results2 = new ArrayList<>();
            Map<String, String> triples2 = new HashMap<>();
            triples2.put("pred", PRED2);
            triples2.put("subj", SUBJ);
            triples2.put("c", CONTEXT2);
            results2.add(triples2);

            Mockito.when(mockedRepoService.queryRepository(
                    "SELECT ?subj ?pred ?c { GRAPH ?c { ?subj ?pred <" + URL
                            + "> } }"))
                    .thenReturn(results2);
        }

        String C_PRED = "CPRED";
        String C_OBJ = "COBJ";

        // setup extra triples for context 1
        {
            List<Map<String, String>> resultsContext = new ArrayList<>();
            Map<String, String> triplesContext = new HashMap<>();
            triplesContext.put("pred", C_PRED);
            triplesContext.put("obj", C_OBJ);
            resultsContext.add(triplesContext);

            Mockito.when(mockedRepoService.queryRepository(
                    "SELECT ?pred ?obj { <" + CONTEXT1 + "> ?pred ?obj }"))
                    .thenReturn(resultsContext);
        }

        // ========== Tests ============
        List<RDFStatement> statements = serviceToTest.getStatements(URL);
        Assert.assertEquals(3, statements.size());

        // assert statements are correct
        RDFStatement stat1 = new RDFStatement();
        stat1.setContext(CONTEXT1);
        stat1.setSubject(URL);
        stat1.setPredicate(PRED1);
        stat1.setObject(OBJ);
        Assert.assertTrue(statements.contains(stat1));

        RDFStatement stat2 = new RDFStatement();
        stat2.setContext(CONTEXT2);
        stat2.setSubject(SUBJ);
        stat2.setPredicate(PRED2);
        stat2.setObject(URL);
        Assert.assertTrue(statements.contains(stat2));

        RDFStatement stat3 = new RDFStatement();
        stat3.setSubject(CONTEXT1);
        stat3.setPredicate(C_PRED);
        stat3.setObject(C_OBJ);
        Assert.assertTrue(statements.contains(stat3));
    }

    @Test
    public void test_getStatements_noResults() {
        // setup
        String URL = "TEST_URL";

        List<Map<String, String>> results1 = new ArrayList<>();
        Mockito.when(mockedRepoService
                .queryRepository("SELECT ?pred ?obj ?c { GRAPH ?c { <" + URL
                        + "> ?pred ?obj } }"))
                .thenReturn(results1);
        
        List<Map<String, String>> results2 = new ArrayList<>();
        Mockito.when(mockedRepoService.queryRepository(
                "SELECT ?subj ?pred ?c { GRAPH ?c { ?subj ?pred <" + URL
                        + "> } }"))
                .thenReturn(results2);
        
        // ========== Tests ============
        List<RDFStatement> statements = serviceToTest.getStatements(URL);
        Assert.assertEquals(0, statements.size());

    }
}
