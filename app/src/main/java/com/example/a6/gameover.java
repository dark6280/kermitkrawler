package com.example.a6;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class gameover extends AppCompatActivity {

    private static MediaPlayer song1;
    Button startover,mainmenu;
    TextView tvscore;
    MediaPlayer loser;
    int score,firebasescore,firebasetotalscore;
    float totalscore,newscore;
    String currentuserID;
    boolean updated = false;

    Query checkuser;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        loser = MediaPlayer.create(gameover.this,R.raw.loser);
        song1 = MediaPlayer.create(gameover.this,R.raw.song1);
        setContentView(R.layout.game_over);
        startover = findViewById(R.id.btn_startover);
        mainmenu = findViewById(R.id.btn_mainmenu);
        tvscore = findViewById(R.id.tv_score);



        auth = FirebaseAuth.getInstance();

        //db
        db = FirebaseDatabase.getInstance();
        //reference
        reference = db.getReference("userData");
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


            checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
            checkuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        firebasescore = snapshot.child(currentuserID).child("score").getValue(int.class);
                        firebasetotalscore = snapshot.child(currentuserID).child("totalscore").getValue(int.class);
                        newscore = score+ firebasescore;
                        totalscore = score + firebasetotalscore;




                        if(!updated) {
                            updated = true;
                            HashMap user = new HashMap();
                            user.put("score", newscore);
                            user.put("totalscore", totalscore);
                            reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }






        score = (getIntent().getExtras().getInt("score"));
        tvscore.setText("score: " + score);


         IsScoreBest();

        AudioPlay.playAudio(this,R.raw.loser);



    }



    public void IsScoreBest(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null ){
            currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


            checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
            checkuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        int bestscore = snapshot.child(currentuserID).child("bestscore").getValue(int.class);

                        HashMap user = new HashMap();
                        if(score > bestscore){
                            user.put("bestscore", score);
                        }


                        reference = FirebaseDatabase.getInstance().getReference("users");
                        reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

    public void onClick(View view) {
        if(view == startover){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, MainActivity.class));
        }

        if(view == mainmenu){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, menu.class));
        }
    }


    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }
}
