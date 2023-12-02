package com.example.myfirstapp;

import android.widget.Button;
import android.widget.TextView;
import java.time.LocalDate;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Scanner;




public class commenter {

    public static void main(String[] args){
        LocalDate myObj = LocalDate.now(); // Create a date object
    }
    // this function enables the students from events to add comments to their specific event they attended

    String studentID;   //  asks for student number
    String date;        //  gives the date of comments posted
    String Content;     //  content of the student's comments
    String Rating;      //  student chooses a rating
        System.out.println("How do you feel about this event?");
        Scanner scanner = new Scanner(System.in);
        String comment = scanner.nextLine();
        System.out.println("thanks for the comment");

        System.out.println("on a rating of 1 to 10, how do you feel about the event?");
        String rating = scanner.nextline();
        System.out.println("thanks for the rating!");



    TextView textview;
    TextInputEditText editTextUtorID;
    TextInputEditText editTextPassword;
    Button loginbtn;
    Button Submit_comment;
    //this allows the user to add in comments and ratings and displays it for others to see



}
