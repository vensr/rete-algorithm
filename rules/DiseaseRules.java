package com.vensr.evrete;

import org.evrete.dsl.annotation.Rule;
import org.evrete.dsl.annotation.RuleSet;
import org.evrete.dsl.annotation.Where;

import com.vensr.evrete.model.Diagnosis;
import com.vensr.evrete.model.Report;

@RuleSet(value = "Disease Rule Set", defaultSort = RuleSet.Sort.BY_NAME)
public class DiseaseRules {

    @Rule()
    public void rule1_setNoDisease(Diagnosis $d) {
        $d.setHasCough(false);
        $d.setHasFever(false);
    }

    @Rule()
    @Where("$d.patient == $r.patient && $r.temperature >= 100")
    public void rule2_feverRule(Diagnosis $d, Report $r) {
        $d.setHasFever(true);
    }

    @Rule()
    @Where("$d.patient == $r.patient && $r.temperature < 100")
    public void rule3_noFeverRule(Diagnosis $d, Report $r) {
        $d.setHasFever(false);
    }

    @Rule()
    @Where("$d.patient == $r.patient && $r.hasCough == true")
    public void rule4_coughRule(Diagnosis $d, Report $r) {
        $d.setHasCough(true);
    }

    @Rule()
    @Where("$d.patient == $r.patient && $r.hasCough == false")
    public void rule5_noCoughRule(Diagnosis $d, Report $r) {
        $d.setHasCough(false);
    }

}
