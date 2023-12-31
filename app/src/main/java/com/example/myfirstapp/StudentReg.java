package com.example.myfirstapp;

import com.google.firebase.database.DatabaseReference;

public class StudentReg {

    private final DatabaseReference database;

    public StudentReg(DatabaseReference d) {
        this.database = d;
    }
    public void pushStudentToDatabase(Student s){
        DatabaseReference studentRef = this.database.child("users").child("students").child(s.getUtorID());
        studentRef.child("utorID").setValue(s.getUtorID());
        studentRef.child("password").setValue(s.getPassword());
        studentRef.child("grades").setValue(s.hasGrades());
        studentRef.child("marksList").setValue(s.getMarks());
        studentRef.child("admissionCategory").setValue(s.getAdmissionCategory());
        studentRef.child("coop").setValue(s.getCoop());
    }
}
