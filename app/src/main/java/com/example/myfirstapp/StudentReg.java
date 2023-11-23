package com.example.myfirstapp;

import com.google.firebase.database.DatabaseReference;

public class StudentReg {

    private DatabaseReference database;

    public StudentReg(DatabaseReference d) {
        this.database = d;
    }
    public void pushStudentToDatabase(Student s){
        this.database.child("users").child("students").child(s.getUtorID()).setValue(s);

    }
}
