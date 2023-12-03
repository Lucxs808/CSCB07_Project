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

public class ViewComplaints extends AppCompatActivity {

    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintsList;
    private String currentUid;
    private DatabaseReference complaintsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);

        // Initialize Firebase
        complaintsReference = FirebaseDatabase.getInstance().getReference("complaints");
        currentUid = getIntent().getStringExtra("utorID");

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewcomplaints);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize eventList
        complaintsList = new ArrayList<>();

        // Set up the EventAdapter
        complaintAdapter = new ComplaintAdapter(complaintsList);
        recyclerView.setAdapter(complaintAdapter);

        // Load events from Firebase
        loadComplaints();
    }


    private void loadComplaints() {
        complaintsReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Handle null data
                    if (snapshot.getValue() != null) {
                        Complaint complaint = snapshot.getValue(Complaint.class);
                        if (complaint != null) {
                            complaintsList.add(complaint);
                        }
                    }
                }
                complaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    public void OnBackBtn3Clicksc(View view) {
        Intent intent = new Intent(this, AdminPage.class);
        intent.putExtra("utorID", currentUid);
        startActivity(intent);
    }

}
