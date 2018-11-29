package com.example.sakib23arko.shareandlearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.BitSet;


public class userProfile extends AppCompatActivity implements View.OnClickListener {

    private static final int PROFILE_IMAGE = 100;
    private ImageView userImage;
    private TextView addChange;
    private EditText userName;
    private Button saveButton;
    private Uri uriProfileImage;
    private ProgressBar progressBarUserProfileImage;
    private String profileImageUrl;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImage = (ImageView) findViewById(R.id.userImageViewId);
        addChange = (TextView) findViewById(R.id.addChangePhotoId);
        userName = (EditText) findViewById(R.id.userNameProfileId);
        saveButton = (Button) findViewById(R.id.saveUserProfileId);
        progressBarUserProfileImage = (ProgressBar) findViewById(R.id.progressBarUserProfileImageId);

        mAuth=FirebaseAuth.getInstance();

        loadUserInformation();

        addChange.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(userProfile.this,MainActivity.class));
        }
    }

    private void loadUserInformation() {
        FirebaseUser user= mAuth.getCurrentUser();

        if(user!=null){
            if(user.getPhotoUrl()!=null){
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(userImage);
                Log.d("profilepic",user.getPhotoUrl().toString());
            }
            if(user.getDisplayName()!=null){
                userName.setText(user.getDisplayName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addChangePhotoId) {
            showImageChooser();
        }
        if(v.getId()==R.id.saveUserProfileId){
            saveUserInformation();
        }
    }


    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PROFILE_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PROFILE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                userImage.setImageBitmap(bitmap);
                uploaImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploaImageToFirebaseStorage() {
        StorageReference userProfileImageRef = FirebaseStorage.getInstance().getReference("profilePictures/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            progressBarUserProfileImage.setVisibility(View.VISIBLE);
            userProfileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBarUserProfileImage.setVisibility(View.GONE);
                            taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            profileImageUrl = uri.toString();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressBarUserProfileImage.setVisibility(View.GONE);
                                            Toast.makeText(userProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBarUserProfileImage.setVisibility(View.GONE);
                            Toast.makeText(userProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void saveUserInformation() {
        String name=userName.getText().toString();
        if(name.isEmpty()){
            userName.setText("Please enter name");
            userName.requestFocus();
            return;
        }
        FirebaseUser user= mAuth.getCurrentUser();

        if(user!=null && profileImageUrl!=null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(userProfile.this,Homepage.class));
                        Toast.makeText(userProfile.this,"Profile Updated",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(userProfile.this,"Some error occured",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }

}
