package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitComment extends AppCompatActivity {
    private EditText editTextComment;
    private EditText editTextRating;
    private String currentUid;
    private DatabaseReference eventsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_comment);

        editTextComment = findViewById(R.id.editTextCommentBody);
        editTextRating = findViewById(R.id.editTextNumericRate);
        currentUid = getIntent().getStringExtra("utorID");
        //String currentEventID = getIntent().getStringExtra("eventID");

        //Original version is: eventsReference = FirebaseDatabase.getInstance().getReference("events").child(currentEventID);
        eventsReference = FirebaseDatabase.getInstance().getReference("events");
        Button button = findViewById(R.id.commentBtn);

        button.setOnClickListener(v -> {
            SubmitComm();
            Toast.makeText(SubmitComment.this, "You have scheduled a new Event", Toast.LENGTH_SHORT).show();
        });
    }


    private void SubmitComm(){
        String commentBody = editTextComment.getText().toString();
        String numRating = editTextRating.getText().toString();

        if (commentBody.isEmpty() || numRating.isEmpty()) {
            // Display an error message or handle invalid input
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int numericRating = Integer.parseInt(numRating);

        eventsReference.child("comments").child(currentUid).setValue(commentBody);
        eventsReference.child("ratings").child(currentUid).setValue(numericRating);
    }

    public void OnBackBtn6Click(View view){
        Intent intent = new Intent(this, ViewRegisteredEvents.class);
        intent.putExtra("utorID", currentUid);
        startActivity(intent);
    }
}
