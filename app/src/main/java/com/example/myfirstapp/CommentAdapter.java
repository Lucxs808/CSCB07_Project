package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private final List<Comment> commentsList;

    public CommentAdapter(List<Comment> commentsList){
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment currentComment = commentsList.get(position);
        holder.commentBodyTextView.setText("Comment: " + currentComment.getContent());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder{
        private final TextView commentBodyTextView;

        public CommentViewHolder(@NonNull View itemView){
            super(itemView);

            commentBodyTextView = itemView.findViewById(R.id.commentBodyTextView);
        }
    }
}
