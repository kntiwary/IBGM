package com.goalsr.kidsgrowth.kidsgrowthcharts.db;

import java.util.List;

/**
 * Created by Shrishail on 2/9/2017.
 */

public class ModelGraphData {

    private float patientModelHeight;

    private float patientModelWeight;

    private float patientModelBMI;

    private float patientModelHeadCircumference;

    private float patientModelAge;

    private int patientModelAgeMonth;

    private int patientVisitedCount;

    public ModelGraphData() {

    }

    public int getPatientVisitedCount() {
        return patientVisitedCount;
    }

    public ModelGraphData(float patientModelHeight, float patientModelWeight, float patientModelBMI, float patientModelHeadCircumference, float patientModelAge, int patientModelAgeMonth, int patientVisitedCount) {
        this.patientModelHeight = patientModelHeight;
        this.patientModelWeight = patientModelWeight;
        this.patientModelBMI = patientModelBMI;
        this.patientModelHeadCircumference = patientModelHeadCircumference;
        this.patientModelAge = patientModelAge;
        this.patientModelAgeMonth = patientModelAgeMonth;
        this.patientVisitedCount = patientVisitedCount;
    }

    public void setPatientVisitedCount(int patientVisitedCount) {
        this.patientVisitedCount = patientVisitedCount;
    }

    public float getPatientModelHeight() {
        return patientModelHeight;
    }

    public void setPatientModelHeight(float patientModelHeight) {
        this.patientModelHeight = patientModelHeight;
    }

    public float getPatientModelWeight() {
        return patientModelWeight;
    }

    public void setPatientModelWeight(float patientModelWeight) {
        this.patientModelWeight = patientModelWeight;
    }

    public float getPatientModelBMI() {
        return patientModelBMI;
    }

    public void setPatientModelBMI(float patientModelBMI) {
        this.patientModelBMI = patientModelBMI;
    }

    public float getPatientModelHeadCircumference() {
        return patientModelHeadCircumference;
    }

    public void setPatientModelHeadCircumference(float patientModelHeadCircumference) {
        this.patientModelHeadCircumference = patientModelHeadCircumference;
    }

    public float getPatientModelAge() {
        return patientModelAge;
    }

    public void setPatientModelAge(float patientModelAge) {
        this.patientModelAge = patientModelAge;
    }

    public int getPatientModelAgeMonth() {
        return patientModelAgeMonth;
    }

    public void setPatientModelAgeMonth(int patientModelAgeMonth) {
        this.patientModelAgeMonth = patientModelAgeMonth;
    }
}
