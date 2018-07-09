package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

/**
 * Created by umakant.angadi on 08-01-2016.
 */
public class CHMModel {
    private String patient_uuid;
    private String doctor_uuid;
    private String dob;
    private String city;
    private String state;
    private String father_height;
    private String mother_height;
    private String visit_date;
    private String height;
    private String weight;
    private String head_circumference;
    private String bmi;
    private String age;
    //private String v_entrydate;


    public String getPatient_uuid() {
        return patient_uuid;
    }

    public void setPatient_uuid(String patient_uuid) {
        this.patient_uuid = patient_uuid;
    }

    public String getDoctor_uuid() {
        return doctor_uuid;
    }

    public void setDoctor_uuid(String doctor_uuid) {
        this.doctor_uuid = doctor_uuid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFather_height() {
        return father_height;
    }

    public void setFather_height(String father_height) {
        this.father_height = father_height;
    }

    public String getMother_height() {
        return mother_height;
    }

    public void setMother_height(String mother_height) {
        this.mother_height = mother_height;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHead_circumference() {
        return head_circumference;
    }

    public void setHead_circumference(String head_circumference) {
        this.head_circumference = head_circumference;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    @Override
    public String toString() {
        return "CHMModel{" +
                "patient_uuid='" + patient_uuid + '\'' +
                ", doctor_uuid='" + doctor_uuid + '\'' +
                ", dob='" + dob + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", father_height='" + father_height + '\'' +
                ", mother_height='" + mother_height + '\'' +
                ", visit_date='" + visit_date + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", head_circumference='" + head_circumference + '\'' +
                ", bmi='" + bmi + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
