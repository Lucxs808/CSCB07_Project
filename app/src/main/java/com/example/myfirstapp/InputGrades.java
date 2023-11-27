package com.example.myfirstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        marksList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference().child("users").child("students");
        final EditText a08mark = findViewById(R.id.CSCA08);
        final EditText a20mark = findViewById(R.id.CSCA20);
        final EditText a48mark = findViewById(R.id.CSCA48);
        final EditText a67mark = findViewById(R.id.CSCA67);
        final EditText a31mark = findViewById(R.id.MATA31);
        final EditText a37mark = findViewById(R.id.MATA37);
        final EditText a22mark = findViewById(R.id.MATA22);

        Button buttonSubmitGrades = findViewById(R.id.Submit);
        buttonSubmitGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
    }
    private void updateFirebase() {
        utorid = getIntent().getStringExtra("utorid");
        databaseReference.child(utorid).child("marksList").setValue(marksList);
    }
}
