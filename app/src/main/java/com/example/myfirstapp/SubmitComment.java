package com.example.myfirstapp;

import com.example.myfirstapp.Comment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class SubmitComment extends AppCompatActivity {

    private EditText subjectInput, bodyInput, ratingInput;
    private DatabaseReference commentsRef;
    private String currentUid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_comment);

        // Firebase initialization
        commentsRef = FirebaseDatabase.getInstance().getReference("comments");
        currentUid = getIntent().getStringExtra("utorID");

        // UI elements
        subjectInput = findViewById(R.id.editTextComment);
        bodyInput = findViewById(R.id.editTextBody);
        ratingInput = findViewById(R.id.editTextRating);
        Button submitButton = findViewById(R.id.submitcbtn);

        submitButton.setOnClickListener(v -> submitComment());
    }

    private void submitComment() {
        String subject = subjectInput.getText().toString();
        String body = bodyInput.getText().toString();
        String ratingText = ratingInput.getText().toString();

        if (subject.isEmpty() || body.isEmpty() || ratingText.isEmpty()) {
            showToast("One or more fields missing");
            return;
        }

        int rating = parseRating(ratingText);
        if (rating == -1) {
            showToast("Invalid rating. Please enter a number between 1 and 10.");
            return;
        }

        String commentKey = commentsRef.push().getKey();
        assert commentKey != null;
        commentsRef.child(commentKey).setValue(new Comment(subject, body, rating));

        showToast("Comment submitted with rating: " + rating);
    }

    private int parseRating(String ratingText) {
        try {
            int rating = Integer.parseInt(ratingText);
            if (rating >= 1 && rating <= 10) {
                return rating;
            } else {
                return -1; // Invalid rating
            }
        } catch (NumberFormatException e) {
            return -1; // Invalid rating format
        }
    }

    public void onBackBtnClick(View view) {
        startActivity(new Intent(this, MainActivity.class).putExtra("utorID", currentUid));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}