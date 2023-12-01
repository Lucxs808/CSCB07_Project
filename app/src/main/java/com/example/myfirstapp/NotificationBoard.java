package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

//public class NotificationBoard extends AppCompatActivity {
//    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/");
//
//    protected void onCreate(Bundle saveInstanceState){
//        super.onCreate(saveInstanceState);
//        setContentView(R.layout.activity_notification_board);
//
//        Spinner spinner = findViewById(R.id.spinnerOption);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.spinner_options, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        fetchNotifications("announcements", true);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0){
//                    fetchNotifications("announcements", true);
//                } else {
//                    fetchNotifications("events", false);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        Button button = findViewById(R.id.back_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    private void fetchNotifications(String node, boolean isAnnouncement) {
//        DatabaseReference databaseRef = database.getReference(node);
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (isAnnouncement){
//                    ArrayList<Announcement> notifications = new ArrayList<>();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        if (snapshot.getValue() != null){
//                            Announcement announcement = snapshot.getValue(Announcement.class);
//                            if (announcement != null){
//                                notifications.add(announcement);
//                            }
//                        }
//                    }
//                    displayFragment(notifications, true);
//                } else {
//                    ArrayList<Event> notifications = new ArrayList<>();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        if (snapshot.getValue() != null){
//                            Event event = snapshot.getValue(Event.class);
//                            if (event != null){
//                                notifications.add(event);
//                            }
//                        }
//                    }
//                    displayFragment(notifications, false);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void displayFragment(ArrayList<?> notifications, boolean isAnnouncement){
//        NotificationList fragment = NotificationList.newInstance(notifications, isAnnouncement);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
//    }
//}
public class NotificationBoard extends AppCompatActivity implements RSVPClickListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/");
    private Student currentStudent;
    private DatabaseReference eventsReference;

    private ArrayList<?> notifications;
    private boolean isAnnouncement;
    private ArrayList<Event> eventsList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_board);

        Spinner spinner = findViewById(R.id.spinnerOption);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fetchNotifications("announcements", true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fetchNotifications("announcements", true);
                } else {
                    fetchNotifications("events", false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button button = findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchNotifications(String node, boolean isAnnouncement) {
        DatabaseReference databaseRef = database.getReference(node);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isAnnouncement) {
                    ArrayList<Announcement> announcements = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Announcement announcement = snapshot.getValue(Announcement.class);
                        if (announcement != null) {
                            announcements.add(announcement);
                        }
                    }
                    displayFragment(announcements, true);
                } else {
                    ArrayList<Event> events = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Event event = snapshot.getValue(Event.class);
                        if (event != null) {
                            events.add(event);
                        }
                    }
                    displayFragment(events, false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void displayFragment(ArrayList<?> notifications, boolean isAnnouncement) {
        NotificationList fragment = NotificationList.newInstance(notifications, isAnnouncement);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onRSVPClick(Event event) {
        if (currentStudent != null) {
            String studentUtorID = currentStudent.getUtorID();
            if (!event.hasStudentRSVPed(studentUtorID)) {
                event.addRSVP(studentUtorID);
                updateEventInFirebase(event);
                Log.d("RSVP", "RSVP successful for event: " + event.getName());
            } else {
                Log.d("RSVP", "Student already RSVP'd for event: " + event.getName());
            }
        }
    }

    private void updateEventInFirebase(Event event) {
        DatabaseReference eventRef = eventsReference.child(event.getId());
        eventRef.setValue(event);
    }
}

