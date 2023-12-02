package com.example.myfirstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class NotificationList extends Fragment{
    private ArrayList<?> notifications;
    private RecyclerView.Adapter<?> adapter;
    private boolean isAnnouncement;
    private String currentutorid;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        if (getArguments() != null){
            currentutorid = getArguments().getString("utorid");
        }
    }

    public static NotificationList newInstance(ArrayList<?> notifications, boolean isAnnouncement, String currentutorid) {
        NotificationList fragment = new NotificationList();
        Bundle args = new Bundle();
        args.putString("utorid", currentutorid);
        fragment.setArguments(args);
        if (isAnnouncement){
            ArrayList<Announcement> announcementsList = (ArrayList<Announcement>) notifications;
            fragment.notifications = announcementsList;
            fragment.isAnnouncement = true;
            fragment.adapter = new AnnouncementAdapter(announcementsList);
        } else {
            ArrayList<Event> eventsList = (ArrayList<Event>) notifications;
            fragment.notifications = eventsList;
            fragment.isAnnouncement = false;
            fragment.adapter = new EventAdapter1(eventsList, currentutorid);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutID = isAnnouncement ? R.layout.activity_view_announcements : R.layout.activity_view_events1;
        return inflater.inflate(layoutID, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
