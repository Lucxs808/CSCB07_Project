package com.example.myfirstapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class utoridDatabaseChecker {
    public interface UtorIDCheckListener {
        void onUtorIDCheckResult(boolean exists);
    }
    private final DatabaseReference d;

    // Initialize the database reference in contructor
    utoridDatabaseChecker(){
        d = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/").getReference("users");
    }
    public void checkUtorIDExists(String utorid, final UtorIDCheckListener listener){

        DatabaseReference adminReference = d.child("admin").child(utorid);
        DatabaseReference studentReference = d.child("students").child(utorid);

        adminReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot adminSnapshot) {
                studentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot studentSnapshot) {
                        listener.onUtorIDCheckResult(adminSnapshot.exists() || studentSnapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onUtorIDCheckResult(false);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onUtorIDCheckResult(false); // Assume UTORid doesn't exist in case of an error
            }
        });
    }
}
