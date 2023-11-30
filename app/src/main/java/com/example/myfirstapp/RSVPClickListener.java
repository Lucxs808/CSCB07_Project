package com.example.myfirstapp;

import com.example.myfirstapp.Event;
import com.example.myfirstapp.EventAdapter;
import com.example.myfirstapp.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RSVPClickListener implements EventAdapter.RSVPClickListener {

    private Student currentStudent;
    private DatabaseReference eventsReference;

    public RSVPClickListener(Student student, DatabaseReference eventsReference) {
        this.currentStudent = student;
        this.eventsReference = eventsReference;
    }

    @Override
    public void onRSVPClicked(Event event) {
        // Handle Firebase update here
        // Use currentStudent.getUtorID() to get the UtorID of the current student
        String studentID = currentStudent.getUtorID();

        // Update the Firebase database
        DatabaseReference eventRef = eventsReference.child(event.getId());
        event.addRSVP(studentID); // Add the student's UtorID to the rsvpList
        eventRef.setValue(event); // Update the Firebase database with the updated event
    }
}


