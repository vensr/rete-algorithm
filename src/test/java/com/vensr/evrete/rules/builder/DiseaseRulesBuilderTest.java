package com.vensr.evrete.rules.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vensr.evrete.model.Diagnosis;
import com.vensr.evrete.model.Report;

public class DiseaseRulesBuilderTest extends DiseaseRulesTests {
    
    private DiseaseRulesBuilder diseaseRulesBuilder = new DiseaseRulesBuilder();

    public List<Diagnosis> runRules(List<Report> reports, List<Diagnosis> diagnosis) {
        // setup reports data
        Collection<Object> sessionData = new ArrayList<>();
        sessionData.addAll(reports);
        sessionData.addAll(diagnosis);

        // call the rules engine to fire the rules
        diseaseRulesBuilder.fire(sessionData);
        return diagnosis;
    }
}
