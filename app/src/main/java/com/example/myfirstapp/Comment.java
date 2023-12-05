// Assuming Comment.java
package com.example.myfirstapp;

public class Comment {
    private String subject;
    private String body;
    private int rating;

    public Comment() {
        // Default constructor required for Firebase
    }

    public Comment(String subject, String body, int rating) {
        this.subject = subject;
        this.body = body;
        this.rating = rating;
    }

    // Getter and setter methods
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

    // Add getters and setters as needed

