package com.example.learnfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnfirebase.Model.Users;
import com.example.learnfirebase.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    Button signup;
    EditText email, password, name;
    TextView login;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        signup=binding.signUp;
        email=binding.email;
        password=binding.password;
        name=binding.name;
        login=binding.haveAnAccount;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account please wait");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String emailAdress=email.getText().toString();
                String pass=password.getText().toString();
                String userName=name.getText().toString();
                auth.createUserWithEmailAndPassword(emailAdress,pass).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "User create Successfully", Toast.LENGTH_SHORT).show();
                                Users users=new Users(userName,emailAdress,pass);
                                String id=task.getResult().getUser().getUid();
                                database.getReference().child("User").child(id).setValue(users);
                                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else {
                                Log.v("task", "Exception: "+task.getException().getMessage());
                            }
                        }
                    });


            }
        });
    }
}