package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
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

        // Set the event details (name, date, time, location)
        holder.bind(currentEvent);
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

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView);
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
