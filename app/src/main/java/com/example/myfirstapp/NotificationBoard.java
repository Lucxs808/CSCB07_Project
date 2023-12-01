package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class NotificationBoard extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/");
    private String currentutorid;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_notification_board);

        currentutorid = getIntent().getStringExtra("utorid");

        Spinner spinner = findViewById(R.id.spinnerOption);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fetchNotifications("announcements", true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
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
                if (isAnnouncement){
                    ArrayList<Announcement> notifications = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if (snapshot.getValue() != null){
                            Announcement announcement = snapshot.getValue(Announcement.class);
                            if (announcement != null){
                                notifications.add(announcement);
                            }
                        }
                    }
                    displayFragment(notifications, true);
                } else {
                    ArrayList<Event> notifications = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if (snapshot.getValue() != null){
                            Event event = snapshot.getValue(Event.class);
                            if (event != null){
                                event.setId(snapshot.getKey());
                                notifications.add(event);
                            }
                        }
                    }
                    displayFragment(notifications, false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayFragment(ArrayList<?> notifications, boolean isAnnouncement){
        NotificationList fragment = NotificationList.newInstance(notifications, isAnnouncement, currentutorid);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}
