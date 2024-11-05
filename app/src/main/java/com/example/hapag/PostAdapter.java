package com.example.hapag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }
    // Method to add a new post to the RecyclerView
    public void addPost(Post post) {
        postList.add(post);
        notifyItemInserted(postList.size() - 1); // Notify adapter that an item has been inserted
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.tvDate.setText(post.getDate());
        holder.tvTime.setText(post.getTime());
        holder.tvTitle.setText(post.getTitle());
        holder.tvUpvotes.setText(String.valueOf(post.getUpvotes()));
        holder.tvDownvotes.setText(String.valueOf(post.getDownvotes()));

        holder.btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setUpvotes(post.getUpvotes() + 1);
                holder.tvUpvotes.setText(String.valueOf(post.getUpvotes()));
            }
        });

        holder.btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setDownvotes(post.getDownvotes() + 1);
                holder.tvDownvotes.setText(String.valueOf(post.getDownvotes()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername, tvDate, tvTime, tvTitle, tvUpvotes, tvDownvotes;
        ImageView btnUpvote, btnDownvote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUpvotes = itemView.findViewById(R.id.tvUpvotes);
            tvDownvotes = itemView.findViewById(R.id.tvDownvotes);
            btnUpvote = itemView.findViewById(R.id.btnUpvote);
            btnDownvote = itemView.findViewById(R.id.btnDownvote);
        }
    }
}