package com.vensr.evrete.rules.builder;

import java.io.File;
import java.util.Collection;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;

public class DiseaseAnnotatedRulesBuilder {

    private KnowledgeService knowledgeService;

    private Knowledge knowledge;

    public DiseaseAnnotatedRulesBuilder() throws Exception {
        this.knowledgeService = new KnowledgeService();
        this.knowledge = this.buildRules();
    }

    private Knowledge buildRules() throws Exception {    
        return knowledgeService
            .newKnowledge(
                "JAVA-SOURCE",
                new File("rules/DiseaseRules.java").toURI().toURL());
                // can mention remote URL as well
                // new URL("https://gist.githubusercontent.com/vensr/2eeda86b9fa5e7218060a6ca47068c22/raw/d79f9ab36feff8fae5d006fdca2223f4afac5ac0/DiseaseRules"));
    }

    public void fire(Collection<Object> sessionData) {
        knowledge
            .newStatelessSession()
            .insert(sessionData)
            .fire();
    }

}
