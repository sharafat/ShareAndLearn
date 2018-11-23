package com.example.sakib23arko.shareandlearn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    private Button button;
    ArrayList<infoOfUser> userlist ;
    ListView ListUserView;
    Bundle bundle;
    DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        userDatabase = FirebaseDatabase.getInstance().getReference("USER");


        button = findViewById(R.id.CreateNewPostID);
        userlist = new ArrayList<>();
        ListUserView = findViewById(R.id.ListUserViewID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,CreateNewPost.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(Homepage.this, "WOW", Toast.LENGTH_LONG).show();
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                int counter = 0;
                for(DataSnapshot X : dataSnapshot.getChildren()) {
                    userlist.add(X.getValue(infoOfUser.class));
                     counter++;
                     Toast.makeText(Homepage.this, "WasdasdOW" + counter , Toast.LENGTH_SHORT).show();
                }
//
                UserList adapter = new UserList(Homepage.this, userlist);
                ListUserView.setAdapter(adapter);
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
