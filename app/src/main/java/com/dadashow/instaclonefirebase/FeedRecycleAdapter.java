package com.dadashow.instaclonefirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecycleAdapter extends RecyclerView.Adapter<FeedRecycleAdapter.PostHolder>{
    private ArrayList<String> emails;
    private ArrayList<String> comments;
    private ArrayList<String> urls;

    public FeedRecycleAdapter(ArrayList<String> emails, ArrayList<String> comments, ArrayList<String> urls) {
        this.emails = emails;
        this.comments = comments;
        this.urls = urls;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_row_layout,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.commentText.setText(comments.get(position));
        holder.emailText.setText(emails.get(position));
        Picasso.get().load(urls.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView emailText;
        TextView commentText;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recyclerview_imageview);
            emailText=itemView.findViewById(R.id.recyclerview_emailtext);
            commentText=itemView.findViewById(R.id.recyclerview_commenttext);
        }
    }
}
