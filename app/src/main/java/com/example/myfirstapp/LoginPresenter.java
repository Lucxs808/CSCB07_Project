package com.example.myfirstapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter{
    private final LoginView view;
    private DatabaseReference databaseReference;

    public LoginPresenter(LoginView view, DatabaseReference databaseReference) {
        this.view = view;
        //this.databaseReference = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference("users");
        this.databaseReference = databaseReference;
    }
    public void onLoginButtonClicked(String utorid, String password) {
        if (isNullOrEmpty(utorid) || isNullOrEmpty(password)) {
            view.showToast("Both UTORid and password are required");
            return;
        }
        // Query database for the provided UTORid
        databaseReference.child("students").child(utorid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String storedPassword = snapshot.child("password").getValue(String.class);
                    if (password.equals(storedPassword)) {
                        // Password matches, login successful for student
                        view.navigateToMainPage(utorid);
                    } else {
                        view.showToast("Incorrect Password");
                    }
                } else {
                    // UTORid does not exist in student node, check Admin node
                    checkAdminLogin(utorid, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showToast("Database error: " + error.getMessage());
            }
        });
    }
    // If the user is not a Student it could still be an admin, now we check to see if the inputted utorID is an admin
    private void checkAdminLogin(final String utorid, final String password) {
        databaseReference.child("admin").child(utorid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String storedPassword = snapshot.child("password").getValue(String.class);
                    if (password.equals(storedPassword)) {
                        // Password matches, login successful for admin
                        view.navigateToAdminPage(utorid);
                    } else {
                        // utorid exists but passwords dont match
                        view.showToast("Incorrect Password");
                    }
                    // All possible cases are exhausted, UTORid does not exist in database
                } else {
                    view.showToast("UTORid Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showToast("Database Error: " + error.getMessage());
            }
        });
    }
    // This is for the Register Now button (Takes you to register page)
    public void onRegisterNowClicked() {
        view.navigateToRegisterPage();
    }
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
