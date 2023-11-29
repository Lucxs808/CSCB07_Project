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

public class ViewAnnouncements extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AnnouncementAdapter announcementAdapter;
    private List<Announcement> announcementsList;

    private DatabaseReference announcementsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcements);

        // Initialize Firebase
        announcementsReference = FirebaseDatabase.getInstance().getReference("announcements");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize eventList
        announcementsList = new ArrayList<>();

        // Set up the EventAdapter
        announcementAdapter = new AnnouncementAdapter(announcementsList);
        recyclerView.setAdapter(announcementAdapter);

        // Load events from Firebase
        loadAnnouncements();
    }

    private void loadAnnouncements() {
        announcementsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                announcementsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Handle null data
                    if (snapshot.getValue() != null) {
                        Announcement announcement = snapshot.getValue(Announcement.class);
                        if (announcement != null) {
                            announcementsList.add(announcement);
                        }
                    }
                }
                announcementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
