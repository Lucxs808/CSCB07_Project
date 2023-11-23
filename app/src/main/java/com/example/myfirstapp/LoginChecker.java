package com.example.myfirstapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginChecker {
    private DatabaseReference d;

    LoginChecker(DatabaseReference d){
        this.d = d;
    }
    public void verifylogin(String utorID, String password, Context context, LoginCall.AuthenticateCall checkUser){
        this.d.child("admins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isAdmin = false;
                for(DataSnapshot admin: snapshot.getChildren()){
                    String adminUtorid = admin.child("utorid").getValue(String.class);
                    String adminPassword = admin.child("password").getValue(String.class);
                    if(adminUtorid.equals(utorID) && adminPassword.equals(password)){
                        isAdmin = true;
                        checkUser.authenticatieCall(true);
                    }
                }
                if(!isAdmin){
                    d.child("customers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean isUser = false;
                            for(DataSnapshot student: snapshot.getChildren()){
                                Student s = student.getValue(Student.class);
                                if (s.getUtorID().equals(utorID) && s.getPassword().equals(password)){
                                    isUser = true;
                                    checkUser.authenticatieCall(false);
                                }
                            }
                            if(!isUser){
                                Toast.makeText(context,"Login failed",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
