package com.example.a6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Objects;

public class speedrun_mode extends AppCompatActivity {

    ImageView returntomenu,iv_level1,iv_level2,iv_level3,iv_level4,iv_boss_battle,iv_elmo_battle;
    String currentuserID;
    TextView level1_time,level2_time,level3_time,level4_time,boss_battle_time,elmo_battle_time;

    Query checkuser;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
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
        setContentView(R.layout.speedrun_mode);
        returntomenu = findViewById(R.id.backtomenu);
        iv_level1 = findViewById(R.id.ivlevel1);
        iv_level2 = findViewById(R.id.ivlevel2);
        iv_level3 = findViewById(R.id.ivlevel3);
        iv_level4 = findViewById(R.id.ivlevel4);
        iv_boss_battle = findViewById(R.id.ivboss_battle);
        iv_elmo_battle = findViewById(R.id.ivelmo_boss_fight);
        level1_time = findViewById(R.id.level1_time);
        level2_time = findViewById(R.id.level2_time);
        level3_time = findViewById(R.id.level3_time);
        level4_time = findViewById(R.id.level4_time);
        boss_battle_time = findViewById(R.id.boss_battle_time);

        auth = FirebaseAuth.getInstance();

        //db
        db = FirebaseDatabase.getInstance();
        //reference
        reference = db.getReference("userData");

        if (!AudioPlay.isplayingAudio) {
            AudioPlay.playAudio(this, R.raw.menusong);
            AudioPlay.setlooping();
        }

        showbest();

    }


    public void onClick(View view) {
        if(view == returntomenu){
            startActivity(new Intent(this, menu.class));
        }
        if(view == iv_level1){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, level1_speedrunmode.class));
        }
        if(view == iv_level2){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, level2_speedrunmode.class));
        }
        if(view == iv_level3){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, level3_speedrunmode.class));
        }
        if(view == iv_level4){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, level4_speedrunmode.class));
        }
        if(view == iv_boss_battle){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, boss_fight_speedrunmode.class));
        }
        if(view == iv_elmo_battle){
            AudioPlay.stopAudio();
            startActivity(new Intent(this, elmo_boss_fight.class));
        }
    }

    public void showbest(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


            checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
            checkuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        float best1 = snapshot.child(currentuserID).child("best_lvl1").getValue(float.class);
                        float best2 = snapshot.child(currentuserID).child("best_lvl2").getValue(float.class);
                        float best3 = snapshot.child(currentuserID).child("best_lvl3").getValue(float.class);
                        float best4 = snapshot.child(currentuserID).child("best_lvl4").getValue(float.class);
                        float best_boss_battle = snapshot.child(currentuserID).child("best_boss_battle").getValue(float.class);

                        HashMap user = new HashMap();
                        if(best1 !=0){
                           level1_time.setText(""+ best1);
                        }
                        if(best2 !=0){
                            level2_time.setText(""+ best2);
                        }
                        if(best3 !=0){
                            level3_time.setText(""+ best3);
                        }
                        if(best4 !=0){
                            level4_time.setText(""+ best4);
                        }
                        if(best_boss_battle !=0){
                            boss_battle_time.setText(""+ best_boss_battle);
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

    @Override
    public void onBackPressed() {

    }
}
