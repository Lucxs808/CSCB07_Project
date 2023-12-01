package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private Button backButton;

    private DatabaseReference announcementsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcements1);

        // Initialize Firebase
        announcementsReference = FirebaseDatabase.getInstance().getReference("announcements");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize eventList
        announcementsList = new ArrayList<>();

        // Set up the EventAdapter
        announcementAdapter = new AnnouncementAdapter(announcementsList);
        recyclerView.setAdapter(announcementAdapter);

        backButton = findViewById(R.id.back_button4);

        // Load events from Firebase
        loadAnnouncements();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);
                finish();
            }
        });
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
