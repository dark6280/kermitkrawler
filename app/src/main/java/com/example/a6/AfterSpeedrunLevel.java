package com.example.a6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import java.util.Objects;

public class AfterSpeedrunLevel extends AppCompatActivity {


    Button tryagain,mainmenu;
    TextView tvyourtime,tv_yourbest;
    int score,firebasescore,firebasetotalscore;
    float time;
    String currentuserID;
    boolean updated = false;

    TextView why;
    String st_time;

    Query checkuser;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;

    int level;


    @SuppressLint("MissingInflatedId")
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
        setContentView(R.layout.afterspeedrun);
        tryagain = findViewById(R.id.btn_tryagain);
        mainmenu = findViewById(R.id.btn_mainmenu);
        tvyourtime = findViewById(R.id.tv_yourtime);
        tv_yourbest = findViewById(R.id.tv_yourbest);
        why = findViewById(R.id.why);



        auth = FirebaseAuth.getInstance();

        //db
        db = FirebaseDatabase.getInstance();
        //reference
        reference = db.getReference("userData");



        st_time = (getIntent().getExtras().getString("Time"));
        tvyourtime.setText("YOUR TIME: " + st_time);


        level = (getIntent().getExtras().getInt("Level"));
        why.setVisibility(View.INVISIBLE);


        updatebest();

        updatebesttextview();
        didtheplayerwinboss();

    }




    public void onClick(View view) {
        if(view == tryagain){
            WhatLevel(level);
        }

        if(view == mainmenu){
            startActivity(new Intent(this, speedrun_mode.class));
        }
    }

    private void WhatLevel(int level) {
        if (level == 1){
            startActivity(new Intent(this, level1_speedrunmode.class));
        }
        if (level == 2){
            startActivity(new Intent(this, level2_speedrunmode.class));
        }
        if (level == 3){
            startActivity(new Intent(this, level3_speedrunmode.class));
        }
        if (level == 4){
            startActivity(new Intent(this, level4_speedrunmode.class));
        }
        if(level ==5){
            startActivity(new Intent(this, boss_fight_speedrunmode.class));
        }
        if(level ==6){
            startActivity(new Intent(this, elmo_boss_fight.class));
        }
    }

    private void updatebest(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null && !Objects.equals(st_time, "DEAD") && !Objects.equals(st_time, "ELMODEAD")){
            currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            time = Float.parseFloat(st_time);

            checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
            checkuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){


                        HashMap user = new HashMap();
                        if(level ==1){
                            float best = snapshot.child(currentuserID).child("best_lvl1").getValue(float.class);
                            if(Istimebest(best,time)){
                                user.put("best_lvl1", time);
                            }
                        }
                        if(level ==2){
                            float best = snapshot.child(currentuserID).child("best_lvl2").getValue(float.class);
                            if(Istimebest(best,time)){
                                user.put("best_lvl2", time);
                            }
                        }
                        if(level ==3){
                            float best = snapshot.child(currentuserID).child("best_lvl3").getValue(float.class);
                            if(Istimebest(best,time)){
                                user.put("best_lvl3", time);
                            }

                        }
                        if(level ==4){
                            float best = snapshot.child(currentuserID).child("best_lvl4").getValue(float.class);
                            if(Istimebest(best,time)){
                                user.put("best_lvl4", time);
                            }
                        }
                        if(level ==5){
                            float best = snapshot.child(currentuserID).child("best_boss_battle").getValue(float.class);
                            if(Istimebest(best,time)){
                                user.put("best_boss_battle", time);
                            }
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

    private boolean Istimebest(float best,float time){
        if(best == 0){
            return true;
        }
        if(time != 0 && time < best){
                return true;
        }
        return false;
    }

    private void updatebesttextview(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


            if(!updated){
                checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
                checkuser.addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            if(level ==1){
                                float best = snapshot.child(currentuserID).child("best_lvl1").getValue(float.class);
                                tv_yourbest.setText("BEST: "+ best);

                            }
                            if(level ==2){
                                float best = snapshot.child(currentuserID).child("best_lvl2").getValue(float.class);
                                tv_yourbest.setText("BEST: "+ best);
                            }
                            if(level ==3){
                                float best = snapshot.child(currentuserID).child("best_lvl3").getValue(float.class);
                                tv_yourbest.setText("BEST: "+ best);

                            }
                            if(level ==4){
                                float best = snapshot.child(currentuserID).child("best_lvl4").getValue(float.class);
                                tv_yourbest.setText("BEST: "+ best);
                            }
                            if(level ==5){
                                float best = snapshot.child(currentuserID).child("best_boss_battle").getValue(float.class);
                                tv_yourbest.setText("BEST: "+ best);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

        }
    }

    private void didtheplayerwinboss(){
        if((!Objects.equals(st_time, "DEAD") && level ==5) ||(Objects.equals(st_time, "ELMODEAD") && level ==6)  ){
            AudioPlay.playAudio(this,R.raw.why);
            AudioPlay.setlooping();
            why.setVisibility(View.VISIBLE);
            ConstraintLayout myLayout = findViewById(R.id.afterspeedrun);
            myLayout.setBackgroundResource(R.mipmap.was_it_worth_it);
            tryagain.setVisibility(View.INVISIBLE);
            mainmenu.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AudioPlay.stopAudio();
                    tryagain.setVisibility(View.VISIBLE);
                    mainmenu.setVisibility(View.VISIBLE);
                }
            }, 10000);
        }
    }

    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }
}
