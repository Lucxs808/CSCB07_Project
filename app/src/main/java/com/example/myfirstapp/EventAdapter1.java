package com.example.myfirstapp;

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

    private List<Event> eventList;
    private String currentutorid;

    public EventAdapter1(List<Event> eventList, String currentutorid) {
        this.eventList = eventList;
        this.currentutorid = currentutorid;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_student, parent, false);
        return new EventViewHolder(view);
    }

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
        eventRef.child(currentutorid).setValue(true).addOnSuccessListener(aVoid -> {
            Toast.makeText(context, "You have successfully registered!", Toast.LENGTH_SHORT).show();
        });
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventNameTextView;
        private TextView eventDateTextView;
        private TextView eventTimeTextView;
        private TextView eventLocationTextView;
        private Button button;
        private Button button1;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView1);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView1);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView1);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView1);
            button = itemView.findViewById(R.id.rsvp_button);
            button1 = itemView.findViewById(R.id.comment_button);
        }
    }
}
