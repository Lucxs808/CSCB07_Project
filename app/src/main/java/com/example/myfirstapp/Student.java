package com.example.myfirstapp;

public class Student {
    private String email;
    private int studentnumber;
    private String password;

    public Student(){}

    public Student(String email, int studentnumber, String password){
        this.email = email;
        this.studentnumber = studentnumber;
        this.password = password;
    }

    //Getters
    public int getStudentNum(){
        return studentnumber;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
