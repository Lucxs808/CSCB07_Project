package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
            if (selectedCategory.equals("Admitted to Co-op")){
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
            int a08marks = Integer.parseInt(a08mark.getText().toString());
            int a20marks = Integer.parseInt(a20mark.getText().toString());
            int a48marks = Integer.parseInt(a48mark.getText().toString());
            int a67marks = Integer.parseInt(a67mark.getText().toString());
            int a31marks = Integer.parseInt(a31mark.getText().toString());
            int a37marks = Integer.parseInt(a37mark.getText().toString());
            int a22marks = Integer.parseInt(a22mark.getText().toString());
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
            intent.putIntegerArrayListExtra("marks", marksList);
            startActivity(intent);
            finish();
        });
    }
    private void updateFirebase() {
        databaseReference.child(utorid).child("marksList").setValue(marksList);
        databaseReference.child(utorid).child("grades").setValue(true);
    }
}