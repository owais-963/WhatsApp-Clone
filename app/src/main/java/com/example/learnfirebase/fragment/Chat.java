package com.example.learnfirebase.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.learnfirebase.Adapters.RecyclerViewChatSecAdaper;
import com.example.learnfirebase.LoginActivity;
import com.example.learnfirebase.MainActivity;
import com.example.learnfirebase.Model.Users;
import com.example.learnfirebase.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Chat extends Fragment {

    public Chat() {
        // Required empty public constructor
    }

    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView recyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat, container, false);

        auth=FirebaseAuth.getInstance();


        String logInUID=auth.getUid();

        RecyclerViewChatSecAdaper adapter=new RecyclerViewChatSecAdaper(list,getContext());

        database = FirebaseDatabase.getInstance();
        recyclerView=view.findViewById(R.id.chat_recyclerView);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        //Toast.makeText(getContext(), "Database " +database.getReference() , Toast.LENGTH_SHORT).show();

        DatabaseReference data=database.getReference().child("User");
                data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Toast.makeText(getContext(), "name "+snapshot.getChildren(), Toast.LENGTH_SHORT).show();
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    if (!users.getUserId().equals(logInUID)) {

                        list.add(users);

                    }

                    //Toast.makeText(getContext(), "onDataChange: "+users.getUserId(), Toast.LENGTH_SHORT).show();




                    //Toast.makeText(getContext(), "key "+logINuser , Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                Toast.makeText(getContext(), "Failed to read data " +error.toException(), Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

}