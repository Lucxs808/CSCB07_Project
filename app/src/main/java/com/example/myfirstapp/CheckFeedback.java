package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class CheckFeedback extends AppCompatActivity {
    private CommentAdapter commentadapter;
    private List<Comment> commentsList;
    private DatabaseReference commentRef;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        currentUid = getIntent().getStringExtra("utorID");
        String currentEventID = getIntent().getStringExtra("eventID");

        // Initialize Firebase, see later
        assert currentEventID != null;
        commentRef = FirebaseDatabase.getInstance().getReference("events").child(currentEventID).child("comments");

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize eventList
        commentsList = new ArrayList<>();

        // Set up the EventAdapter
        commentadapter = new CommentAdapter(commentsList);
        recyclerView.setAdapter(commentadapter);

        //loadComments();
    }

    /*private void loadComments() {
        commentRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Handle null data
                    if (snapshot.getValue() != null) {
                        Comment comment= snapshot.getValue(Comment.class);
                        if (comment != null) {
                            commentsList.add(comment);
                        }
                    }
                }
                commentadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }*/

    public void OnBackBtn7Click(View view) {
        Intent intent = new Intent(this, ViewEventsActivity.class);
        intent.putExtra("utorID", currentUid);
        startActivity(intent);
    }
}
