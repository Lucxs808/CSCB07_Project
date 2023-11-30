// ViewEventsActivity.java
package com.example.myfirstapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

//public class ViewEventsActivity extends AppCompatActivity {
//
//
//    private RecyclerView recyclerView;
//    private EventAdapter eventAdapter;
//    private List<Event> eventList;
//    private DatabaseReference eventsReference;
//    private Student currentStudent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_events);
//
//
//
//// Corrected: Assign to the class-level variable
//        eventsReference = FirebaseDatabase.getInstance().getReference("events");
//
//        // Initialize RecyclerView
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//
//// Initialize eventList
//        eventList = new ArrayList<>();
////        currentStudent = getCurrentStudent();
//        // Set up the EventAdapter
////        eventAdapter = new EventAdapter(eventList);
////        eventAdapter.setStudent(currentStudent);
//        recyclerView.setAdapter(eventAdapter);
////        eventAdapter = new EventAdapter(eventList);
//        RSVPClickListener rsvpClickListener = new RSVPClickListener(currentStudent, eventsReference);
//        eventAdapter = new EventAdapter(eventList);
//        eventAdapter.setStudent(currentStudent);
//        eventAdapter.setRSVPClickListener(rsvpClickListener); // Set the listener// Set the listener
//
//
//        // Load events from Firebase
//        loadEvents();
//    }
////    @Override
////    public void onRSVPClicked(Event event) {
////        // Handle Firebase update here
////        // Use currentStudent.getUtorID() to get the UtorID of the current student
////        String studentID = currentStudent.getUtorID();
////
////        // Update the Firebase database
////        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("events").child(event.getId());
////        eventRef.setValue(event);
////    }
//
//    private void loadEvents() {
//        eventsReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                eventList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    // Handle null data
//                    if (snapshot.getValue() != null) {
//                        Event event = snapshot.getValue(Event.class);
//                        if (event != null) {
//                            eventList.add(event);
//                        }
//                    }
//                }
//                eventAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }
//}
public class ViewEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private DatabaseReference eventsReference;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        // Corrected: Assign to the class-level variable
        eventsReference = FirebaseDatabase.getInstance().getReference("events");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize eventList
        eventList = new ArrayList<>();
        currentStudent = getCurrentStudent(); // Assume 'getCurrentStudent' method properly retrieves the current student

        // Set up the EventAdapter
        eventAdapter = new EventAdapter(eventList);
        eventAdapter.setStudent(currentStudent);
        eventAdapter.setEventsReference(eventsReference);
        eventAdapter.setRSVPClickListener(new RSVPClickListener(currentStudent, eventsReference)); // Pass the RSVPClickListener
        recyclerView.setAdapter(eventAdapter);

        // Load events from Firebase
        loadEvents();
    }

    private void loadEvents() {
        eventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Handle null data
                    if (snapshot.getValue() != null) {
                        Event event = snapshot.getValue(Event.class);
                        if (event != null) {
                            eventList.add(event);
                        }
                    }
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private Student getCurrentStudent() {
        // Implement the logic to retrieve the current student from shared preferences, database, or any other relevant source
        // Assuming this method returns a Student object
        return new Student("studentID", "Student Name"); // Replace with actual implementation
    }
}
