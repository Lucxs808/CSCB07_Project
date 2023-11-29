// AnnounceEventActivity.java
package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnnounceEventActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText locationEditText;
    private Button announceEventButton;

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
        announceEventButton = findViewById(R.id.announceEventBtn);

        // Set up click listener for the announce event button
        announceEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announceEvent();
            }
        });
    }

    private void announceEvent() {
        // Retrieve input values
        String eventName = eventNameEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String location = locationEditText.getText().toString();

        // Validate input
        if (eventName.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
            // Display an error message or handle invalid input
            return;
        }

        // Create an Event object or use a Map to store event details
        Event event = new Event(eventName, date, time, location);

        // Push the event to Firebase
        String eventKey = eventsReference.push().getKey();
        eventsReference.child(eventKey).setValue(event);

        // Optionally, provide feedback to the user (e.g., show a toast message)

        // Finish the activity or navigate back to the admin page
        finish();
    }

    public void onBackButton1Click(View view) {
        Intent intent = new Intent(this, AdminPage.class);
        startActivity(intent);
    }
}
