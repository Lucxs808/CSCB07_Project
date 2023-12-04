package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private final List<Complaint> complaintList;

    public ComplaintAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint currentComplaint = complaintList.get(position);

        holder.bind(currentComplaint);
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ComplaintSubjectTextView;
        private final TextView ComplaintBodyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ComplaintSubjectTextView = itemView.findViewById(R.id.complaintsubject);
            ComplaintBodyTextView = itemView.findViewById(R.id.complaintbody);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Complaint complaint) {
            ComplaintSubjectTextView.setText("Subject: " + complaint.getSubject());
            ComplaintBodyTextView.setText("Body: " + complaint.getBody());
        }
    }
}