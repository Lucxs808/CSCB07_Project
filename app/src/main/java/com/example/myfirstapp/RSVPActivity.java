package com.example.myfirstapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RSVPActivity extends AppCompatActivity {
    private DatabaseReference eventsReference;
    private String eventId; // You'll need to pass the event ID from the previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp);

        // Initialize Firebase
        eventsReference = FirebaseDatabase.getInstance().getReference("events");

        // Get the event ID from the intent or wherever it is stored
        eventId = getIntent().getStringExtra("eventId");

        // Set up the RSVP button click listener
        MaterialButton rsvpButton = findViewById(R.id.rsvpButton);
        rsvpButton.setOnClickListener(v -> RSVPForEvent());
    }

    private void RSVPForEvent() {
        // Assuming you have a 'students' node in your database
        String studentId = "123"; // You should replace this with the actual student ID
        eventsReference.child(eventId).child("participants").child(studentId).setValue(true);
    }
}
