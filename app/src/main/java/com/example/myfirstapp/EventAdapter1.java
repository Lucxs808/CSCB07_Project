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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EventAdapter1 extends RecyclerView.Adapter<EventAdapter1.EventViewHolder> {

    private final List<Event> eventList;
    private final String currentUid;
    public EventAdapter1(List<Event> eventList, String currentUid) {
        this.eventList = eventList;
        this.currentUid = currentUid;
    }


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
        DatabaseReference eventRef2 = FirebaseDatabase.getInstance().getReference("events").child(currentEvent.getId());
        holder.eventNameTextView.setText("Name: " + currentEvent.getName());
        holder.eventDateTextView.setText("Date: " + currentEvent.getDate());
        holder.eventTimeTextView.setText("Time: " + currentEvent.getTime());
        holder.eventLocationTextView.setText("Place: " + currentEvent.getLocation());

        eventRef2.child("attendances").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists() && snapshot.hasChildren()){
                    for (DataSnapshot childSnapshot : snapshot.getChildren()){
                        Boolean attended = childSnapshot.getValue(Boolean.class);
                        if (attended != null && attended){
                            count ++;
                        }
                    }
                    int placeLeft = currentEvent.getParticipantLimit() - count;
                    holder.eventPlaceTextView.setText("Number of places Left: " + placeLeft);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.button.setOnClickListener(v -> addAttendToEvent(currentEvent.getId(), v.getContext()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addAttendToEvent(String eventId, Context context) {
        DatabaseReference eventRef = FirebaseDatabase.getInstance("https://cscb07-group-18-6e750-default-rtdb.firebaseio.com/")
                .getReference("events")
                .child(eventId);
        DatabaseReference eventAttendRef = eventRef.child("attendances");
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                if (event!= null){
                    long participantLimit = event.getParticipantLimit();

                    eventAttendRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(currentUid)) {
                                // User is already registered, i just chnged some things kevin did lol, did i commit???
                                Toast.makeText(context, "You are already registered for this event.", Toast.LENGTH_SHORT).show();
                            } else {
                                long currentAttendCount = snapshot.getChildrenCount();
                                if (currentAttendCount >= participantLimit) {
                                    Toast.makeText(context, "Event is full. Cannot register.", Toast.LENGTH_SHORT).show();
                                } else {
                                    eventAttendRef.child(currentUid).setValue(true).addOnSuccessListener(aVoid ->
                                            Toast.makeText(context, "You have successfully registered!", Toast.LENGTH_SHORT).show());
                                }
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


    static class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView eventNameTextView;
        private final TextView eventDateTextView;
        private final TextView eventTimeTextView;
        private final TextView eventLocationTextView;
        private final TextView eventPlaceTextView;
        private final Button button;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView1);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView1);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView1);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView1);
            eventPlaceTextView = itemView.findViewById(R.id.eventPlaceLeftTextView1);
            button = itemView.findViewById(R.id.rsvp_button);
            Button button1 = itemView.findViewById(R.id.comment_button);
        }
    }
}
