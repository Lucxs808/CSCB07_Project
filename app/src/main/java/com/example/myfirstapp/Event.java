// Event.java
package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String date;
    private String time;
    private String location;
    private List<String> attendees;
    private List<String> rsvpList;
    private String id;

    public Event() {
        // initialize the list when creating a new event
        attendees = new ArrayList<>();
        rsvpList = new ArrayList<>();
    }

    public List<String> getAttendees() {
        return attendees;
    }

//    public Event() { REMOVE THIS
//        // Default constructor required for Firebase
//        rsvpList = new ArrayList<>();
//    }
// hello
    public Event(String name, String date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public List<String> getRsvpList() {
        return rsvpList;
    }

    public boolean hasStudentRSVPed(String studentUtorID) {
        return rsvpList.contains(studentUtorID);
    }

    public void addRSVP(String studentUtorID) {
        rsvpList.add(studentUtorID);
    }
}
