package com.example.sakib23arko.shareandlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class PostDetails extends AppCompatActivity {

    TextView PostTitle, PostTime, Postdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

//        infoOfUser details = getIntent().getExtras("country");
        String TitleName = getIntent().getStringExtra("Title");
        String TimeName = getIntent().getStringExtra("Timee");
        String DescriptionName = getIntent().getStringExtra("Description");

        PostTitle = findViewById(R.id.ProjectTitleID);
        PostTime = findViewById(R.id.TimeID);
        Postdetails = findViewById(R.id.InformationID);

        PostTitle.setText(TitleName);
        PostTime.setText(TimeName);
        Postdetails.setText(DescriptionName);


    }
}
