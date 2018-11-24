package com.example.sakib23arko.shareandlearn;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class UserList extends ArrayAdapter<infoOfUser> {

    private Activity context;
    private ArrayList<infoOfUser> userlist;

    public UserList(Activity context, ArrayList<infoOfUser> userlist) {
        super(context, R.layout.sample_post, userlist);
        this.context = context;
        this.userlist = userlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItems = inflater.inflate(R.layout.sample_post, null, true);

        TextView textViewTitle = ListViewItems.findViewById(R.id.ProjectTitleID);
        TextView textViewDescription = ListViewItems.findViewById(R.id.InfoID);
        TextView textViewDateTime = ListViewItems.findViewById(R.id.TimeID);

        infoOfUser user = userlist.get(position);

        String TitleName300 = user.getTitle();
        String Description400 = user.getDescription();

        String ShowTitle = "";
        String ShowDescription = "";

        for(int i = 0 ; i < Math.min(200, TitleName300.length()) ; i++) {
            ShowTitle += TitleName300.charAt(i);
        }
        if(TitleName300.length() > 200) ShowTitle += "...";



        for(int i = 0 ; i < Math.min(300, Description400.length()) ; i++) {
            ShowDescription += Description400.charAt(i);
        }
        if(Description400.length() > 300) ShowDescription += "...";


        textViewTitle.setText(ShowTitle);
        textViewDescription.setText(ShowDescription);
        textViewDateTime.setText(user.getDateTime());

        return ListViewItems;
    }
}
