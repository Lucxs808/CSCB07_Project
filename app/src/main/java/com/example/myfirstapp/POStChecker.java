package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
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


        Minor_Computer_Science.setText("Minor in Computer Science: " + (isEligibleForMinorCS ? "Yes" : "No"));
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
        Specialist_Stats.setText("Specialist in Statistics: " + (isEligibleForSpecialistStats ? "Yes" : "No"));
        Specialist_Coop_Stats.setText("Specialist(Coop) in Statistics: " + (isEligibleForSpecialistCoopStats ? "Yes" : "No"));
    }

    private boolean checkEligibilityForMinorCS(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForMajorCS(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForMajorCoopCS(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForSpecialistCS(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForSpecialistCoopCS(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForMajorMath(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForMajorCoopMath(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForSpecialistMath(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForSpecialistCoopMath(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForMajorStats(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForMajorCoopStats(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForSpecialistStats(ArrayList<Integer> marks) {
        return false;
    }
    private boolean checkEligibilityForSpecialistCoopStats(ArrayList<Integer> marks) {
        return false;
    }

    private double calculateAverageMarks(ArrayList<Integer> marks) {
        if (marks != null && !marks.isEmpty()) {
            int sum = 0;
            for (int mark : marks) {
                sum += mark;
            }
            return (double) sum / marks.size();
        } else {
            // Handle the case where marks is null or empty
            return 0.0; // or any default value you prefer
        }
    }
}
