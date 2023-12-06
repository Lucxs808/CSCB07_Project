package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter2 extends RecyclerView.Adapter<EventAdapter2.EventViewHolder> {
    private final List<Event> eventsList;
    public EventAdapter2(List<Event> eventsList){
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventAdapter2.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_comment, parent, false);
        return new EventViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventAdapter2.EventViewHolder holder, int position) {
        Event currentEvent = eventsList.get(position);
        holder.eventNameTextView.setText("Name: " + currentEvent.getName());
        holder.eventDateTextView.setText("Date: " + currentEvent.getDate());
        holder.eventTimeTextView.setText("Time: " + currentEvent.getTime());
        holder.eventLocationTextView.setText("Place: " + currentEvent.getLocation());

        //Start the SubmitComment activity
        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SubmitComment.class);

            //Try to pass the eventID to SubmitComment activity
            //intent.putExtra("eventID", currentEvent.getId());
            v.getContext().startActivities(new Intent[]{intent});
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView eventNameTextView;
        private final TextView eventDateTextView;
        private final TextView eventTimeTextView;
        private final TextView eventLocationTextView;
        private final Button button;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView2);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView2);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView2);
            eventLocationTextView = itemView.findViewById(R.id.eventLocationTextView2);
            button = itemView.findViewById(R.id.comment_button);
        }
    }
}
