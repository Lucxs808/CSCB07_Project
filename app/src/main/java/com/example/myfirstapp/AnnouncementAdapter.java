package com.example.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private List<Announcement> announcementList;

    public AnnouncementAdapter(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Announcement currentAnnouncement = announcementList.get(position);

        holder.bind(currentAnnouncement);
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView AnnouncementAdminTextView;
        private TextView AnnouncementDateTextView;
        private TextView AnnouncementContentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AnnouncementAdminTextView = itemView.findViewById(R.id.Admin);
            AnnouncementDateTextView = itemView.findViewById(R.id.Announce_date);
            AnnouncementContentTextView = itemView.findViewById(R.id.Announcement_view);
        }

        public void bind(Announcement announcement) {
            AnnouncementAdminTextView.setText("Admin: " + announcement.getAdminID());
            AnnouncementDateTextView.setText("Date: " + announcement.getDate());
            AnnouncementContentTextView.setText("Content: " + announcement.getContent());
        }
    }
}
