package com.example.learnfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnfirebase.Adapters.ChatAdapter;
import com.example.learnfirebase.Model.Messages;
import com.example.learnfirebase.Model.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView menue_bar,userPic,sendMsgBtn;
    TextView user_name;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView chatRecyclerView;
    EditText type_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        getSupportActionBar().hide();
        menue_bar=findViewById(R.id.menueBar);
        userPic=findViewById(R.id.user_image);
        user_name=findViewById(R.id.user_name);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        Intent intent=getIntent();
        String profil_pic_url=intent.getStringExtra("profilePic");
        String name=intent.getStringExtra("userName");
        String guestId=intent.getStringExtra("UserId");
        String hostId=auth.getUid();

        Picasso.get().load(profil_pic_url).placeholder(R.drawable.profile_foreground)
                .into(userPic);

        user_name.setText(name);

        /*
        Toast.makeText(this, "hostID "+hostId, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "guestID "+guestId, Toast.LENGTH_SHORT).show();

         */

        final String senderRoom=guestId+hostId;
        final String reciverRoom=hostId+guestId;

        final ArrayList<Messages> messages=new ArrayList<>();

        chatRecyclerView=findViewById(R.id.chattingRecycclerView);

        ChatAdapter adapter=new ChatAdapter(messages,this);

        chatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);



        database.getReference().child("Chats").child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        messages.clear();

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                            Messages model=dataSnapshot.getValue(Messages.class);
                            messages.add(model);

                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });




        type_msg=findViewById(R.id.type_msg);
        sendMsgBtn=findViewById(R.id.send_msg_btn);
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=type_msg.getText().toString();
                Messages messages=new Messages(msg,hostId);
                messages.setTimestamp(new Date().getTime());
                type_msg.setText("");

                database.getReference().child("Chats").child(senderRoom).push().
                        setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Chats").child(reciverRoom).push().
                                setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });







    }


}