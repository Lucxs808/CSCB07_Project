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
        post_checker.setOnClickListener(v -> {
            DatabaseReference studentRef = studentsRef.child(utorid);
            studentRef.child("grades").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean hasGrades = dataSnapshot.getValue(Boolean.class);
                    if (hasGrades != null && hasGrades) {
                        studentRef.child("marksList").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot gradeListSnapshot) {
                                ArrayList<Integer> finalGradeList = new ArrayList<>();
                                for (DataSnapshot gradeSnapshot : gradeListSnapshot.getChildren()) {
                                    Integer grade = gradeSnapshot.getValue(Integer.class);
                                    if (grade != null) {
                                        finalGradeList.add(grade);
                                    }
                                }
                                Intent intent = new Intent(MainActivity.this, POStChecker.class);
                                intent.putExtra("utorid", utorid);
                                intent.putIntegerArrayListExtra("marksList", finalGradeList);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("FirebaseDebug", "Error");
                            }
                        });
                    } else {
                        // If hasGrades is not true, this means marks still need to be inputted.
                        Intent intent = new Intent(MainActivity.this, InputGrades.class);
                        intent.putExtra("utorid", utorid);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FirebaseDebug", "Error");
                }
            });
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