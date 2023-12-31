package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {
    private String utorID;
    private String password;
    private List<Integer> marksList; // [a08,a20,a48,a67,a31,a37,a67]
    private boolean grades; //Entered or not

    private int admissionCategory; // 0 for Computer Science, 1 for Math, 2 for Stats
    private boolean coop;

    private List event_list;

    public Student(){}

    public Student(String utorID,String password){
        this.utorID = utorID;
        this.password = password;
        this.marksList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        this.grades = false;
        this.admissionCategory = 0;
        this.coop = false;
    }

    //Getters
    public String getUtorID(){
        return utorID;
    }
    public String getPassword(){
        return password;
    }
    public boolean hasGrades() {
        return grades;
    }
    public List<Integer> getMarks() {
        return marksList;
    }
    public int getAdmissionCategory() {
        return admissionCategory;
    }
    public boolean getCoop() {
        return coop;
    }
}
