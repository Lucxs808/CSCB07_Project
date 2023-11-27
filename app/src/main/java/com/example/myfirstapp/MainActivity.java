package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    DatabaseReference d;
    Button button;
    TextView user_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String utorid = null;
        boolean hasGrades = false;
        ArrayList<Integer> marks = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        Intent intent = getIntent();
        if (intent != null) {
            utorid = intent.getStringExtra("utorID");
            String password = intent.getStringExtra("password");
            hasGrades = intent.getBooleanExtra("hasGrades", false);
            marks = intent.getIntegerArrayListExtra("marks");
            //Log.d("MainActivityDebug", "Received utorid: " + utorid);
            //Log.d("MainActivityDebug", "Received password: " + password);

            if (utorid != null) {
                user_details = findViewById(R.id.user_details);
                user_details.setText("Welcome! " + utorid);
            } else {
                //Log.e("AdminPageDebug", "Received utorid is null");
                user_details.setText("Welcome!");
            }
        } else {
            //Log.e("AdminPageDebug", "Intent is null");
            user_details.setText("Welcome!");
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
        Button post_checker = findViewById(R.id.post);
        boolean finalHasGrades = hasGrades;
        ArrayList<Integer> finalMarks = marks;
        String finalUtorid = utorid;
        post_checker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (!finalHasGrades) {
                    intent = new Intent(MainActivity.this, InputGrades.class);
                    intent.putExtra("utorid", finalUtorid);
                    intent.putExtra("hasGrades", finalHasGrades);
                    intent.putIntegerArrayListExtra("marks", finalMarks);
                } else {
                    intent = new Intent(MainActivity.this, POStChecker.class);
                }
                startActivity(intent);
            }
        });
    }
}