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
        TextView textProgram1 = findViewById(R.id.textProgram1);
        TextView textProgram2 = findViewById(R.id.textProgram2);
        ArrayList<Integer> marks = getIntent().getIntegerArrayListExtra("marks");

        boolean isEligibleForProgram1 = checkEligibilityForProgram1(marks);
        boolean isEligibleForProgram2 = checkEligibilityForProgram2(marks);

        textProgram1.setText("Program 1: " + (isEligibleForProgram1 ? "Yes" : "No"));
        textProgram2.setText("Program 2: " + (isEligibleForProgram2 ? "Yes" : "No"));

    }

    private boolean checkEligibilityForProgram1(ArrayList<Integer> marks) {
        return calculateAverageMarks(marks) >= 70;
    }

    private boolean checkEligibilityForProgram2(ArrayList<Integer> marks) {
        return marks.get(0) >= 60;
    }

    private double calculateAverageMarks(ArrayList<Integer> marks) {
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        return (double) sum / marks.size();
    }
}
