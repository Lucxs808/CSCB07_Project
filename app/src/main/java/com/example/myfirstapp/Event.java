// Event.java
package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Event {
    private String name;
    private String date;
    private String time;
    private String location;
    private HashMap<String, Boolean> attendances;
    private String id;
    private int participantLimit; // TRYING
    public int getParticipantLimit() {///////////////////
        return participantLimit;
    }

    public void setParticipantLimit(int participantLimit) {////////////////
        this.participantLimit = participantLimit;
    }

    public boolean isEventFull() {////////////
        return attendances.size() >= participantLimit;
    }

    public Event() {
        // Default constructor required for Firebase
       attendances = new HashMap<>();
    }

    public Event(String name, String date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.attendances = new HashMap<>();
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
    public HashMap<String, Boolean> getAttendances(){
        return attendances;
    }

    public void setAttendances(HashMap<String, Boolean> attendances){
        this.attendances = attendances;
    }

    public void addAttendance(String utorid){
        if (this.attendances == null){
            this.attendances = new HashMap<>();
        }
        this.attendances.put(utorid, true);
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
