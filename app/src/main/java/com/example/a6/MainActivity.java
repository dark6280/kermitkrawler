package com.example.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    TextView fueltime;
    boolean moveright = false, moveleft = false, hitleft=false,hitright=false,jump=false,onground=true,maxheight = false,gotea = false,dead = false,gotfuel = true;
    int height=0,score =0,teafuelcount = 30;
    TextView tvscore;
    ImageView kermit,wallright, wallleft,ground,tea;
    ImageView spikes1,spikes2,spikes3,spikes4,spikes5;
    ImageView platform1,lw_platform1,rw_platform1,dw_platform1;
    ImageView platform2,lw_platform2,rw_platform2,dw_platform2;
    ImageView platform3,lw_platform3,rw_platform3,dw_platform3;
    Button btnleft, btnright,btnjump;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private CountDownTimer mCountDownTimer;








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
        setContentView(R.layout.activity_main);
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

        platform1 = findViewById(R.id.ivground2);
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
                                Ayyohedead();
                            }
                            if(gotea){
                                gotea = false;
                                cancel();
                                AyyoNextLevel();
                            }
                        }

                    });
                }
            }, 0, 6);

       teafuel();
       AudioPlay.playAudio(this,R.raw.song1);
       AudioPlay.setlooping();




       
        btnright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!hitright){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setPressed(true);
                        moveright = true;
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
                if (event.getAction() == MotionEvent.ACTION_DOWN && height<=0 &&height>-3){
                    v.setPressed(true);
                    jump = true;
                }
                v.setPressed(false);

                return true;
            }
        });

    }




/*
    protected void onUserLeaveHint()
    {
        Log.d("onUserLeaveHint","Home button pressed");
        AudioPlay.stopAudio();
        super.onUserLeaveHint();
    }

 */

    //gravity and on ground
    public void ongroundthings(){

        //checks if the player is on a ground
        //)|| isCollisionDetected(kermit,platform1)|| isCollisionDetected(kermit,platform2)|| isCollisionDetected(kermit,platform3)
        if(isCollisionDetected(kermit,ground)|| isCollisionDetected(kermit,platform1)|| isCollisionDetected(kermit,platform2)|| isCollisionDetected(kermit,platform3)){
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

        //checks if the player is not on a ground
        if(!isCollisionDetected(kermit,ground) && !isCollisionDetected(kermit,platform1)&& !isCollisionDetected(kermit,platform2)&& !isCollisionDetected(kermit,platform3))
            onground= false;

        //gravity
        if(!jump && !onground)
        {
            if(height>24){
                kermit.setY((float) (kermit.getY() +2));

            }
            else if(height>23){
                kermit.setY((float) (kermit.getY() +3));
            }

            else if(height>22) {
                kermit.setY((float) (kermit.getY() +4));
            }

            else if(height>20) {
                kermit.setY((float) (kermit.getY() +5));
            }
            else if(height>15){
                kermit.setY(kermit.getY()+10);
            }
            else{
                kermit.setY(kermit.getY()+13);
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
        if(isCollisionDetected(kermit,wallleft)||isCollisionDetected(kermit,rw_platform1)|| isCollisionDetected(kermit,rw_platform2)|| isCollisionDetected(kermit,rw_platform3)){
            hitleft= true;
        }

        else
            hitleft= false;
    }

    public void hitwalltoright(){
        //write here every right wall looooooooool
        if(isCollisionDetected(kermit,wallright) || isCollisionDetected(kermit,lw_platform1)|| isCollisionDetected(kermit,lw_platform2)|| isCollisionDetected(kermit,lw_platform3) )
            hitright= true;
        else{
            hitright= false;
        }

    }

    public void hitwallup(){
          //write here every wall up looooooooool
        if(isCollisionDetected(kermit,dw_platform1) || isCollisionDetected(kermit,dw_platform2) || isCollisionDetected(kermit,dw_platform3)  ){
            maxheight = true;
        }
    }

    //gameover
    public void spikesandNextLevel(){

        if(isCollisionDetected(kermit,spikes1) || isCollisionDetected(kermit,spikes2) || isCollisionDetected(kermit,spikes3)|| isCollisionDetected(kermit,spikes4)|| isCollisionDetected(kermit,spikes5)) {
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
        timer.cancel();
        AudioPlay.stopAudio();
        Intent i = new Intent(this, gameover.class);
        i.putExtra("score", score);
        startActivity(i);
        //onRestart();
    }

    public void AyyoNextLevel(){
        timer.cancel();
        final int random = new Random().nextInt(3);

        if(random == 0){
            Intent i = new Intent(this, level2_act.class);
            i.putExtra("score", score);
            startActivity(i);
            //onRestart();
        }
        if(random ==1){
            Intent i = new Intent(this, level3_act.class);
            i.putExtra("score", score);
            startActivity(i);
            //onRestart();
        }
        if(random ==2){
            Intent i = new Intent(this, level4_act.class);
            i.putExtra("score", score);
            startActivity(i);
            //onRestart();
        }



    }

    //moving left and right and jump
    public void Move() {
        //basic movement left
        if (moveleft && !(hitleft)) {
            kermit.setX(kermit.getX() - 4);
            if(height != 0)
                kermit.setX((float) (kermit.getX() - 3));
        }
        //basic movement right
        if (moveright && !(hitright)) {
            kermit.setX(kermit.getX() + 4);
            if(height !=0)
                kermit.setX((float) (kermit.getX() +3));
        }

        //jumps until height limit && onground
        if(jump  && !maxheight ){
            if(height>24){
                kermit.setY((float) (kermit.getY() -2));

            }
            else if(height>23){
                kermit.setY((float) (kermit.getY() -3));
            }

            else if(height>22) {
                kermit.setY((float) (kermit.getY() - 4));
            }

            else if(height>20) {
                kermit.setY((float) (kermit.getY() - 5));
            }
            else if(height>15){
                kermit.setY(kermit.getY()-10);
            }
            else{
                kermit.setY(kermit.getY()-13);
            }
            height++;
            onground= false;
        }
        //height limit
        if(height == 35)
            maxheight = true;

        //falls until hits ground
        if(maxheight){
            jump=false;
        }
    }


    public void runoutoffuel(){
        if(!gotfuel){
            dead= true;
            gotfuel= true;
        }
    }

    private void teafuel() {
        mCountDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(teafuelcount == 5){
                    fueltime.setTextColor(Color.parseColor("#FF0000"));
                }
                fueltime.setText("" + (teafuelcount));
                teafuelcount  = teafuelcount -1;
            }
            @Override
            public void onFinish() {
                gotfuel = false;
            }
        }.start();
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
        mCountDownTimer.cancel();
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

}
