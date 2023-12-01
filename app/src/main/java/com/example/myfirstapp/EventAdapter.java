package com.example.myfirstapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//import com.example.myfirstapp.EventAdapter.RSVPClickListener;

//public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
//
//    private List<Event> eventList;
//    private Button rsvpButton;
//
//    public EventAdapter(List<Event> eventList) {
//        this.eventList = eventList;
//    }
//
//    @NonNull
//    @Override
//    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
//        return new EventViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
//        Event currentEvent = eventList.get(position);
//
//        // Set the event details (name, date, time, location)
//        holder.bind(currentEvent);
//    }
//
//    @Override
//    public int getItemCount() {
//        return eventList.size();
//    }
//
//    static class EventViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView eventNameTextView;
//        private TextView eventDateTextView;
//        private TextView eventTimeTextView;
//        private TextView eventLocationTextView;
//        private Button rsvpButton;
//
//        public EventViewHolder(@NonNull View itemView) {
//            super(itemView);
//            eventNameTextView = itemView.findViewById(R.id.AdminID);
//            eventDateTextView = itemView.findViewById(R.id.Announcement_view);
//            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
//            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView);
//            rsvpButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Event selectedEvent = eventList.get(position);
//
//                        // Assuming "studentID" is the unique identifier of the student
//                        String studentID = "studentID";
//
//                        // Check if the student is not already RSVP'd
//                        if (!selectedEvent.getAttendees().contains(studentID)) {
//                            // Add the student to the RSVP list
//                            selectedEvent.getAttendees().add(studentID);
//
//                            // Update the Firebase database
//                            updateEvent(selectedEvent);
//                        }
//                    }
//                }
//            });
//        }
//
//        public void bind(Event event) {
//            eventNameTextView.setText("Name: " + event.getName());
//            eventDateTextView.setText("Date: " + event.getDate());
//            eventTimeTextView.setText("Time: " + event.getTime());
//            eventLocationTextView.setText("Place: " + event.getLocation());
//        }
//        private void updateEvent(Event event) {
//            DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("events").child(event.getId());
//            eventRef.setValue(event);
//        }
//    }
//}
// EventAdapter.java
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private RSVPClickListener rsvpClickListener;

    public EventAdapter(List<Event> eventList, RSVPClickListener rsvpClickListener) {
        this.eventList = eventList;

        // Check if the provided rsvpClickListener is null
        if (rsvpClickListener == null) {
            throw new IllegalArgumentException("RSVPClickListener cannot be null");
        }

        this.rsvpClickListener = rsvpClickListener;
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
        holder.bind(currentEvent);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventNameTextView;
        private TextView eventDateTextView;
        private TextView eventTimeTextView;
        private TextView eventLocationTextView;
        private Button rsvpButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventNameTextView = itemView.findViewById(R.id.AdminID);
            eventDateTextView = itemView.findViewById(R.id.Announcement_view);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView);
            rsvpButton = itemView.findViewById(R.id.rsvpButton);

            rsvpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Event selectedEvent = eventList.get(position);
                        rsvpClickListener.onRSVPClick(selectedEvent);

                        // Add a log statement to check if onClick is called
                        Log.d("RSVP", "Button Clicked for: " + selectedEvent.getName());
                    }
                }
            });
        }

        public void bind(Event event) {
            eventNameTextView.setText("Name: " + event.getName());
            eventDateTextView.setText("Date: " + event.getDate());
            eventTimeTextView.setText("Time: " + event.getTime());
            eventLocationTextView.setText("Place: " + event.getLocation());
        }
    }
}

