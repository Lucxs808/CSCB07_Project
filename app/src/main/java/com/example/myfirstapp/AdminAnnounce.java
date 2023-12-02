package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAnnounce extends AppCompatActivity {
    private EditText DateEdit;
    private EditText ContentEdit;
    private String ID;

    private DatabaseReference announcementsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_announcements);

        //Initialize firebase
        announcementsReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference("announcements");


        DateEdit = findViewById(R.id.editAnnounceDate );
        ContentEdit = findViewById(R.id.editAnnounceContent);
        Button makeAnnounce = findViewById(R.id.MakeAnnounce);

        //Used for storing in firebase
        ID = getIntent().getStringExtra("utorID");

        makeAnnounce.setOnClickListener(v -> {
            makeAnnouncement();
            Toast.makeText(AdminAnnounce.this,"You have posted a new announcement",Toast.LENGTH_SHORT).show();
        });
    }

    private void makeAnnouncement() {
        String adminID = ID;
        String date = DateEdit.getText().toString();
        String content = ContentEdit.getText().toString();

        if (adminID.isEmpty() || date.isEmpty() || content.isEmpty()) {
            return;
        }

        Announcement announce = new Announcement(adminID, date, content);

        String announcementKey = announcementsReference.push().getKey();
        assert announcementKey != null;
        announcementsReference.child(announcementKey).setValue(announce);

    }

    public void OnBackBtn4Click(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminPage.class);
        intent.putExtra("utorID", ID);
        startActivity(intent);
    }
}
