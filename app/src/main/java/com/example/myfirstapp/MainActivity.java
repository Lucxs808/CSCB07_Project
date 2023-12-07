package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    DatabaseReference d;
    TextView user_details;
    String utorid;
    int admissionCategory;
    boolean coop;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilzing utorid: Getting utorID from extra (Login)
        utorid = getIntent().getStringExtra("utorID");
        //Displaying Welcome message on activity_main page
        user_details = findViewById(R.id.user_details);
        user_details.setText("Welcome! " + utorid);

        d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();
        // Logout Button returns back to login page



        //First User Story (POSt Checker)
        Button post_checker = findViewById(R.id.post);
        DatabaseReference studentsRef = FirebaseDatabase.getInstance().getReference().child("users").child("students");
        DatabaseReference coopRef = studentsRef.child(utorid);
        coopRef.child("coop").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean coopValue = dataSnapshot.getValue(Boolean.class);
                coop = coopValue != null && coopValue;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseDebug", "Error fetching coop value");
            }
        });
        coopRef.child("admissionCategory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer admissionCategoryValue = dataSnapshot.getValue(Integer.class);
                admissionCategory = admissionCategoryValue != null ? admissionCategoryValue : 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseDebug", "Error fetching admissionCategory value");
            }
        });
        post_checker.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InputGrades.class);
            intent.putExtra("utorid", utorid);
            startActivity(intent);
        });
    }

    public void onViewNotificationClick(View view) {
        Intent intent = new Intent(this, NotificationBoard.class);
        intent.putExtra("utorID", utorid);
        startActivity(intent);
    }

    public void onViewRegisteredClick(View view) {
        Intent intent = new Intent(this, ViewRegisteredEvents.class);
        intent.putExtra("utorid", utorid);
        startActivity(intent);
    }


    public void OnSubmitComClick(View view) {
        Intent intent = new Intent(this, SubmitComplaint.class);
        intent.putExtra("utorID", utorid);
        startActivity(intent);
    }

    public void OnLogoutClick(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}