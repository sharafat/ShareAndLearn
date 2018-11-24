package com.example.sakib23arko.shareandlearn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    private Button button, myProfileButton;
    ArrayList<infoOfUser> userlist;
    ListView ListUserView;
    Bundle bundle;
    TextView continueReading;
    DatabaseReference userDatabase;
    private Button signoutButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        userDatabase = FirebaseDatabase.getInstance().getReference("USER");


        myProfileButton = findViewById(R.id.myProfileButtonId);
        button = findViewById(R.id.CreateNewPostID);
        signoutButton = findViewById(R.id.signoutButtonID);
        userlist = new ArrayList<>();
        ListUserView = findViewById(R.id.ListUserViewID);
        continueReading = findViewById(R.id.continueReadingid);

        mAuth=FirebaseAuth.getInstance();

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(Homepage.this,"You have signout",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(Homepage.this,MainActivity.class));
            }
        });


        ListUserView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText(Homepage.this, "sdakdas", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Homepage.this, PostDetails.class);
                //Get the value of the item you clicked

                infoOfUser itemClicked = userlist.get(position);
                intent.putExtra("Title", itemClicked.getTitle());
                intent.putExtra("Timee", itemClicked.getDateTime());
                intent.putExtra("Description", itemClicked.getDescription());
                startActivity(intent);

            }
        });

        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, userProfile.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, CreateNewPost.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                for (DataSnapshot X : dataSnapshot.getChildren()) {
                    userlist.add(0, X.getValue(infoOfUser.class));
                }
                UserList adapter = new UserList(Homepage.this, userlist);
                ListUserView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
