package com.vensr.evrete.rules.builder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vensr.evrete.model.Diagnosis;
import com.vensr.evrete.model.Patient;
import com.vensr.evrete.model.Report;


public abstract class DiseaseRulesTests {
    
    @Test
    public void should_report_all_fever_no_cough() {
        
        // setup patient data
        List<Report> reports = buildFeverReports();
        List<Diagnosis> diagnosis = buildDiagnosis(reports);

        diagnosis = runRules(reports, diagnosis);

        for (Diagnosis diag : diagnosis) {
            assertEquals(true, diag.isHasFever());
            assertEquals(false, diag.isHasCough());
        }
        
    }

    @Test
    public void should_report_all_cough_no_fever() {
        
        // setup patient data
        List<Report> reports = buildCoughReports();
        List<Diagnosis> diagnosis = buildDiagnosis(reports);

        diagnosis = runRules(reports, diagnosis);

        for (Diagnosis diag : diagnosis) {
            assertEquals(false, diag.isHasFever());
            assertEquals(true, diag.isHasCough());
        }
        
    }

    @Test
    public void should_report_all_cough_all_fever() {
        
        // setup patient data
        List<Report> reports = buildFeverCoughReports();
        List<Diagnosis> diagnosis = buildDiagnosis(reports);

        diagnosis = runRules(reports, diagnosis);

        for (Diagnosis diag : diagnosis) {
            assertEquals(true, diag.isHasFever());
            assertEquals(true, diag.isHasCough());
        }
        
    }

    @Test
    public void should_fire_disease_classification_rules() {

        // setup patient data
        List<Report> reportsWithFever = buildFeverReports();
        List<Report> reportsWithCough = buildCoughReports();
        List<Report> reportsWithBothCoughAndFever = buildFeverCoughReports();

        // blend of all three types of reports with fever, cough and both
        List<Report> allReports = new ArrayList<>();
        allReports.addAll(reportsWithFever);
        allReports.addAll(reportsWithCough);
        allReports.addAll(reportsWithBothCoughAndFever);

        List<Diagnosis> diagnosis = new ArrayList<>();
        diagnosis.addAll(buildDiagnosis(allReports));

        diagnosis = runRules(allReports, diagnosis);
        
        long feverCount = diagnosis.stream().filter(diag -> diag.isHasFever() == true).count();
        long coughCount = diagnosis.stream().filter(diag -> diag.isHasCough() == true).count();
        long bothFeverAndCoughCount = diagnosis.stream().filter(diag -> diag.isHasCough() == true && diag.isHasFever() == true).count();
        long onlyFeverCount = diagnosis.stream().filter(diag -> diag.isHasFever() == true && diag.isHasCough() == false).count();
        long onlyCoughCount = diagnosis.stream().filter(diag -> diag.isHasCough() == true && diag.isHasFever() == false).count();

        // 10 should have only fever and 10 should have both fever and cough
        assertEquals(20, feverCount);

        // 10 should have only cough and 10 should have both fever and cough
        assertEquals(20, coughCount);

        // 10 should have both fever and cough
        assertEquals(10, bothFeverAndCoughCount);

        // 10 should have only fever
        assertEquals(10, onlyFeverCount);

        // 10 should have both fever and cough
        assertEquals(10, onlyCoughCount);        
        
    }

    // not used but can be used to display data
    protected void displayData(List<Diagnosis> diagnosis) {
        for(Diagnosis diagnose : diagnosis) {
            System.out.println("Report for  -> "+diagnose.getPatient().getName());
            System.out.println("Fever -> "+diagnose.isHasFever());
            System.out.println("Cough -> "+diagnose.isHasCough());
            System.out.println("##");

        }
    }

    protected abstract List<Diagnosis> runRules(List<Report> reports, List<Diagnosis> diagnosis);

    // all these private method can be moved to a builder class
    private List<Diagnosis> buildDiagnosis(List<Report> reports) {
        List<Diagnosis> diagnosisList = new ArrayList<>();
        for (Report report : reports) {
            Diagnosis diagnosis = Diagnosis.builder()
            .patient(report.getPatient())
            .build();
            diagnosisList.add(diagnosis);
        }
        return diagnosisList;
    }

    private List<Report> buildFeverCoughReports() {
        List<Report> reports = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Patient patient = new Patient("patient-"+i);
            Report report = Report.builder()
            .patient(patient)
            .hasCough(true)
            .temperature(100)
            .build();
            reports.add(report);
        }
        return reports;
    }
    private List<Report> buildCoughReports() {
        List<Report> reports = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Patient patient = new Patient("patient-"+i);
            Report report = Report.builder()
            .patient(patient)
            .hasCough(true)
            .temperature(97)
            .build();
            reports.add(report);
        }
        return reports;
    }


    private List<Report> buildFeverReports() {
        List<Report> reports = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Patient patient = new Patient("patient-"+i);
            Report report = Report.builder()
            .patient(patient)
            .hasCough(false)
            .temperature(100)
            .build();
            reports.add(report);
        }
        return reports;
    }

}
