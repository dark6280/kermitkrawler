package com.example.a6;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class level3_speedrunmode extends AppCompatActivity {
    TextView fueltime;
    boolean moveright = false, moveleft = false, hitleft=false,hitright=false,jump=false,onground=true,maxheight = false,gotea = false,dead = false,gotfuel = true;
    int height=0,score ,teafuelcount = 30;
    TextView tvscore;
    ImageView kermit,wallright, wallleft,ground,tea;
    ImageView spikes1,spikes2,spikes3,spikes4,spikes5;
    ImageView platform1,lw_platform1,rw_platform1,dw_platform1;
    ImageView platform2,lw_platform2,rw_platform2,dw_platform2;
    ImageView platform3,lw_platform3,rw_platform3,dw_platform3;
    ImageView platform4,lw_platform4,rw_platform4,dw_platform4;
    ImageView platform5,lw_platform5,rw_platform5,dw_platform5;

    Button btnleft, btnright,btnjump;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private CountDownTimer mCountDownTimer;


    Timer countuptimer;
    TimerTask timertask;
    double time= 0.0;

    private long startTime = 0;
    int seconds,milliseconds,showseconds,showmilliseconds;
    String St_time;
    boolean isTimerRunning = false;





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
        setContentView(R.layout.level3);
        kermit = findViewById(R.id.IV_Kermit);
        btnleft = findViewById(R.id.btnleft);
        btnright = findViewById(R.id.btnright);
        btnjump = findViewById(R.id.btn_jump);
        wallleft = findViewById(R.id.ivwall);
        wallright = findViewById(R.id.ivwall2);
        ground = findViewById(R.id.ivground);
        tea= findViewById(R.id.tea);
        tvscore=  findViewById(R.id.tv_Score);
        fueltime = findViewById(R.id.fueltime);
        spikes1 = findViewById(R.id.spikes);
        spikes2 = findViewById(R.id.spikes2);
        spikes3 = findViewById(R.id.spikes3);
        spikes4 = findViewById(R.id.spikes4);
        spikes5 = findViewById(R.id.spikes5);

        platform1 = findViewById(R.id.iv_platform1);
        lw_platform1 = findViewById(R.id.lw_ivplatform1);
        rw_platform1 = findViewById(R.id.rw_ivplatform1);
        dw_platform1 = findViewById(R.id.dw_ivplatform1);

        platform2 = findViewById(R.id.iv_platform2);
        lw_platform2 = findViewById(R.id.lw_ivplatform2);
        rw_platform2 = findViewById(R.id.rw_ivplatform2);
        dw_platform2 = findViewById(R.id.dw_ivplatform2);

        platform3 = findViewById(R.id.iv_platform3);
        lw_platform3 = findViewById(R.id.lw_ivplatform3);
        rw_platform3 = findViewById(R.id.rw_ivplatform3);
        dw_platform3 = findViewById(R.id.dw_ivplatform3);

        platform4 = findViewById(R.id.iv_platform4);
        lw_platform4 = findViewById(R.id.lw_ivplatform4);
        rw_platform4 = findViewById(R.id.rw_ivplatform4);
        dw_platform4 = findViewById(R.id.dw_ivplatform4);

        platform5 = findViewById(R.id.iv_platform5);
        lw_platform5 = findViewById(R.id.lw_ivplatform5);
        rw_platform5 = findViewById(R.id.rw_ivplatform5);
        dw_platform5 = findViewById(R.id.dw_ivplatform5);


        tvscore.setVisibility(View.INVISIBLE);
        fueltime.setTextSize(15);
        fueltime.setText("start");

        AudioPlay.playAudio(this,R.raw.song1);
        AudioPlay.setlooping();



        //timer

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hitsomething();
                        Move();
                        ongroundthings();
                        runoutoffuel();
                        spikesandNextLevel();
                        if(dead){
                            dead = false;
                            cancel();
                            stopTimer();
                            Ayyohedead();
                        }
                        if(gotea){
                            gotea = false;
                            cancel();
                            stopTimer();
                            AyyoNextLevel();
                        }
                    }

                });
            }
        }, 0, 1);

        teafuel();


        btnright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!hitright){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setPressed(true);
                        moveright = true;
                        startTimer();
                    } if ((event.getAction() == MotionEvent.ACTION_UP) || (event.getActionMasked() == MotionEvent.ACTION_HOVER_EXIT)) {
                        v.setPressed(false);
                        moveright = false;
                    }
                }
                else {
                    hitright=false;
                }
                return true;
            }
        });

        btnleft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!hitleft) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setPressed(true);
                        moveleft = true;
                        startTimer();
                    } if ((event.getAction() == MotionEvent.ACTION_UP) || (event.getActionMasked() == MotionEvent.ACTION_HOVER_EXIT)){
                        v.setPressed(false);
                        moveleft = false;
                    }
                }
                else {

                    hitleft= false;
                }
                return true;
            }
        });

        btnjump.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && height<=0 &&height>-30){
                    v.setPressed(true);
                    jump = true;
                    startTimer();
                }
                v.setPressed(false);

                return true;
            }
        });
    }

    //gravity and on ground
    public void ongroundthings(){

        //checks if the player is on a ground
        //)|| isCollisionDetected(kermit,platform1)|| isCollisionDetected(kermit,platform2)|| isCollisionDetected(kermit,platform3)
        if(isCollisionDetected(kermit,ground)|| isCollisionDetected(kermit,platform1)|| isCollisionDetected(kermit,platform2)|| isCollisionDetected(kermit,platform3)|| isCollisionDetected(kermit,platform4)|| isCollisionDetected(kermit,platform5)){
            onground= true;
            height =0;
            maxheight = false;
        }

        if( (isCollisionDetected(kermit,platform1) && isCollisionDetected(kermit,lw_platform1)|| (isCollisionDetected(kermit,platform1) && isCollisionDetected(kermit,rw_platform1)))){
            kermit.setY((float) (kermit.getY() -1));
        }
        if( (isCollisionDetected(kermit,platform2) && isCollisionDetected(kermit,lw_platform2)|| (isCollisionDetected(kermit,platform2) && isCollisionDetected(kermit,rw_platform2)))){
            kermit.setY((float) (kermit.getY() -1));
        }
        if( (isCollisionDetected(kermit,platform3) && isCollisionDetected(kermit,lw_platform3)|| (isCollisionDetected(kermit,platform3) && isCollisionDetected(kermit,rw_platform3)))){
            kermit.setY((float) (kermit.getY() -1));
        }
        if( (isCollisionDetected(kermit,platform4) && isCollisionDetected(kermit,lw_platform4)|| (isCollisionDetected(kermit,platform4) && isCollisionDetected(kermit,rw_platform4)))){
            kermit.setY((float) (kermit.getY() -1));
        }
        if( (isCollisionDetected(kermit,platform5) && isCollisionDetected(kermit,lw_platform5)|| (isCollisionDetected(kermit,platform5) && isCollisionDetected(kermit,rw_platform5)))){
            kermit.setY((float) (kermit.getY() -1));
        }

        //checks if the player is not on a ground
        if(!isCollisionDetected(kermit,ground) && !isCollisionDetected(kermit,platform1)&& !isCollisionDetected(kermit,platform2)&& !isCollisionDetected(kermit,platform3)&& !isCollisionDetected(kermit,platform4)&& !isCollisionDetected(kermit,platform5))
            onground= false;

        //gravity
        if(!jump && !onground) {
            if (height > 125) {
                kermit.setY((float) (kermit.getY() + 0.1));
            } else if (height > 100) {
                kermit.setY((float) (kermit.getY() + 0.3));
            } else if (height > 90) {
                kermit.setY((float) (kermit.getY() + 0.6));
            } else if (height > 80) {
                kermit.setY((float) (kermit.getY() + 1.2));
            } else if (height > 70) {
                kermit.setY(kermit.getY() + 2);
            } else {
                kermit.setY(kermit.getY() + 3);
            }
            height--;
        }
    }

    public void hitsomething(){
        hitwalltoleft();
        hitwalltoright();
        hitwallup();
    }

    public void hitwalltoleft(){
        //write here every left wall looooooooool
        if(isCollisionDetected(kermit,wallleft)||isCollisionDetected(kermit,rw_platform1)|| isCollisionDetected(kermit,rw_platform2)|| isCollisionDetected(kermit,rw_platform3)|| isCollisionDetected(kermit,rw_platform4)|| isCollisionDetected(kermit,rw_platform5))
            hitleft= true;
        else
            hitleft= false;
    }

    public void hitwalltoright(){
        //write here every right wall looooooooool
        if(isCollisionDetected(kermit,wallright) || isCollisionDetected(kermit,lw_platform1)|| isCollisionDetected(kermit,lw_platform2)|| isCollisionDetected(kermit,lw_platform3)|| isCollisionDetected(kermit,lw_platform4)|| isCollisionDetected(kermit,lw_platform5) )
            hitright= true;
        else
            hitright= false;
    }

    public void hitwallup(){
        //write here every wall up looooooooool
        if(isCollisionDetected(kermit,dw_platform1) || isCollisionDetected(kermit,dw_platform2) || isCollisionDetected(kermit,dw_platform3)  || isCollisionDetected(kermit,dw_platform4) || isCollisionDetected(kermit,dw_platform5)){
            maxheight = true;
        }
    }

    //gameover
    public void spikesandNextLevel(){
        if(isCollisionDetected(kermit,spikes1) || isCollisionDetected(kermit,spikes2) || isCollisionDetected(kermit,spikes3) || isCollisionDetected(kermit,spikes4) || isCollisionDetected(kermit,spikes5)) {
            if(!dead) {
                dead = true;
                onground= true;
            }
        }
        if(isCollisionDetected(kermit,tea)){
            if(!gotea) {
                gotea = true;
            }
        }
    }

    public void Ayyohedead(){
        Intent i = new Intent(this, AfterSpeedrunLevel.class);
        i.putExtra("Time", "DEAD");
        i.putExtra("Level",3);
        startActivity(i);
    }

    public void AyyoNextLevel(){
        Intent i = new Intent(this, AfterSpeedrunLevel.class);
        i.putExtra("Time",St_time);
        i.putExtra("Level",3);
        startActivity(i);

    }

    private void teafuel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();

                while (isTimerRunning) {

                    long millis = System.currentTimeMillis() - startTime; // calculate the elapsed time in milliseconds

                    //fueltime.setTextSize(15);
                    // format the elapsed time into a string
                    String timeString = String.format("%02d.%03d",
                            TimeUnit.MILLISECONDS.toSeconds(millis),
                            millis % 1000);


                    // update the timer TextView on the UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fueltime.setText(timeString);
                            St_time = timeString;
                        }
                    });

                    try {
                        Thread.sleep(1); // sleep for 1 millisecond
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void startTimer() {
        if(!isTimerRunning){
            isTimerRunning = true;
            teafuel();
        }
    }


    private void stopTimer() {
        isTimerRunning = false;
    }

    //moving left and right and jump
    public void Move() {
        //basic movement left
        if (moveleft && !(hitleft)) {
            kermit.setX(kermit.getX() - 1);
            if(height != 0)
                kermit.setX((float) (kermit.getX() - 0.5));
        }
        //basic movement right
        if (moveright && !(hitright)) {
            kermit.setX(kermit.getX() + 1);
            if(height !=0)
                kermit.setX((float) (kermit.getX() +0.5));
        }

        //jumps until height limit && onground
        if(jump  && !maxheight ){
            if(height>145){
                kermit.setY((float) (kermit.getY() -0.1));

            }
            else if(height>135){
                kermit.setY((float) (kermit.getY() -0.2));
            }

            else if(height>125) {
                kermit.setY((float) (kermit.getY() - 0.4));
            }

            else if(height>115) {
                kermit.setY((float) (kermit.getY() - 0.8));
            }
            else if(height>105){
                kermit.setY(kermit.getY()-1);
            }
            else{
                kermit.setY(kermit.getY()-2);
            }
            height++;
            onground= false;
        }
        //height limit
        if(height == 150)
            maxheight = true;

        //falls until hits ground
        if(maxheight&& !onground){
            jump=false;
        }
    }



    public void runoutoffuel(){
        if(!gotfuel){
            dead= true;
            gotfuel= true;
        }
    }



    //can detect collision between two images
    public static boolean isCollisionDetected(View v1, View v2) {
        Rect R1 = new Rect();
        v1.getHitRect(R1);
        Rect R2 = new Rect();
        v2.getHitRect(R2);
        return Rect.intersects(R1, R2);
    }

    @Override
    protected void onDestroy() {
        AudioPlay.stopAudio();
        timer.cancel();
        stopTimer();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }
}
