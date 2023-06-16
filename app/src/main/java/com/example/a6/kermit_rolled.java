package com.example.a6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class kermit_rolled extends AppCompatActivity {

    VideoView rolled;
    String videopath;
    Uri uri;
    ImageView returntomenu;
    String currentuserID;

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            HashMap user = new HashMap();
            user.put("ppic8", true);
            reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.kermit_rolled);
        rolled = findViewById(R.id.rolled);
        returntomenu = findViewById(R.id.return_to_menu);

        videopath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        uri = Uri.parse(videopath);
        rolled.setVideoURI(uri);

        rolled.start();
    }

    public void onClick(View view) {
        if(view == returntomenu){
            startActivity(new Intent(this, menu.class));
            onRestart();
        }

    }

    @Override
    public void onBackPressed() {

    }
}
