package com.vensr.evrete.rules.builder;

import java.util.Collection;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;

import com.vensr.evrete.model.Diagnosis;
import com.vensr.evrete.model.Report;

public class DiseaseRulesBuilder {
    
    private KnowledgeService knowledgeService;

    private Knowledge knowledge;

    public DiseaseRulesBuilder() {
        this.knowledgeService = new KnowledgeService();
        this.knowledge = this.buildRules();
    }

    private Knowledge buildRules() {
        return knowledgeService
            .newKnowledge()
                // Rule 1: Set no disease by default
                .newRule("No Disease Rule")
                .forEach(
                    "$d", Diagnosis.class)
                .execute(ctx -> {
                    Diagnosis diagnosis = ctx.get("$d");
                    diagnosis.setHasCough(false);
                    diagnosis.setHasFever(false);
                })
                // Rule 2: if temperature >= 100 then fever
                .newRule("Fever Rule")
                .forEach(
                    "$d", Diagnosis.class,
                    "$r", Report.class)
                .where("$d.patient == $r.patient")
                .where("$r.temperature >= 100")
                .execute(ctx -> {
                    Diagnosis diagnosis = ctx.get("$d");
                    diagnosis.setHasFever(true);
                })
                // Rule 3: if temperature < 100 then no fever
                .newRule("No Fever Rule")
                .forEach(
                    "$d", Diagnosis.class,
                    "$r", Report.class)
                    .where("$d.patient == $r.patient")
                    .where("$r.temperature < 100")
                    .execute(ctx -> {
                        Diagnosis diagnosis = ctx.get("$d");
                        diagnosis.setHasFever(false);
                    })
                // Rule 4: if person is coughing, then cough
                .newRule("Cough Rule")
                .forEach(
                    "$d", Diagnosis.class,
                    "$r", Report.class)
                    .where("$d.patient == $r.patient")
                    .where("$r.hasCough == true")
                    .execute(ctx -> {
                        Diagnosis diagnosis = ctx.get("$d");
                        diagnosis.setHasCough(true);
                    })
                // Rule 5: if person is not coughing, then no cough
                .newRule("No Cough Rule")
                .forEach(
                    "$d", Diagnosis.class,
                    "$r", Report.class)
                    .where("$d.patient == $r.patient")
                    .where("$r.hasCough == false")
                    .execute(ctx -> {
                        Diagnosis diagnosis = ctx.get("$d");
                        diagnosis.setHasCough(false);
                    });
        }

        public void fire(Collection<Object> sessionData) {
            knowledge
                .newStatelessSession()
                .insert(sessionData)
                .fire();
        }
        
}
