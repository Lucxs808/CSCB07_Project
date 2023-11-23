package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextView textview;
    TextInputEditText editTextUtorID;
    TextInputEditText editTextPassword;
    Button loginbtn;
    private LoginChecker loginCheck;
    DatabaseReference d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginCheck = new LoginChecker(d);
        editTextUtorID = findViewById(R.id.utorID);
        editTextPassword = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login_btn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utorid = String.valueOf(editTextUtorID.getText());
                String password = String.valueOf(editTextPassword.getText());
                if (TextUtils.isEmpty(utorid)){
                    Toast.makeText(Login.this,"UTORid cannot be blank",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"password cannot be blank",Toast.LENGTH_SHORT).show();
                    return;
                }
                loginCheck.verifylogin(utorid, password, Login.this, new LoginCall.AuthenticateCall() {
                    @Override
                    public void authenticatieCall(boolean isAdmin) {

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
}