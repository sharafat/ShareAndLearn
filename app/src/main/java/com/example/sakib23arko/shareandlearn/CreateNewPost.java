package com.example.sakib23arko.shareandlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CreateNewPost extends AppCompatActivity {


    private Button Post;
    private EditText Title;
    private EditText Description;
    private Spinner Tag;
    DatabaseReference userInfoDatabase;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
        Title = findViewById(R.id.TitleID);
        Description = findViewById(R.id.DescriptionID);
        Tag = findViewById(R.id.TagID);
        Post = findViewById(R.id.ClickHereToPostID);

        userInfoDatabase = FirebaseDatabase.getInstance().getReference("USER");


        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PleasePostNow();
                startActivity(new Intent(CreateNewPost.this, Homepage.class));
                Toast.makeText(CreateNewPost.this, "Successfully Uploaded", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void PleasePostNow() {
        String TitleName = Title.getText().toString().trim();
        String DescriptionName = Description.getText().toString().trim();
        String TagName = Tag.getSelectedItem().toString();

        if( TextUtils.isEmpty(TitleName) || TextUtils.isEmpty(DescriptionName) || TextUtils.isEmpty(TagName) ) {

            Toast.makeText(CreateNewPost.this, "Fill UP properly", Toast.LENGTH_LONG).show();

        } else {
            String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
            String ID = userInfoDatabase.push().getKey();

            infoOfUser userInfo = new infoOfUser(TitleName, DescriptionName, TagName, timeStamp);
            userInfoDatabase.child(ID).setValue(userInfo);

            Toast.makeText(CreateNewPost.this, "SuccessFully Added", Toast.LENGTH_LONG).show();
        }
    }
}
