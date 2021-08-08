package com.example.learnfirebase.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnfirebase.ChattingActivity;
import com.example.learnfirebase.Model.Users;
import com.example.learnfirebase.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewChatSecAdaper extends RecyclerView.Adapter<RecyclerViewChatSecAdaper.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public RecyclerViewChatSecAdaper(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.chat_sec_sample,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewChatSecAdaper.ViewHolder holder, int position) {

        Users users=list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.profile_foreground)
                .into(holder.profilePic);
        holder.personName.setText(users.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ChattingActivity.class);
                intent.putExtra("UserId",users.getUserId());
                intent.putExtra("profilePic",users.getProfilepic());
                intent.putExtra("userName",users.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profilePic;
        TextView personName,lastMessage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            profilePic=itemView.findViewById(R.id.user_image);
            personName=itemView.findViewById(R.id.personName);
            lastMessage=itemView.findViewById(R.id.lastMessage);
        }
    }

}
