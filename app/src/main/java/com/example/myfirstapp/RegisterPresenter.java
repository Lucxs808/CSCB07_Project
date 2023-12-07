package com.example.myfirstapp;

import android.text.TextUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// This is the Register Presenter that handles the actual process of register it interacts between the view and Register model
public class RegisterPresenter {
    private final RegisterView view;
    private final utoridDatabaseChecker checkUtorid;

    // Contructor class takes a view and an instance of utoridDatabaseChecker
    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.checkUtorid = new utoridDatabaseChecker();
    }

    // When the register button is clicked
    public void onRegisterButtonClicked(String utorid, String password) {
        // Cases if input is not correctly entered
        if (TextUtils.isEmpty(utorid)) {
            view.showToast("Enter UTORid");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            view.showToast("Enter password");
            return;
        }
        // A utorid and password is entered, now checking to see if utorid already exists or not
        checkUtorid.checkUtorIDExists(utorid, new utoridDatabaseChecker.UtorIDCheckListener() {
            @Override
            public void onUtorIDCheckResult(boolean exists) {
                // If doesnt exists we create the user and add to our firebase
                if (!exists) {
                    Student s = new Student(utorid, password);
                    DatabaseReference d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference();
                    StudentReg newStudent = new StudentReg(d);
                    newStudent.pushStudentToDatabase(s);
                    view.showToast("Account Created!");
                    view.navigateToMainPage(utorid, password);
                }
                // If exists we do not create the user and display toast message
                else {
                    view.showToast("UTORid already exists, choose a different UTORid or login.");
                }
            }
        });
    }
    // This is for the login now button (Clicking the Login Now Button navigates to login page)
    public void onLoginNowClicked() {
        view.navigateToLogin();
    }
}
