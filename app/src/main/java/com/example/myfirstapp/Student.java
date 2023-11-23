package com.example.myfirstapp;

public class Student {
    private String utorID;
    private String password;

    public Student(){}

    public Student(String utorID,String password){
        this.utorID = utorID;
        this.password = password;
    }

    //Getters
    public String getUtorID(){
        return utorID;
    }
    public String getPassword(){
        return password;
    }
}
