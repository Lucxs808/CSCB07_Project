package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail;
    TextInputEditText editTextStudentnum;
    TextInputEditText editTextPassword;
    Button registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.email);
        editTextStudentnum = findViewById(R.id.stdNum);
        editTextPassword = findViewById(R.id.password);
        registerbtn = findViewById(R.id.register_btn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail);
                int studentNumber = Integer.valueOf(editTextStudentnum.getText().toString());
                String password = String.valueOf(editTextPassword);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (studentNumber == 0) {
                    Toast.makeText(Register.this, "Enter a valid student number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                Student s = new Student(email,studentNumber,password);
                DatabaseReference d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();
                StudentReg newStudent = new StudentReg(new StudentView(), d);
                newStudent.pushStudentToDatabase(s);
                Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}