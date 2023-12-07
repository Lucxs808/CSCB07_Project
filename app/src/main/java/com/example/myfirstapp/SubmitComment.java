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
    private String utorID;
    private DatabaseReference eventsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_comment);

        editTextComment = findViewById(R.id.editTextCommentBody);
        editTextRating = findViewById(R.id.editTextNumericRate);
        utorID = getIntent().getStringExtra("utorID");
        String currentEventID = getIntent().getStringExtra("eventID");
        //eventsReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference().child("events").child(currentEventID);
        eventsReference = FirebaseDatabase.getInstance().getReference("events").child(currentEventID);
        Button button = findViewById(R.id.commentBtn);

        button.setOnClickListener(v -> {
            SubmitComm();
            Toast.makeText(SubmitComment.this, "You have commented!", Toast.LENGTH_SHORT).show();
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
        if (0 >= Integer.parseInt(numRating) || Integer.parseInt(numRating) >= 10) {
            // Display an error message or handle invalid input
            Toast.makeText(this, "Rating has to be an integer between 0 and 10", Toast.LENGTH_SHORT).show();
            return;
        }

        int numericRating = Integer.parseInt(numRating);


        eventsReference.child("comments").child(utorID).setValue(commentBody);
        eventsReference.child("ratings").child(utorID).setValue(numericRating);
    }

    public void OnBackBtn6Click(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("utorID", utorID);
        startActivity(intent);
    }
}
