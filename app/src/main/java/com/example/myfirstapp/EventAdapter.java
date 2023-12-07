package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<Event> eventList;
    private final String currentUid;

    public EventAdapter(List<Event> eventList, String currentUid) {
        this.eventList = eventList;
        this.currentUid = currentUid;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events")
                .child(currentEvent.getId()).child("ratings");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;
                int count = 0;

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Integer rating = snapshot1.getValue(Integer.class);
                    if (rating != null){
                        sum += rating;
                    } else {
                        sum += 0;
                    }
                    count ++;
                }
                double average;
                if (count > 0){
                    average = (double) sum / (double) count;
                    @SuppressLint("DefaultLocale") String average1;
                    average1 = String.format("%.2f", average);
                    average = Double.parseDouble(average1);

                } else {
                    average = 0;
                }
                holder.eventAveRating.setText("Rating: " + average);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set the event details (name, date, time, location)
        holder.bind(currentEvent);
        String ID = currentEvent.getId();
        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CheckFeedback.class);
            intent.putExtra("eventID", ID);
            intent.putExtra("utorID", currentUid);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView eventNameTextView;
        private final TextView eventDateTextView;
        private final TextView eventTimeTextView;
        private final TextView eventLocationTextView;
        private final TextView eventAveRating;
        private Button button;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView);
            eventAveRating = itemView.findViewById(R.id.eventAverageRating);
            button = itemView.findViewById(R.id.buttonFeedback);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Event event) {
            eventNameTextView.setText("Name: " + event.getName());
            eventDateTextView.setText("Date: " + event.getDate());
            eventTimeTextView.setText("Time: " + event.getTime());
            eventLocationTextView.setText("Place: " + event.getLocation());
        }
    }
}
