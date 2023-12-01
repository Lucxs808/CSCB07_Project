package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myfirstapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class AdminPage extends AppCompatActivity {
    //comment
    DatabaseReference d;
    Button button;
    TextView admin_welcome;
    String utorid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        if (intent != null) {
            utorid = intent.getStringExtra("utorID");
            String password = intent.getStringExtra("password");
            //Log.d("AdminPageDebug", "Received utorid: " + utorid);
            //Log.d("AdminPageDebug", "Received password: " + password);

            if (utorid != null) {
                admin_welcome = findViewById(R.id.adminWelcomeText);
                admin_welcome.setText("Welcome! " + utorid);
            } else {
                //Log.e("AdminPageDebug", "Received utorid is null");
                admin_welcome.setText("Welcome! (utorid is null)");
            }
        } else {
            //Log.e("AdminPageDebug", "Intent is null");
            admin_welcome.setText("Welcome! (Intent is null)");
        }

        d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();
        button = findViewById(R.id.logout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Method to handle the button click and launch the event announcement page
    public void onAnnounceEventClick(View view) {
        Intent intent = new Intent(this, AnnounceEventActivity.class);
        startActivity(intent);
    }
    public void onViewEventsClick(View view) {
        Intent intent = new Intent(this, ViewEventsActivity.class);
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
}