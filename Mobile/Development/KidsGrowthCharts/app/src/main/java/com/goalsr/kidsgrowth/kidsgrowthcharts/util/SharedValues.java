package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import android.app.Application;

/**
 * Created by Vinayaka on 12/17/2015.
 */
public class SharedValues {

    private static String docid ;

    public static String getDocid()
    {
        return docid ;
    }

    public static void setDocid(String setdocid)
    {
        docid = setdocid ;
    }


/*
    private static String patientid ;
    public static String getPatientid()
    {
        return patientid ;
    }

    public static void setPatientid(String setpatientid)
    {
        patientid = setpatientid ;
    }
*/

    private static String selectedPatientId ;
    public static String getSelectedPatientId()
    {
        return selectedPatientId ;
    }

    public static void setSelectedPatientId(String passedPatientId)
    {
        selectedPatientId = passedPatientId ;
    }

    private static String selectedPatientName ;
    public static String getSelectedPatientName()
    {
        return selectedPatientName ;
    }

    public static void setSelectedPatientName(String passedpname)
    {
        selectedPatientName = passedpname ;
    }

    private static String selectedPatientDOB ;
    public static String getSelectedPatientDOB()
    {
        return selectedPatientDOB ;
    }

    public static void setSelectedPatientDOB(String passedpdob)
    {
        System.out.println("passedpdob"+ passedpdob);
        selectedPatientDOB = passedpdob ;
    }

    private static String selectedPatientGender ;
    public static String getSelectedPatientGender()
    {
        return selectedPatientGender ;
    }

    public static void setSelectedPatientGender(String passedpgender)
    {
        selectedPatientGender = passedpgender ;
    }

    private static String selectedPatientCity ;
    public static String getSelectedPatientCity()
    {
        return selectedPatientCity ;
    }

    public static void setSelectedPatientCity(String passedpcity)
    {
        selectedPatientCity = passedpcity ;
    }

    private static String selectedPatientState ;
    public static String getSelectedPatientState()
    {
        return selectedPatientState ;
    }

    public static void setSelectedPatientState(String passedpstate)
    {
        selectedPatientState = passedpstate ;
    }

    private static String selectedPatientFather ;
    public static String getSelectedPatientFather()
    {
        return selectedPatientFather ;
    }

    public static void setSelectedPatientFather(String passedpfather)
    {
        selectedPatientFather = passedpfather ;
    }

    private static String selectedPatientMother ;
    public static String getSelectedPatientMother()
    {
        return selectedPatientMother ;
    }

    public static void setSelectedPatientMother(String passedpmother)
    {
        selectedPatientMother = passedpmother ;
    }

    private static double patientAge;

    public static double getPatientAge() {
        return patientAge;
    }
//Shrishail Adding temp age for Percentile value

    private static String referenceHeight;

    private static String referenceWeight;

    public static String getReferenceHeight() {
        return referenceHeight;
    }

    public static void setReferenceHeight(String referenceHeight) {
        SharedValues.referenceHeight = referenceHeight;
    }

    public static String getReferenceWeight() {
        return referenceWeight;
    }

    public static void setReferenceWeight(String referenceWeight) {
        SharedValues.referenceWeight = referenceWeight;
    }

    private static  double tempPatientAge;

    public static double getTempPatientAge() {
        return tempPatientAge;
    }

    public static void setTempPatientAge(double tempPatientAge) {
        SharedValues.tempPatientAge = tempPatientAge;
    }

    public static void setPatientAge(double patientAge) {
        SharedValues.patientAge = patientAge;
    }

    private static long midParentalHeight;

    public static long getMidParentalHeight() {
        return midParentalHeight;
    }

    public static void setMidParentalHeight(long midParentalHeight) {
        SharedValues.midParentalHeight = midParentalHeight;
    }
}
