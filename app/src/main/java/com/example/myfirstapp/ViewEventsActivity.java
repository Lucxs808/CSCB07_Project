// ViewEventsActivity.java
package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ViewEventsActivity extends AppCompatActivity {

    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private DatabaseReference eventsReference;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        currentUid = getIntent().getStringExtra("utorID");

        // Initialize Firebase
        eventsReference = FirebaseDatabase.getInstance().getReference("events");

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize eventList
        eventList = new ArrayList<>();

        // Set up the EventAdapter
        eventAdapter = new EventAdapter(eventList, currentUid);
        recyclerView.setAdapter(eventAdapter);

        // Load events from Firebase
        loadEvents();
    }

    private void loadEvents() {
        eventsReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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

    public void onBackBtnClick(View view) {
        Intent intent = new Intent(this, AdminPage.class);
        intent.putExtra("utorID", currentUid);
        startActivity(intent);
    }
}
