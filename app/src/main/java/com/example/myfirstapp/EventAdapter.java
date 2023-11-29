package com.example.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;

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

        private TextView eventNameTextView;
        private TextView eventDateTextView;
        private TextView eventTimeTextView;
        private TextView eventLocationTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.AdminID);
            eventDateTextView = itemView.findViewById(R.id.Announcement_view);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView);
        }

        public void bind(Event event) {
            eventNameTextView.setText("Name: " + event.getName());
            eventDateTextView.setText("Date: " + event.getDate());
            eventTimeTextView.setText("Time: " + event.getTime());
            eventLocationTextView.setText("Place: " + event.getLocation());
        }
    }
}
