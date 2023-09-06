package com.example.eyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView profileName;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName=findViewById(R.id.profileName);
        mAuth= FirebaseAuth.getInstance();

        FirebaseUser user= mAuth.getCurrentUser();

        String email= user.getEmail();

        profileName.setText(""+email);

//        reference= FirebaseDatabase.getInstance().getReference().child("users");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String userEmail= snapshot.child("email").getValue().toString();
//                if(email.equals(userEmail)){
//                    String name= snapshot.child("name").getValue().toString();
//                    profileName.setText(""+name);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}