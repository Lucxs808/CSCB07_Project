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
import java.util.Map;

public class ViewRegisteredEvents extends AppCompatActivity {
    private EventAdapter2 eventAdapter;
    private List<Event> eventList;
    private DatabaseReference eventsRef;
    private String currentUid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        eventsRef = FirebaseDatabase.getInstance().getReference("events");
        currentUid = getIntent().getStringExtra("utorid");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter2(eventList, currentUid);
        recyclerView.setAdapter(eventAdapter);
        loadEvents();


    }

    private void loadEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Handle null data
                    if (snapshot.getValue() != null) {
                        Event event = snapshot.getValue(Event.class);
                        if (event != null) {
                            Map<String, Boolean> attendances = event.getAttendances();
                            if (attendances.containsKey(currentUid)){
                                boolean isAttend = Boolean.TRUE.equals(attendances.get(currentUid));
                                if (isAttend){
                                    eventList.add(event);
                                }
                            }
                        }
                    }
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void onBackBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("utorID", currentUid);
        startActivity(intent);
    }
}
