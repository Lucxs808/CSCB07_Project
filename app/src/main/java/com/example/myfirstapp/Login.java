package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextView textview;
    TextInputEditText editTextUtorID;
    TextInputEditText editTextPassword;
    Button loginbtn;
    DatabaseReference d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize references
        editTextUtorID = findViewById(R.id.utorID);
        editTextPassword = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login_btn);
        textview = findViewById(R.id.registerNow);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String utorid = String.valueOf(editTextUtorID.getText());
                final String password = String.valueOf(editTextPassword.getText());
                if (TextUtils.isEmpty(utorid) || TextUtils.isEmpty(password) ){
                    Toast.makeText(Login.this,"Both UTORid and password are required",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Query database for the provided UTORid
                d.child("students").child(utorid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Test
                        Log.d("FirebaseDebug", "Snapshot: " + snapshot.getValue());
                        //Check if UTORid exists in the student node
                        if (snapshot.exists()){
                            String storedPassword = snapshot.child("password").getValue(String.class);
                            if (password.equals(storedPassword)){
                                //password matches, login successful
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("utorID",utorid);
                                intent.putExtra("password", password);
                                startActivity(intent);
                                finish();
                            }else{
                                //password does not match
                                Toast.makeText(Login.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            // UTORid does not exist in student node
                            // Now check Admin node
                            checkAdminLogin(utorid, password);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this,"Database error: " +error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void checkAdminLogin(final String utorid, final String password){
        d.child("admin").child(utorid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // Check if UTORid is in the admin node
               if (snapshot.exists()){
                   // Check if the password matches
                   String storedPassword = snapshot.child("password").getValue(String.class);
                   if (password.equals(storedPassword)){
                       // Password matches, login successful for admin
                       Intent intent = new Intent(Login.this, AdminPage.class);
                       intent.putExtra("utorID",utorid);
                       intent.putExtra("password", password);
                       startActivity(intent);
                       finish();
                   }else{
                       Toast.makeText(Login.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(Login.this,"UTORid Not Found",Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this,"Database Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}