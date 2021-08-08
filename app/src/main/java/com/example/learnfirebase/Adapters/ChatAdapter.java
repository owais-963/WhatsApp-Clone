package com.example.learnfirebase.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnfirebase.Model.Messages;
import com.example.learnfirebase.R;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Messages> messages;
    Context context;

    public ChatAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    int SENDER_VIEW_TYPE=1;
    int RECIVER_VIEW_TYPE=2;

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {



        if (viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender_msg,parent,false);
            return new SenderViewHolder(view);

        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_recive_msg,parent,false);
            return new Reciverviewholder(view);

        }



    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getuID().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        Messages messages1=messages.get(position);

        if (holder.getClass()==SenderViewHolder.class){
        //if (FirebaseAuth.getInstance().getUid()==messages1.getuID()){
            ((SenderViewHolder)holder).sendMsg.setText(messages1.getMessage());
        }else {
            ((Reciverviewholder)holder).reciveMsg.setText(messages1.getMessage());
        }

    }

    @Override
    public int getItemCount() {

       return messages.size();

    }

    public class Reciverviewholder extends RecyclerView.ViewHolder{

        TextView reciveMsg,reciveTime;

        public Reciverviewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            reciveMsg=itemView.findViewById(R.id.reciveText);
            reciveTime=itemView.findViewById(R.id.reciveTime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView sendMsg,sendTime;


        public SenderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            sendMsg=itemView.findViewById(R.id.send_text);
            sendTime=itemView.findViewById(R.id.sendTime);
        }
    }

}
