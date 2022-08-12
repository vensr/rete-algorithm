package com.vensr.evrete.rules.builder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;

import com.vensr.evrete.model.Diagnosis;
import com.vensr.evrete.model.Report;


public class DiseaseAnnotatedRulesBuilderTest extends DiseaseRulesTests {
    
    private DiseaseAnnotatedRulesBuilder annotatedRulesBuilder;

    @Before
    public void setUp() {
        try {
            annotatedRulesBuilder = new DiseaseAnnotatedRulesBuilder();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<Diagnosis> runRules(List<Report> reports, List<Diagnosis> diagnosis) {
        // setup reports data
        Collection<Object> sessionData = new ArrayList<>();
        sessionData.addAll(reports);
        sessionData.addAll(diagnosis);

        // call the rules engine to fire the rules
        annotatedRulesBuilder.fire(sessionData);
        return diagnosis;
    }

}
