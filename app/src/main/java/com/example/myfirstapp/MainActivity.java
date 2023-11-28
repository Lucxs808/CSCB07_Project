package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myfirstapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String utorid;
        boolean hasGrades = false;
        ArrayList<Integer> marksList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        Intent intent = getIntent();
        utorid = intent.getStringExtra("utorID");
        String password = intent.getStringExtra("password");
        hasGrades = intent.getBooleanExtra("hasGrades", false);
        marksList = intent.getIntegerArrayListExtra("marksList");
        user_details = findViewById(R.id.user_details);
        user_details.setText("Welcome! " + utorid);
        //Log.d("MainActivityDebug", "Received utorid: " + utorid);
        //Log.d("MainActivityDebug", "Received password: " + password);

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
        //boolean finalHasGrades = hasGrades;
        //String finalUtorid = utorid;
        DatabaseReference studentsRef = FirebaseDatabase.getInstance().getReference().child("users").child("students");

        // Fixed by Lucus
        post_checker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newstudentRef = studentsRef.child(utorid);
                newstudentRef.child("grades").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean hasGrades = dataSnapshot.getValue(Boolean.class);
                        if (hasGrades != null && hasGrades) {
                            newstudentRef.child("marksList").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot gradeListSnapshot) {
                                    ArrayList<Integer> finalGradeList = new ArrayList<>();
                                    for (DataSnapshot gradeSnapshot : gradeListSnapshot.getChildren()) {
                                        Integer grade = gradeSnapshot.getValue(Integer.class);
                                        Log.d("FirebaseDebug", "grade: " + grade);
                                        if (grade != null) {
                                            finalGradeList.add(grade);
                                        }
                                    }
                                    Log.d("FirebaseDebug", "utorid: " + utorid);
                                    Log.d("FirebaseDebug", "hasGrades: " + hasGrades);
                                    Log.d("FirebaseDebug", "finalGradeList: " + finalGradeList);
                                    Intent intent = new Intent(MainActivity.this, POStChecker.class);
                                    intent.putExtra("utorid", utorid);
                                    intent.putIntegerArrayListExtra("marksList", finalGradeList);

                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Intent intent = new Intent(MainActivity.this, InputGrades.class);
                            intent.putExtra("utorid", utorid);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}