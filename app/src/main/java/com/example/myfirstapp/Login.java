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
// The Login Class is the Model (represents the logic of the login app)
public class Login extends AppCompatActivity implements LoginView{
    // Declaring Variables
    private TextInputEditText editTextUtorID;
    private TextInputEditText editTextPassword;
    private Button loginbtn;
    private TextView textview;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing Variables
        editTextUtorID = findViewById(R.id.utorID);
        editTextPassword = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login_btn);
        textview = findViewById(R.id.registerNow);

        // Initializing the current Presenter
        // By MVP The presenter receives input and updates the view accordingly and then processes it through this main login class.
        DatabaseReference ref = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference("users");
        presenter = new LoginPresenter(this, ref);

        //This listener redirects to the Register Page (The actual code is implemented in the presenter)
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterNowClicked();
            }
        });
        // When the Login button is pressed this is when we start checking the database to see if the parameters entered are correct. Again this process is completed in Presenter
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginButtonClicked(String.valueOf(editTextUtorID.getText()), String.valueOf(editTextPassword.getText()));
            }
        });
    }

    // This will be used in the Presenter to make Toast messages easier & cleaner to display (prevents redudancy)
    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    // Call this method from presenter when a user wants to go to main page
    @Override
    public void navigateToMainPage(String utorid) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.putExtra("utorID", utorid);
        startActivity(intent);
        finish();
    }
    // Call this method from presenter when a user needs to go to admin page
    @Override
    public void navigateToAdminPage(String utorid) {
        Intent intent = new Intent(Login.this, AdminPage.class);
        intent.putExtra("utorID", utorid);
        startActivity(intent);
        finish();
    }
    // Call this method from presenter when a user wants to go to register page
    @Override
    public void navigateToRegisterPage() {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
        finish();
    }
}