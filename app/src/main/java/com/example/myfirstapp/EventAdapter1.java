package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EventAdapter1 extends RecyclerView.Adapter<EventAdapter1.EventViewHolder> {

    private final List<Event> eventList;
    private final String currentUid;

    public EventAdapter1(List<Event> eventList, String currentUid) {
        this.eventList = eventList;
        this.currentUid = currentUid;
    }
    private int getEventPositionById(String eventId) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getId().equals(eventId)) {
                return i;
            }
        }
        return -1;
    }// EXTRA THING
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_student, parent, false);
        return new EventViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);
        holder.eventNameTextView.setText("Name: " + currentEvent.getName());
        holder.eventDateTextView.setText("Date: " + currentEvent.getDate());
        holder.eventTimeTextView.setText("Time: " + currentEvent.getTime());
        holder.eventLocationTextView.setText("Place: " + currentEvent.getLocation());

        holder.button.setOnClickListener(v -> addAttendToEvent(currentEvent.getId(), v.getContext()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addAttendToEvent(String eventId, Context context){
        DatabaseReference eventRef = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/")
                .getReference("events")
                .child(eventId).child("attendances");
        if (eventList.get(getEventPositionById(eventId)).isEventFull()) {////////
            Toast.makeText(context, "Event is full. Cannot register.", Toast.LENGTH_SHORT).show();
            return;
        }
        eventRef.child(currentUid).setValue(true).addOnSuccessListener(aVoid -> Toast.makeText(context, "You have successfully registered!", Toast.LENGTH_SHORT).show());
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView eventNameTextView;
        private final TextView eventDateTextView;
        private final TextView eventTimeTextView;
        private final TextView eventLocationTextView;
        private final Button button;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView1);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView1);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView1);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView1);
            button = itemView.findViewById(R.id.rsvp_button);
            Button button1 = itemView.findViewById(R.id.comment_button);
        }
    }
}
