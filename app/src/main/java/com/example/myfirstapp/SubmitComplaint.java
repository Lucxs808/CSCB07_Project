package com.example.myfirstapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.app.Activity;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SubmitComplaint extends AppCompatActivity {


    private EditText complaintSubjectEditText;
    private EditText complaintBodyEditText;
    private Button submitComplaintButton;
    private DatabaseReference complaintsReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_complaint);


        // Initialize Firebase
        complaintsReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference("complaints");

        // Initialize UI elements
        complaintSubjectEditText = findViewById(R.id.csubject);
        complaintBodyEditText = findViewById(R.id.cbody);
        submitComplaintButton = findViewById(R.id.submitcbtn);


        submitComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushComplaint();
            }
        });
    }


    private void pushComplaint(){


        String complaintSubject = complaintSubjectEditText.getText().toString();
        String complaintBody = complaintBodyEditText.getText().toString();


        if (complaintSubject.isEmpty() || complaintBody.isEmpty()) {
            // Display an error message or handle invalid input
            Toast.makeText(SubmitComplaint.this,"One or more fields missing", Toast.LENGTH_SHORT).show();
            return;
        }


        Complaint complaint = new Complaint(complaintSubject, complaintBody);


        String complaintKey = complaintsReference.push().getKey();
        complaintsReference.child(complaintKey).setValue(complaint);

        Toast.makeText(SubmitComplaint.this, "You have submitted a complaint", Toast.LENGTH_SHORT).show();




    }

}

