package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;

public class POStChecker extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_checker);

        String utorid = getIntent().getStringExtra("utorid");
        TextView textProgramsAvailable = findViewById(R.id.textProgramsAvailable);
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

        ArrayList<Integer> marks = getIntent().getIntegerArrayListExtra("marksList");

        boolean isEligibleForMinorCS = checkEligibilityForMinorCS(marks);
        boolean isEligibleForMajorCS = checkEligibilityForMajorCS(marks);
        boolean isEligibleForMajorCoopCS = checkEligibilityForMajorCoopCS(marks);
        boolean isEligibleForSpecialistCS = checkEligibilityForSpecialistCS(marks);
        boolean isEligibleForSpecialistCoopCS = checkEligibilityForSpecialistCoopCS(marks);
        boolean isEligibleForMajorMath = checkEligibilityForMajorMath(marks);
        boolean isEligibleForMajorCoopMath = checkEligibilityForMajorCoopMath(marks);
        boolean isEligibleForSpecialistMath = checkEligibilityForSpecialistMath(marks);
        boolean isEligibleForSpecialistCoopMath = checkEligibilityForSpecialistCoopMath(marks);
        boolean isEligibleForMajorStats = checkEligibilityForMajorStats(marks);
        boolean isEligibleForMajorCoopStats = checkEligibilityForMajorCoopStats(marks);
        boolean isEligibleForSpecialistStats = checkEligibilityForSpecialistStats(marks);
        boolean isEligibleForSpecialistCoopStats = checkEligibilityForSpecialistCoopStats(marks);


        Minor_Computer_Science.setText("Minor in Computer Science: " + (isEligibleForMinorCS ? "Requirements met but not guaranteed" : "No"));
        Major_Computer_Science.setText("Major in Computer Science: " + (isEligibleForMajorCS ? "Yes" : "No"));
        Major_Coop_Computer_Science.setText("Major(Coop) in Computer Science: " + (isEligibleForMajorCoopCS ? "Yes" : "No"));
        Specialist_Computer_Science.setText("Specialist in Computer Science: " + (isEligibleForSpecialistCS ? "Yes" : "No"));
        Specialist_Coop_Computer_Science.setText("Specialist(Coop) in Computer Science: " + (isEligibleForSpecialistCoopCS ? "Yes" : "No"));
        Major_Math.setText("Major in Mathematics: " + (isEligibleForMajorMath ? "Yes" : "No"));
        Major_Coop_Math.setText("Major(Coop) in Mathematics: " + (isEligibleForMajorCoopMath ? "Yes" : "No"));
        Specialist_Math.setText("Specialist in Mathematics: " + (isEligibleForSpecialistMath ? "Yes" : "No"));
        Specialist_Coop_Math.setText("Specialist(Coop) in Mathematics: " + (isEligibleForSpecialistCoopMath ? "Yes" : "No"));
        Major_Stats.setText("Major in Statistics: " + (isEligibleForMajorStats ? "Yes" : "No"));
        Major_Coop_Stats.setText("Major(Coop) in Statistics: " + (isEligibleForMajorCoopStats ? "Yes" : "No"));
        Specialist_Stats.setText("Specialist in Statistics: " + (isEligibleForSpecialistStats ? "Yes" : "Not Coded Yet"));
        Specialist_Coop_Stats.setText("Specialist(Coop) in Statistics: " + (isEligibleForSpecialistCoopStats ? "Yes" : "Not Coded Yet"));
    }

    private boolean checkEligibilityForMinorCS(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 0.0 && (gpaList.get(3) > 0.0 || gpaList.get(4) > 0.0 || gpaList.get(6) > 0.0)){
            return true;
        }
        return false;
    }
    private boolean checkEligibilityForMajorCS(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.5){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForMajorCoopCS(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.5){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForSpecialistCS(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.5){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForSpecialistCoopCS(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0 && gpaList.get(2) >= 3.0 && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (calculateAverageGPA(gpaList) >= 2.5){
                if ((gpaList.get(3) >= 1.7 && gpaList.get(5) >= 1.7) || (gpaList.get(3) >= 1.7 && gpaList.get(6) >= 1.7) || (gpaList.get(5) >= 1.7 && gpaList.get(6) >= 1.7)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForMajorMath(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.0){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForMajorCoopMath(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.0){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForSpecialistMath(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (gpaList.get(0) > 0.0  && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.5){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForSpecialistCoopMath(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (((gpaList.get(0) > 0.0) || (gpaList.get(1) > 0.0)) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/4) >= 2.5){
                if ((gpaList.get(3) >= 3.0) || (gpaList.get(5) >= 3.0) || (gpaList.get(6) >= 3.0)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkEligibilityForMajorStats(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (((gpaList.get(0) > 0.0) || (gpaList.get(1) > 0.0)) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(0) + gpaList.get(1) + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/5) >= 2.3){
                return true;
            }
        }
        return false;
    }
    private boolean checkEligibilityForMajorCoopStats(ArrayList<Integer> marks) {
        ArrayList<Double> gpaList = GPAConverter(marks);
        if (((gpaList.get(0) > 0.0) || (gpaList.get(1) > 0.0)) && gpaList.get(3) > 0.0 && gpaList.get(4) > 0.0 && gpaList.get(5) > 0.0 && gpaList.get(6) > 0.0){
            if (((gpaList.get(0) + gpaList.get(1) + gpaList.get(3) + gpaList.get(4) + gpaList.get(5) + gpaList.get(6))/5) >= 2.5){
                return true;
            }
        }
        return false;
    }
    private boolean checkEligibilityForSpecialistStats(ArrayList<Integer> marks) {
        return false; // to add
    }
    private boolean checkEligibilityForSpecialistCoopStats(ArrayList<Integer> marks) {
        return false; // to add
    }

    private static ArrayList<Double> GPAConverter(ArrayList<Integer> marks) {
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
}
