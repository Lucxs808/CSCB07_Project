package com.example.myfirstapp;

import com.google.firebase.database.DatabaseReference;

public class StudentReg {
    private StudentView sView;
    private DatabaseReference database;

    public StudentReg(StudentView sView, DatabaseReference d) {
        this.sView = sView;
        this.database = d;
    }
    public void pushStudentToDatabase(Student s){
        this.database.child("students").child(s.getUtorID()).setValue(s);

    }
}
