package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class InputGrades extends AppCompatActivity {
    private ArrayList<Integer> marksList;
    private DatabaseReference databaseReference;
    private String utorid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_grades);
        utorid = getIntent().getStringExtra("utorid");
        assert utorid != null;
        marksList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference().child("users").child("students");
        final EditText a08mark = findViewById(R.id.CSCA08);
        final EditText a20mark = findViewById(R.id.CSCA20);
        final EditText a48mark = findViewById(R.id.CSCA48);
        final EditText a67mark = findViewById(R.id.CSCA67);
        final EditText a31mark = findViewById(R.id.MATA31);
        final EditText a37mark = findViewById(R.id.MATA37);
        final EditText a22mark = findViewById(R.id.MATA22);

        RadioGroup CoopCheck = findViewById(R.id.CoopCheck);
        CoopCheck.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedCategory = radioButton.getText().toString();
            if (selectedCategory.equals("Admitted to Co-op")) {
                databaseReference.child(utorid).child("coop").setValue(true);
            } else if (selectedCategory.equals("Not Admitted to Co-op")) {
                databaseReference.child(utorid).child("coop").setValue(false);
            }
        });

        RadioGroup AdmissionCategory = findViewById(R.id.AdmissionCategory);
        AdmissionCategory.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedCategory = radioButton.getText().toString();
            switch (selectedCategory) {
                case "Computer Science":
                    databaseReference.child(utorid).child("admissionCategory").setValue(0);
                    break;
                case "Mathematics":
                    databaseReference.child(utorid).child("admissionCategory").setValue(1);
                    break;
                case "Statistics":
                    databaseReference.child(utorid).child("admissionCategory").setValue(2);
                    break;
            }
        });

        Button buttonSubmitGrades = findViewById(R.id.Submit);
        buttonSubmitGrades.setOnClickListener(view -> {
            int a08marks = parseAndGetInt(a08mark);
            int a20marks = parseAndGetInt(a20mark);
            int a48marks = parseAndGetInt(a48mark);
            int a67marks = parseAndGetInt(a67mark);
            int a31marks = parseAndGetInt(a31mark);
            int a37marks = parseAndGetInt(a37mark);
            int a22marks = parseAndGetInt(a22mark);
            marksList.clear();
            marksList.add(a08marks);
            marksList.add(a20marks);
            marksList.add(a48marks);
            marksList.add(a67marks);
            marksList.add(a31marks);
            marksList.add(a37marks);
            marksList.add(a22marks);
            updateFirebase();
            Intent intent = new Intent(InputGrades.this, POStChecker.class);
            intent.putExtra("utorid", utorid);
            Log.d("InputGrades", "Marks List Size: " + (marksList != null ? marksList.size() : "null"));
            intent.putIntegerArrayListExtra("marksList", marksList); // THIS BROKE EVERYTHING JUST BC IT DIDNT MATCH *Fixed By Lucus
            startActivity(intent);
            finish();
        });
    }

    private void updateFirebase() {
        databaseReference.child(utorid).child("marksList").setValue(marksList);
        databaseReference.child(utorid).child("grades").setValue(true);
    }

    private int parseAndGetInt(TextView textView) {
        if (textView != null) {
            String text = textView.getText().toString().trim(); // Remove leading/trailing whitespaces

            if (!text.isEmpty()) {
                try {
                    return Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    // Handle the case where the text is not a valid integer
                    e.printStackTrace(); // Or log the error
                }
            }
        }
        return 0;
    }
}