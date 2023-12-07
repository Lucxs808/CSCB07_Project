package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class POStChecker extends AppCompatActivity {
    private int admissionCategory;
    private boolean coopStatus;
    private String utorid;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_checker);
        ArrayList<Integer> marks = getIntent().getIntegerArrayListExtra("marksList");
        Log.d("InputGrades1", "Marks List Size: " + (marks != null ? marks.size() : "null"));

        utorid = getIntent().getStringExtra("utorid");
        coopStatus = getIntent().getBooleanExtra("coop", false);
        admissionCategory = getIntent().getIntExtra("admissionCategory", 0);

        if (utorid != null && !utorid.isEmpty()){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference().child("users").child("students").child(utorid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot admissionCategorySnapshot = dataSnapshot.child("admissionCategory");
                    if (admissionCategorySnapshot.exists()) {
                        admissionCategory = admissionCategorySnapshot.getValue(Integer.class);
                    }
                    DataSnapshot coopSnapshot = dataSnapshot.child("coop");
                    if (coopSnapshot.exists()) {
                        coopStatus = coopSnapshot.getValue(Boolean.class);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Failed to read value.", error.toException());
                }
            });
        }

        TextView Minor_Computer_Science = findViewById(R.id.Minor_Computer_Science);
        TextView Major_Computer_Science = findViewById(R.id.Major_Computer_Science);
        TextView Major_Coop_Computer_Science = findViewById(R.id.Major_Coop_Computer_Science);
        TextView Specialist_Computer_Science = findViewById(R.id.Specialist_Computer_Science);
        TextView Specialist_Coop_Computer_Science = findViewById(R.id.Specialist_Coop_Computer_Science);
        TextView Major_Math = findViewById(R.id.Major_Math);
        TextView Major_Coop_Math = findViewById(R.id.Major_Coop_Math);
        TextView Specialist_Math = findViewById(R.id.Specialist_Math);
        TextView Specialist_Coop_Math = findViewById(R.id.Specialist_Coop_Math);
        TextView Major_Stats = findViewById(R.id.Major_Stats);
        TextView Major_Coop_Stats = findViewById(R.id.Major_Coop_Stats);
        TextView Specialist_Stats = findViewById(R.id.Specialist_Stats);
        TextView Specialist_Coop_Stats = findViewById(R.id.Specialist_Coop_Stats);


        String isEligibleForMinorCS = checkEligibilityForMinorCS(marks);
        String isEligibleForMajorCS = checkEligibilityForMajorCS(marks, admissionCategory);
        String isEligibleForMajorCoopCS = checkEligibilityForMajorCoopCS(marks, admissionCategory, coopStatus);
        String isEligibleForSpecialistCS = checkEligibilityForSpecialistCS(marks, admissionCategory);
        String isEligibleForSpecialistCoopCS = checkEligibilityForSpecialistCoopCS(marks, admissionCategory, coopStatus);
        String isEligibleForMajorMath = checkEligibilityForMajorMath(marks, admissionCategory);
        String isEligibleForMajorCoopMath = checkEligibilityForMajorCoopMath(marks, admissionCategory, coopStatus);
        String isEligibleForSpecialistMath = checkEligibilityForSpecialistMath(marks, admissionCategory);
        String isEligibleForSpecialistCoopMath = checkEligibilityForSpecialistCoopMath(marks,admissionCategory);
        String isEligibleForMajorStats = checkEligibilityForMajorStats(marks, admissionCategory);
        String isEligibleForMajorCoopStats = checkEligibilityForMajorCoopStats(marks, admissionCategory);
        String isEligibleForSpecialistStats = checkEligibilityForSpecialistStats(marks, admissionCategory);
        String isEligibleForSpecialistCoopStats = checkEligibilityForSpecialistCoopStats(marks, admissionCategory, coopStatus);


        Minor_Computer_Science.setText("Minor in Computer Science: " + isEligibleForMinorCS);
        Major_Computer_Science.setText("Major in Computer Science: " + isEligibleForMajorCS);
        Major_Coop_Computer_Science.setText("Major(Coop) in Computer Science: " + isEligibleForMajorCoopCS);
        Specialist_Computer_Science.setText("Specialist in Computer Science: " + isEligibleForSpecialistCS);
        Specialist_Coop_Computer_Science.setText("Specialist(Coop) in Computer Science: " + isEligibleForSpecialistCoopCS);
        Major_Math.setText("Major in Mathematics: " + isEligibleForMajorMath);
        Major_Coop_Math.setText("Major(Coop) in Mathematics: " + isEligibleForMajorCoopMath);
        Specialist_Math.setText("Specialist in Mathematics: " + isEligibleForSpecialistMath);
        Specialist_Coop_Math.setText("Specialist(Coop) in Mathematics: " + isEligibleForSpecialistCoopMath);
        Major_Stats.setText("Major in Statistics: " + isEligibleForMajorStats);
        Major_Coop_Stats.setText("Major(Coop) in Statistics: " + isEligibleForMajorCoopStats);
        Specialist_Stats.setText("Specialist in Statistics: " + isEligibleForSpecialistStats);
        Specialist_Coop_Stats.setText("Specialist(Coop) in Statistics: " + isEligibleForSpecialistCoopStats);

    }
    private String checkEligibilityForMinorCS(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 0.0 && (gpaList.get(3) > 0.0 || gpaList.get(4) > 0.0 || gpaList.get(6) > 0.0)){
            return "Eligible but not guaranteed (depends on applicant pool)";
        }
        return "Not Eligible";
    }
    private String checkEligibilityForMajorCS(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.5){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    if (admissionCategory == 0) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForMajorCoopCS(ArrayList<Integer> marks, int admissionCategory, boolean coopStatus) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (coopStatus){
            if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
                if (calculateAverageGPA(gpaList) >= 2.5){
                    if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                        if (admissionCategory == 0) {
                            return "Eligible";
                        }
                        else {
                            return "Eligible but not guaranteed (depends on applicant pool)";
                        }
                    }
                }
            }
            return "Not Eligible";
        }
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.75){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    if (admissionCategory == 0) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForSpecialistCS(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.5){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    if (admissionCategory == 0) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForSpecialistCoopCS(ArrayList<Integer> marks, int admissionCategory, boolean coopStatus) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (coopStatus){
            if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
                if (calculateAverageGPA(gpaList) >= 2.5){
                    if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                        if (admissionCategory == 0) {
                            return "Eligible";
                        }
                        else {
                            return "Eligible but not guaranteed (depends on applicant pool)";
                        }
                    }
                }
            }
            return "Not Eligible";
        }
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.75){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    if (admissionCategory == 0) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForMajorMath(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.0){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    if (admissionCategory == 1) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForMajorCoopMath(ArrayList<Integer> marks, int admissionCategory, boolean coopStatus) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (coopStatus){
            if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
                if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.0){
                    if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                        if (admissionCategory == 1) {
                            return "Eligible";
                        }
                        else {
                            return "Eligible but not guaranteed (depends on applicant pool)";
                        }
                    }
                }
            }
            return "Not Eligible";
        }
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.0){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    return "Eligible but not guaranteed (depends on applicant pool)";
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForSpecialistMath(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.5){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    if (admissionCategory == 1) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForSpecialistCoopMath(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.0){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    if (admissionCategory == 1) {
                        return "Eligible";
                    }
                    else {
                        return "Eligible but not guaranteed (depends on applicant pool)";
                    }
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForMajorStats(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (((gpaList.get(0) > 0.0) || (gpaList.get(1) > 0.0)) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(0) + gpaList.get(1) + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/5) >= 2.3){
                if (admissionCategory == 2) {
                    return "Eligible";
                }
                else {
                    return "Eligible but not guaranteed (depends on applicant pool)";
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForMajorCoopStats(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (((gpaList.get(0) > 0.0) || (gpaList.get(1) > 0.0)) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(0) + gpaList.get(1) + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/5) >= 2.5){
                if (admissionCategory == 2) {
                    return "Eligible";
                }
                else {
                    return "Eligible but not guaranteed (depends on applicant pool)";
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForSpecialistStats(ArrayList<Integer> marks, int admissionCategory) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        String eligibility = "Eligible for Quantitative Finance Stream, Statistical Science Stream";
        if (((gpaList.get(0) > 0.0) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0)){
            if ((gpaList.get(0)  + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6)) >= 2.5){
                if (admissionCategory == 2) {
                    if (gpaList.get(2) > 0.0){
                        eligibility += "and Machine Learning and Data Science Stream";
                    }
                    return eligibility;
                }
                else {
                    if (gpaList.get(2) > 0.0){
                        eligibility += "and Machine Learning and Data Science Stream but not guaranteed (depends on applicant pool)";
                    }
                    return eligibility + "but not guaranteed (depends on applicant pool)";
                }
            }
        }
        return "Not Eligible";
    }
    private String checkEligibilityForSpecialistCoopStats(ArrayList<Integer> marks, int admissionCategory, boolean coopStatus) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        String eligibility = "Eligible for Quantitative Finance Stream, Statistical Science Stream";
        if (coopStatus){
            if (((gpaList.get(0) > 0.0) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0)){
                if ((gpaList.get(0)  + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6)) >= 2.5){
                    if (admissionCategory == 2) {
                        if (gpaList.get(2) > 0.0){
                            eligibility += "and Machine Learning and Data Science Stream";
                        }
                        return eligibility;
                    }
                    else {
                        if (gpaList.get(2) > 0.0){
                            eligibility += "and Machine Learning and Data Science Stream but not guaranteed (depends on applicant pool)";
                        }
                        return eligibility + "but not guaranteed (depends on applicant pool)";
                    }
                }
            }
            return "Not Eligible";
        }
        if (((gpaList.get(0) > 0.0) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0)){
            if ((gpaList.get(0)  + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6)) >= 2.75){
                if (admissionCategory == 2) {
                    if (gpaList.get(2) > 0.0){
                        eligibility += "and Machine Learning and Data Science Stream";
                    }
                    return eligibility;
                }
                else {
                    if (gpaList.get(2) > 0.0){
                        eligibility += "and Machine Learning and Data Science Stream but not guaranteed (depends on applicant pool)";
                    }
                    return eligibility + "but not guaranteed (depends on applicant pool)";
                }
            }
        }
        return "Not Eligible";
    }
    private static ArrayList<Double> GPAConverter(ArrayList<Integer> marks) {
        Log.d("Debug", "Marks size: " + (marks != null ? marks.size() : "null"));
        ArrayList<Double> gpa = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        for (int i = 0; i < marks.size(); i++) {
            int mark = marks.get(i);
            if (mark >= 85 && mark <= 100) {
                gpa.set(i, 4.0);
            } else if (mark >= 80 && mark <= 84) {
                gpa.set(i, 3.7);
            } else if (mark >= 77 && mark <= 79) {
                gpa.set(i, 3.3);
            } else if (mark >= 73 && mark <= 76) {
                gpa.set(i, 3.0);
            } else if (mark >= 70 && mark <= 72) {
                gpa.set(i, 2.7);
            } else if (mark >= 67 && mark <= 69) {
                gpa.set(i, 2.3);
            } else if (mark >= 63 && mark <= 66) {
                gpa.set(i, 2.0);
            } else if (mark >= 60 && mark <= 62) {
                gpa.set(i, 1.7);
            } else if (mark >= 57 && mark <= 59) {
                gpa.set(i, 1.3);
            } else if (mark >= 53 && mark <= 56) {
                gpa.set(i, 1.0);
            } else if (mark >= 50 && mark <= 52) {
                gpa.set(i, 0.7);
            } else {
                gpa.set(i, 0.0);
            }
        }
        return gpa;
    }
    private double calculateAverageGPA(ArrayList<Double> gpa) {
        return ((gpa.get(2) + gpa.get(3) + gpa.get(4) + gpa.get(5) + gpa.get(6))/5);
    }
    public void OnBackButtonPostCheckerClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("utorID", utorid);
        startActivity(intent);
    }
}
