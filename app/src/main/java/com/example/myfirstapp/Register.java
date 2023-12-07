package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

// The Register Class is the Model (represents the logic of the register app)
public class Register extends AppCompatActivity implements RegisterView {
    // Declaring Variables
    private TextInputEditText editTextUtorID;
    private TextInputEditText editTextPassword;
    private Button registerbtn;
    private TextView textview;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing Variables
        editTextUtorID = findViewById(R.id.utorID);
        editTextPassword = findViewById(R.id.password);
        registerbtn = findViewById(R.id.register_btn);
        textview = findViewById(R.id.loginNow);

        // Initilizing the current Presenter
        // By MVP The presenter receives input and updates the view accordingly and then processes it through this register class.
        presenter = new RegisterPresenter(this);

        //This listener redirects to the Login Page (The actual code is implemented in the presenter)
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginNowClicked();
            }
        });

        // When the Register button is pressed this is when we start checking the database to see if we can create the new user. Again this process is completed in Presenter
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonClicked(String.valueOf(editTextUtorID.getText()), String.valueOf(editTextPassword.getText()));
            }
        });
    }

    // This will be used in the Presenter to make Toast messages easier & cleaner to display (prevents redudancy)
    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Call this method from presenter when a user wants to go to login page
    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    // Call this method from presenter when a user need to go to the main activity page
    @Override
    public void navigateToMainPage(String utorid, String password) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("utorID", utorid);
        startActivity(intent);
        finish();
    }
}