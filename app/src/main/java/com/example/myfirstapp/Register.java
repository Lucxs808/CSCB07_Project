package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    utoridDatabaseChecker checkUtorid = new utoridDatabaseChecker();
    TextInputEditText editTextUtorID;
    TextInputEditText editTextPassword;
    Button registerbtn;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUtorID = findViewById(R.id.utorID);
        editTextPassword = findViewById(R.id.password);
        registerbtn = findViewById(R.id.register_btn);
        textview = findViewById(R.id.loginNow);

        textview.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        registerbtn.setOnClickListener(v -> {
            String utorid = String.valueOf(editTextUtorID.getText());
            String password = String.valueOf(editTextPassword.getText());

            if(TextUtils.isEmpty(utorid)){
                Toast.makeText(Register.this,"Enter utorID",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                return;
            }
            checkUtorid.checkUtorIDExists(utorid, exists -> {
                //runs if not admin or student
                //will show toast if duplicate
                if(!exists) {
                    Student s = new Student(utorid, password);
                    DatabaseReference d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();
                    StudentReg newStudent = new StudentReg(d);
                    newStudent.pushStudentToDatabase(s);
                    Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("utorID", utorid);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                }else{
                    // UTORid already exists
                    Toast.makeText(Register.this, "UToid already exists, choose a different UTORid", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}