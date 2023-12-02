package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.TextView;

public class AdminPage extends AppCompatActivity {
    //comment
    DatabaseReference d;
    TextView admin_welcome;
    String utorid;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        utorid = getIntent().getStringExtra("utorID");
        if (utorid != null) {
            admin_welcome = findViewById(R.id.adminWelcomeText);
            admin_welcome.setText("Welcome! " + utorid);
        } else {
            //Log.e("AdminPageDebug", "Received utorid is null");
            admin_welcome.setText("Welcome! (utorid is null)");
        }


        d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();
    }

    // Method to handle the button click and launch the event announcement page
    public void onAnnounceEventClick(View view) {
        Intent intent = new Intent(this, AnnounceEventActivity.class);
        intent.putExtra("AdminID", utorid);
        startActivity(intent);
    }
    public void onViewEventsClick(View view) {
        Intent intent = new Intent(this, ViewEventsActivity.class);
        intent.putExtra("AdminID", utorid);
        startActivity(intent);
    }

    public void onViewAnnouncementClick(View view) {
        Intent intent = new Intent(this, ViewAnnouncements.class);
        intent.putExtra("AdminID", utorid);
        startActivity(intent);
    }

    public void onMakeAnnouncementClick(View view) {
        Intent intent = new Intent(this, AdminAnnounce.class);
        intent.putExtra("AdminID", utorid);
        startActivity(intent);
    }

    public void OnLogoutClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}