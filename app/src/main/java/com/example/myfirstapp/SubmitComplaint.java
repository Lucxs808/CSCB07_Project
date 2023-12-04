package com.example.myfirstapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SubmitComplaint extends AppCompatActivity {


    private EditText complaintSubjectEditText;
    private EditText complaintBodyEditText;
    private DatabaseReference complaintsReference;
    String currentUid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_complaint);


        // Initialize Firebase
        complaintsReference = FirebaseDatabase.getInstance().getReference("complaints");
        currentUid = getIntent().getStringExtra("utorID");

        // Initialize UI elements
        complaintSubjectEditText = findViewById(R.id.editTextComplaint);
        complaintBodyEditText = findViewById(R.id.editTextBody);
        Button submitComplaintButton = findViewById(R.id.submitcbtn);


        submitComplaintButton.setOnClickListener(v -> pushComplaint());
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
        assert complaintKey != null;
        complaintsReference.child(complaintKey).setValue(complaint);

        Toast.makeText(SubmitComplaint.this, "You have submitted a complaint", Toast.LENGTH_SHORT).show();

    }

    public void OnBackBtn2Click(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("utorID", currentUid);
        startActivity(intent);
    }
}


