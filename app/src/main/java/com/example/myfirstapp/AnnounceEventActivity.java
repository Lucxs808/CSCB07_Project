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

public class AnnounceEventActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText locationEditText;
    private EditText participantLimitEditText; // New field for participant limit
    private Button announceEventButton;
    private Button backButton;

    private DatabaseReference eventsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_event);

        // Initialize Firebase
        eventsReference = FirebaseDatabase.getInstance().getReference("events");

        // Initialize UI elements
        eventNameEditText = findViewById(R.id.editTextEventName);
        dateEditText = findViewById(R.id.editTextDate);
        timeEditText = findViewById(R.id.editTextTime);
        locationEditText = findViewById(R.id.editTextLocation);
        participantLimitEditText = findViewById(R.id.editTextParticipantLimit); // New field initialization
        announceEventButton = findViewById(R.id.announceEventBtn);
        backButton = findViewById(R.id.back_button1);

        // Set up click listener for the announce event button
        announceEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announceEvent();
                Toast.makeText(AnnounceEventActivity.this, "You have scheduled a new Event", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void announceEvent() {
        // Retrieve input values
        String eventName = eventNameEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String participantLimitStr = participantLimitEditText.getText().toString(); // New line

        // Validate input
        if (eventName.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty() || participantLimitStr.isEmpty()) {
            // Display an error message or handle invalid input
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse participant limit as an integer
        int participantLimit = Integer.parseInt(participantLimitStr);

        // Create an Event object or use a Map to store event details
        Event event = new Event(eventName, date, time, location);
        event.setParticipantLimit(participantLimit); // Set participant limit

        // Push the event to Firebase
        String eventKey = eventsReference.push().getKey();
        eventsReference.child(eventKey).setValue(event);
    }
}
