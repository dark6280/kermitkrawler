package com.example.a6;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class elmo_boss_fight extends AppCompatActivity {
    TextView fueltime,youdied;
    boolean moveright = false, moveleft = false, hitleft=false,hitright=false,jump=false,onground=true,maxheight = false,gotea = false,dead = false;
    boolean shooting_timing = true, falling_fireball_timing= true,fireball_timing= true,fireplatform_timing= true,Shooting_timing_fromback = false,bossisdown= false;
    int height=0,score,fallingfireball_time = 1000, count=0;;
    TextView tvscore;
    int hearts =5 ,piggyhearts =5;
    float piggystartX, piggystartY;
    ImageView kermit,misspiggy,wallright, wallleft,ground,tea;
    ImageView heart1,heart2,heart3,heart4,heart5;
    ImageView piggyheart1,piggyheart2,piggyheart3, piggyheart4,piggyheart5,piggyheart6, piggyheart7,piggyheart8,piggyheart9,piggyheart10;
    ImageView platform1,lw_platform1,rw_platform1,dw_platform1;
    ImageView platform2,lw_platform2,rw_platform2,dw_platform2;
    ImageView platform3,lw_platform3,rw_platform3,dw_platform3;

    boolean faze1=true,faze2 =false,faze3=false;


    ImageView danger1,danger2,danger3,incoming_right,incoming_left;
    ImageView fire_platform1,fire_platform2,fire_platform3;
    boolean fire_platform1_hurts= false,fire_platform2_hurts= false,fire_platform3_hurts= false;
    boolean fire1last=false,fire2last=false,fire3last=false;

    ImageView aroundfueltime;
    float piggymovementspeed= 0.5F;

    ObjectAnimator rotateAnimation;

    private boolean hasCollided = false;
    private boolean isKermitInvincible =false;

    boolean piggymoveleft = true, piggymoveright = false,untouchable = false;


    Button btnleft, btnright,btnjump;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private CountDownTimer mCountDownTimer;






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
        setContentView(R.layout.elmo_boss_fight);
        kermit = findViewById(R.id.IV_Kermit);
        btnleft = findViewById(R.id.btnleft);
        btnright = findViewById(R.id.btnright);
        btnjump = findViewById(R.id.btn_jump);
        wallleft = findViewById(R.id.ivwall);
        wallright = findViewById(R.id.ivwall2);
        ground = findViewById(R.id.ivground);
        misspiggy = findViewById(R.id.miss_piggy);
        aroundfueltime = findViewById(R.id.aroundfueltime);
        youdied = findViewById(R.id.you_died);
        fueltime = findViewById(R.id.fueltime);

        fire_platform1 =findViewById(R.id.fire_platform1);
        fire_platform2 =findViewById(R.id.fire_platform2);
        fire_platform3 =findViewById(R.id.fire_platform3);

        danger1 = findViewById(R.id.danger_1);
        danger2 = findViewById(R.id.danger_2);
        danger3 = findViewById(R.id.danger_3);
        incoming_left= findViewById(R.id.incoming_left);
        incoming_right= findViewById(R.id.incoming_right);



        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        heart4 = findViewById(R.id.heart4);
        heart5 = findViewById(R.id.heart5);

        piggyheart1= findViewById(R.id.piggyheart1);
        piggyheart2= findViewById(R.id.piggyheart2);
        piggyheart3= findViewById(R.id.piggyheart3);
        piggyheart4= findViewById(R.id.piggyheart4);
        piggyheart5= findViewById(R.id.piggyheart5);
        piggyheart6= findViewById(R.id.piggyheart6);
        piggyheart7= findViewById(R.id.piggyheart7);
        piggyheart8= findViewById(R.id.piggyheart8);
        piggyheart9= findViewById(R.id.piggyheart9);
        piggyheart10= findViewById(R.id.piggyheart10);



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


        falling_fireball_timing_f1();
        falling_fireball_timing_f2();
        falling_fireball_timing_f3();
        fireplatform_timing();
        fireball_timing();
        shooting_timing();
        shooting_timing_bossisdown();
        teleport();
        gifbackground();


        piggyheart10.setVisibility(View.INVISIBLE);
        piggyheart9.setVisibility(View.INVISIBLE);
        piggyheart8.setVisibility(View.INVISIBLE);
        piggyheart7.setVisibility(View.INVISIBLE);
        piggyheart6.setVisibility(View.INVISIBLE);

        misspiggy.animate().scaleX(-1).start();



        fueltime.setVisibility(View.INVISIBLE);
        aroundfueltime.setVisibility(View.INVISIBLE);

        piggystartY = misspiggy.getY();
        piggystartX = misspiggy.getX();

        AudioPlay.playAudio(this,R.raw.elmofightmusic);
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
                        //misspiggymovement();
                        kermitgothurt();
                        if(dead){
                            cancel();
                            Ayyohedead();
                        }
                        if(gotea){
                            cancel();
                            AyyoNextLevel();
                        }
                    }

                });
            }
        }, 0, 1);


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
                if (event.getAction() == MotionEvent.ACTION_DOWN && height<=0 &&height>-30){
                    v.setPressed(true);
                    jump = true;
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
        if(isCollisionDetected(kermit,ground)|| isCollisionDetected(kermit,platform1)|| isCollisionDetected(kermit,platform2)|| isCollisionDetected(kermit,platform3)){
            onground= true;
            height =0;
            maxheight = false;
        }


        //checks if the player is not on a ground
        if(!isCollisionDetected(kermit,ground) && !isCollisionDetected(kermit,platform1) && !isCollisionDetected(kermit,platform3)&& !isCollisionDetected(kermit,platform2))
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
        hitleftpiggy();
        hitrightpiggy();
    }

    public void hitwalltoleft(){
        //write here every left wall looooooooool
        if(isCollisionDetected(kermit,wallleft)){
            hitleft= true;
        }
        else
            hitleft= false;
    }

    public void hitwalltoright(){
        //write here every right wall looooooooool
        if(isCollisionDetected(kermit,wallright)){
            hitright= true;
        }
        else
            hitright= false;
    }

    public void hitwallup(){
        //write here every wall up looooooooool
        if(isCollisionDetected(kermit,dw_platform1)  || isCollisionDetected(kermit,dw_platform3)  || isCollisionDetected(kermit,dw_platform2) ){
            maxheight = true;
        }
    }

    public void Ayyohedead(){

        AudioPlay.stopAudio();
        Intent i = new Intent(this, AfterSpeedrunLevel.class);
        i.putExtra("Time", "DEAD");
        i.putExtra("Level",6);
        startActivity(i);

    }

    public void AyyoNextLevel(){
        AudioPlay.stopAudio();
        Intent i = new Intent(this, AfterSpeedrunLevel.class);
        i.putExtra("Time", "ELMODEAD");
        i.putExtra("Level",6);
        startActivity(i);

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


    //can detect collision between two images
    public static boolean isCollisionDetected(View v1, View v2) {
        Rect R1 = new Rect();
        v1.getHitRect(R1);
        Rect R2 = new Rect();
        v2.getHitRect(R2);
        return Rect.intersects(R1, R2);
    }

    public static boolean isCollisionDetected2(Rect r1, View v2) {
        Rect r2 = new Rect();
        v2.getHitRect(r2);
        return Rect.intersects(r1, r2);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }


    public void misspiggymovement(){

        if(piggymoveleft){
            misspiggy.setX((float) (misspiggy.getX() -piggymovementspeed));
        }
        if(piggymoveright){
            misspiggy.setX((float) (misspiggy.getX() +piggymovementspeed));
        }
    }

    public void hitleftpiggy(){
        if(isCollisionDetected(misspiggy,wallleft)){
            misspiggy.animate().scaleX(-1).start();
            piggymoveleft = false;
            piggymoveright = true;
        }
    }

    public void hitrightpiggy(){
        if(isCollisionDetected(misspiggy,wallright)){
            misspiggy.animate().scaleX(1).start();
            piggymoveleft = true;
            piggymoveright = false;
        }
    }

    public void knockback(int knockback){

        float centerX1 = kermit.getX() + kermit.getWidth() / 2;
        float centerX2 = misspiggy.getX() + misspiggy.getWidth() / 2;

        if (centerX1 < centerX2) {
            // imageView1 is on the left of imageView2
            kermit.animate().translationXBy(knockback);
        } else {
            // imageView1 is on the right of imageView2
            kermit.animate().translationXBy(-knockback);
        }
    }

    public void hurtanimation(){
        AlphaAnimation anim = new AlphaAnimation(0.4f, 1.0f);
        anim.setDuration(3500); // Set the duration of the animation
        kermit.startAnimation(anim);
    }

    public void bosshurt(){
        Drawable originalDrawable;
        originalDrawable = misspiggy.getDrawable();
        int color = Color.parseColor("#cf4040");
        ColorFilter Filter = new LightingColorFilter(color, 1);
        misspiggy.setColorFilter(Filter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                misspiggy.setColorFilter(null);
                misspiggy.setImageDrawable(originalDrawable);
            }
        }, 1200);
    }

    public void kermitgothurt(){
        if(!untouchable){
            if(hearts ==1 && isCollisionDetected(kermit,misspiggy)){
                kermit.setVisibility(View.INVISIBLE);
                kermit.setY(1000);
                kermit.setX(-100);
                hearts--;
                displayHearts();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dead = true;
                    }
                }, 7000);
            }
            else if(kermithurtchecks() && hearts >=2){
                Untouchable();
                if(hearts>0) {
                    hearts--;
                }
                displayHearts();
                hurtanimation();
            }
        }
        if(Hitinthehead(kermit,misspiggy)){
            onground= true;
            height =0;
            maxheight = false;
            jump = true;
            Untouchable();
            bosshurt();
            if(piggyhearts>0)
                piggyhearts--;

            displaybosshearts();
        }

    }

    public void Untouchable(){
        untouchable = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                untouchable = false;
            }
        }, 3500);
    }

    public void displayHearts(){
        if(hearts ==5){
            heart5.setImageResource(R.mipmap.heartfull);
            heart4.setImageResource(R.mipmap.heartfull);
            heart3.setImageResource(R.mipmap.heartfull);
            heart2.setImageResource(R.mipmap.heartfull);
            heart1.setImageResource(R.mipmap.heartfull);
        }
        if(hearts ==4){
            heart5.setImageResource(R.mipmap.heartlost);
            heart4.setImageResource(R.mipmap.heartfull);
            heart3.setImageResource(R.mipmap.heartfull);
            heart2.setImageResource(R.mipmap.heartfull);
            heart1.setImageResource(R.mipmap.heartfull);
            shakescreen();
        }
        if(hearts ==3){
            heart5.setImageResource(R.mipmap.heartlost);
            heart4.setImageResource(R.mipmap.heartlost);
            heart3.setImageResource(R.mipmap.heartfull);
            heart2.setImageResource(R.mipmap.heartfull);
            heart1.setImageResource(R.mipmap.heartfull);
            shakescreen();
        }
        if(hearts ==2){
            heart5.setImageResource(R.mipmap.heartlost);
            heart4.setImageResource(R.mipmap.heartlost);
            heart3.setImageResource(R.mipmap.heartlost);
            heart2.setImageResource(R.mipmap.heartfull);
            heart1.setImageResource(R.mipmap.heartfull);
            shakescreen();
        }
        if(hearts ==1){
            heart5.setImageResource(R.mipmap.heartlost);
            heart4.setImageResource(R.mipmap.heartlost);
            heart3.setImageResource(R.mipmap.heartlost);
            heart2.setImageResource(R.mipmap.heartlost);
            heart1.setImageResource(R.mipmap.heartfull);
            shakescreen();
        }
        if(hearts ==0){
            heart5.setImageResource(R.mipmap.heartlost);
            heart4.setImageResource(R.mipmap.heartlost);
            heart3.setImageResource(R.mipmap.heartlost);
            heart2.setImageResource(R.mipmap.heartlost);
            heart1.setImageResource(R.mipmap.heartlost);
            shakescreen();
            youdied();
        }
    }

    public void displaybosshearts(){
        if(piggyhearts ==10){
            piggyheart10.setImageResource(R.mipmap.piggyheartfull);
            piggyheart9.setImageResource(R.mipmap.piggyheartfull);
            piggyheart8.setImageResource(R.mipmap.piggyheartfull);
            piggyheart7.setImageResource(R.mipmap.piggyheartfull);
            piggyheart6.setImageResource(R.mipmap.piggyheartfull);
            piggyheart5.setImageResource(R.mipmap.piggyheartfull);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
        }
        if(piggyhearts ==9){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartfull);
            piggyheart8.setImageResource(R.mipmap.piggyheartfull);
            piggyheart7.setImageResource(R.mipmap.piggyheartfull);
            piggyheart6.setImageResource(R.mipmap.piggyheartfull);
            piggyheart5.setImageResource(R.mipmap.piggyheartfull);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            misspiggy.setX(platform3.getX()+630);
            misspiggy.animate().scaleX(1).start();
            shooting_timing =false;
            Shooting_timing_fromback =true;
        }
        if(piggyhearts ==8){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartfull);
            piggyheart7.setImageResource(R.mipmap.piggyheartfull);
            piggyheart6.setImageResource(R.mipmap.piggyheartfull);
            piggyheart5.setImageResource(R.mipmap.piggyheartfull);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            teleport();
            misspiggy.animate().scaleX(-1).start();
            shooting_timing =true;
            Shooting_timing_fromback =false;
        }
        if(piggyhearts ==7){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartfull);
            piggyheart6.setImageResource(R.mipmap.piggyheartfull);
            piggyheart5.setImageResource(R.mipmap.piggyheartfull);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            misspiggy.setX(platform3.getX()+630);
            misspiggy.animate().scaleX(1).start();
            shooting_timing =false;
            Shooting_timing_fromback =true;
        }
        if(piggyhearts ==6){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartfull);
            piggyheart5.setImageResource(R.mipmap.piggyheartfull);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            teleport();
            misspiggy.animate().scaleX(-1).start();
            shooting_timing =true;
            Shooting_timing_fromback =false;
        }
        if(piggyhearts ==5){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartlost);
            piggyheart5.setImageResource(R.mipmap.piggyheartfull);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            misspiggy.setX(platform3.getX()+630);
            misspiggy.animate().scaleX(1).start();
            shooting_timing =false;
            Shooting_timing_fromback =true;
        }
        if(piggyhearts ==4){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartlost);
            piggyheart5.setImageResource(R.mipmap.piggyheartlost);
            piggyheart4.setImageResource(R.mipmap.piggyheartfull);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            teleport();
            misspiggy.animate().scaleX(-1).start();
            shooting_timing =true;
            Shooting_timing_fromback =false;
            faze1 =false;
            faze2=true;
            faze3 =false;
        }
        if(piggyhearts ==3){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartlost);
            piggyheart5.setImageResource(R.mipmap.piggyheartlost);
            piggyheart4.setImageResource(R.mipmap.piggyheartlost);
            piggyheart3.setImageResource(R.mipmap.piggyheartfull);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            misspiggy.setX(platform3.getX()+630);
            misspiggy.animate().scaleX(1).start();
            shooting_timing =false;
            Shooting_timing_fromback =true;
        }
        if(piggyhearts ==2){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartlost);
            piggyheart5.setImageResource(R.mipmap.piggyheartlost);
            piggyheart4.setImageResource(R.mipmap.piggyheartlost);
            piggyheart3.setImageResource(R.mipmap.piggyheartlost);
            piggyheart2.setImageResource(R.mipmap.piggyheartfull);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            teleport();
            misspiggy.animate().scaleX(-1).start();
            shooting_timing =true;
            Shooting_timing_fromback =false;
            faze1 =false;
            faze2=false;
            faze3 =true;
        }
        if(piggyhearts ==1){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartlost);
            piggyheart5.setImageResource(R.mipmap.piggyheartlost);
            piggyheart4.setImageResource(R.mipmap.piggyheartlost);
            piggyheart3.setImageResource(R.mipmap.piggyheartlost);
            piggyheart2.setImageResource(R.mipmap.piggyheartlost);
            piggyheart1.setImageResource(R.mipmap.piggyheartfull);
            misspiggy.setX(platform3.getX()+630);
            misspiggy.animate().scaleX(1).start();
            shooting_timing =false;
            Shooting_timing_fromback =true;
        }
        if(piggyhearts ==0){
            piggyheart10.setImageResource(R.mipmap.piggyheartlost);
            piggyheart9.setImageResource(R.mipmap.piggyheartlost);
            piggyheart8.setImageResource(R.mipmap.piggyheartlost);
            piggyheart7.setImageResource(R.mipmap.piggyheartlost);
            piggyheart6.setImageResource(R.mipmap.piggyheartlost);
            piggyheart5.setImageResource(R.mipmap.piggyheartlost);
            piggyheart4.setImageResource(R.mipmap.piggyheartlost);
            piggyheart3.setImageResource(R.mipmap.piggyheartlost);
            piggyheart2.setImageResource(R.mipmap.piggyheartlost);
            piggyheart1.setImageResource(R.mipmap.piggyheartlost);
            elmoexplode2();
            shooting_timing = false;
            bossisdown =false;
            AudioPlay.stopAudio();
            AudioPlay.playAudio(this,R.raw.elmodeath);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotea=true;
                }
            }, 10000);



        }
    }

    public void elmospin(){
        rotateAnimation = ObjectAnimator.ofFloat(misspiggy, "rotation", 0f, 360f);
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.start();
    }


    public void elmoshoot() {
        // create a new ImageView to drop
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.bullet);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));


        if(bossisdown){
            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        }

        // add the ImageView to the parent view
        ViewGroup parentView = findViewById(R.id.elmo_boss_fight);
        parentView.addView(imageView);

        // wait for the parent view to finish laying out its children before setting the position of the ImageView
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the initial position of the ImageView to be in front of ImageView2
                float x = misspiggy.getX() + (misspiggy.getWidth() / 2) - (imageView.getWidth() / 2);
                float y = misspiggy.getY() + misspiggy.getHeight() - imageView.getHeight() / 2 -90;
                imageView.setX(x);
                imageView.setY(y);


                imageView.setRotation(misspiggy.getRotation());

                float dx = (float) Math.cos(Math.toRadians(-misspiggy.getRotation() + 180));
                float dy = (float) Math.sin(Math.toRadians(-misspiggy.getRotation() +0.4));
                ObjectAnimator moveAnimationX = ObjectAnimator.ofFloat(imageView, "translationX", dx * -parentView.getWidth());
                ObjectAnimator moveAnimationY = ObjectAnimator.ofFloat(imageView, "translationY", dy * -parentView.getWidth()+750);
                AnimatorSet moveAnimation = new AnimatorSet();
                moveAnimation.playTogether(moveAnimationX, moveAnimationY);
                moveAnimation.setDuration(2000); // adjust the duration as needed
                moveAnimation.setInterpolator(new AccelerateInterpolator());

                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Rect R1 = new Rect();
                        imageView.getHitRect(R1);
                        R1.offsetTo((int) imageView.getX(), (int) imageView.getY());
                        if (isCollisionDetected2(R1, kermit) && !untouchable) {
                            untouchable = true;
                            hurtanimation();
                            hearts--;
                            displayHearts();

                            if (hearts == 0) {
                                kermit.setVisibility(View.INVISIBLE);
                                kermit.setY(1000);
                                kermit.setX(-100);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dead = true;
                                    }
                                }, 7000);
                            }

                            // Delay resetting isKermitInvincible back to false for 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    untouchable = false;
                                }
                            }, 3500);
                        }
                    }

                };
                moveAnimationX.addUpdateListener(updateListener);

                // remove the ImageView from the parent view after the animation finishes
                moveAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        parentView.removeView(imageView);
                    }
                });

                moveAnimation.start();

                // remove the listener to avoid redundant calls
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void elmoshootFromBack() {
        // create a new ImageView to drop
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.bullet);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));

        if(bossisdown){
            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        }

        // add the ImageView to the parent view
        ViewGroup parentView = findViewById(R.id.elmo_boss_fight);
        parentView.addView(imageView);

        // wait for the parent view to finish laying out its children before setting the position of the ImageView
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the initial position of the ImageView to be behind ImageView2
                float x = misspiggy.getX() + (misspiggy.getWidth() / 2) - (imageView.getWidth() / 2);
                float y = misspiggy.getY() - imageView.getHeight() / 2 +60;
                imageView.setX(x);
                imageView.setY(y);

// set the rotation of the ImageView to be the same as ImageView2's rotation plus 180 degrees
                imageView.setRotation(misspiggy.getRotation() + 180);

// calculate the direction of the bullet based on the new rotation
                float dx = (float) Math.sin(Math.toRadians(misspiggy.getRotation() - 90));
                float dy = (float) -Math.cos(Math.toRadians(misspiggy.getRotation() - 90 + 0.4));

// adjust the position of the bullet to make it appear behind ImageView2
                imageView.setTranslationX(imageView.getTranslationX() + dx * 50);
                imageView.setTranslationY(imageView.getTranslationY() + dy * 50);

// set the animation to move the bullet in the new direction
                ObjectAnimator moveAnimationX = ObjectAnimator.ofFloat(imageView, "translationX", dx * parentView.getWidth());
                ObjectAnimator moveAnimationY = ObjectAnimator.ofFloat(imageView, "translationY", dy * parentView.getWidth() + 750);
                AnimatorSet moveAnimation = new AnimatorSet();
                moveAnimation.playTogether(moveAnimationX, moveAnimationY);
                moveAnimation.setDuration(2000); // adjust the duration as needed
                moveAnimation.setInterpolator(new AccelerateInterpolator());

                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Rect R1 = new Rect();
                        imageView.getHitRect(R1);
                        R1.offsetTo((int) imageView.getX(), (int) imageView.getY());
                        if (isCollisionDetected2(R1, kermit) && !untouchable) {
                            untouchable = true;
                            hurtanimation();
                            hearts--;
                            displayHearts();

                            if (hearts == 0) {
                                kermit.setVisibility(View.INVISIBLE);
                                kermit.setY(1000);
                                kermit.setX(-100);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dead = true;
                                    }
                                }, 7000);
                            }

                            // Delay resetting isKermitInvincible back to false for 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    untouchable = false;
                                }
                            }, 3500);
                        }
                    }
                };
                moveAnimationX.addUpdateListener(updateListener);

                // remove the ImageView from the parent view after the animation finishes
                moveAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        parentView.removeView(imageView);
                    }
                });

                moveAnimation.start();

                // remove the listener to avoid redundant calls
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }



    private void dropImageView() {
        // create a new ImageView to drop
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.divorce_papers);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));

        // add the ImageView to the parent view
        ViewGroup parentView = findViewById(R.id.boss_fight);
        parentView.addView(imageView);

        // wait for the parent view to finish laying out its children before setting the position of the ImageView
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the initial position of the ImageView to be the same as ImageView2
                imageView.setX(misspiggy.getX());
                imageView.setY(misspiggy.getY());

                // animate the ImageView to drop down the screen
                ObjectAnimator dropAnimation = ObjectAnimator.ofFloat(imageView, "translationY", parentView.getHeight());
                dropAnimation.setDuration(2000); // adjust the duration as needed
                dropAnimation.setInterpolator(new AccelerateInterpolator()); // add an acceleration effect to the animation

                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Rect R1 = new Rect();
                        imageView.getHitRect(R1);
                        R1.offsetTo((int)imageView.getX(), (int)imageView.getY());
                        if (isCollisionDetected2(R1, kermit) && !untouchable) {
                            untouchable = true;
                            hurtanimation();
                            hearts--;
                            displayHearts();

                            if(hearts ==0){
                                kermit.setVisibility(View.INVISIBLE);
                                kermit.setY(1000);
                                kermit.setX(-100);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dead = true;
                                    }
                                }, 7000);
                            }

                            // Delay resetting isKermitInvincible back to false for 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    untouchable = false;
                                }
                            }, 3500);
                        }
                    }

                };
                dropAnimation.addUpdateListener(updateListener);

                dropAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        parentView.removeView(imageView);
                    }
                });

                dropAnimation.start();

                // remove the listener to avoid redundant calls
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    public void teleport(){
        // get the parent view that contains the image view
        ViewGroup parentView = findViewById(R.id.elmo_boss_fight);

        elmospin();
        startShootingImageViewsfromback();

        falling_fireball_timing = true;
        fireplatform_timing = true;
        fireball_timing = true;
        bossisdown =false;

        // set the new position of ImageView2 to the center of the screen
        int parentWidth = parentView.getWidth();
        int parentHeight = parentView.getHeight();
        int imageWidth = misspiggy.getWidth();
        int imageHeight = misspiggy.getHeight();
        int newX = (parentWidth - imageWidth) / 2;
        int newY = (parentHeight - imageHeight) / 2;

        misspiggy.setX(newX);
        misspiggy.setY(newY-400);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int startX = 0;
                final int random = new Random().nextInt(3);
                if (random == 1) {
                    startX = 0;
                } else {
                    startX = (parentWidth - imageWidth);
                }

                rotateAnimation.cancel();
                int startY = parentHeight - 55;
                piggymovementspeed = 0.5F;
                handlershoot.removeCallbacks(shootRunnable);
                misspiggy.setX(0);
                misspiggy.setY(platform1.getY() - 155);
                falling_fireball_timing = false;
                fireplatform_timing = false;
                fireball_timing = false;
                bossisdown = true;
            }
        }, 10000);
    }

    public void elmoexplode() {
        // Create an ImageView named misspiggy

        // Add misspiggy to the layout
        ViewGroup layout = findViewById(R.id.elmo_boss_fight);

        // Get the dimensions of the screen
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // Set the starting position of misspiggy in the middle of the screen
        misspiggy.setX(screenWidth / 2 - misspiggy.getWidth() / 2);
        misspiggy.setY(screenHeight / 2 - misspiggy.getHeight() / 2);

        // Create a rotate animation for misspiggy
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000); // Set the duration of the animation to 1 second
        rotate.setInterpolator(new LinearInterpolator()); // Set the interpolation to linear

        // Create a scale animation for misspiggy
        ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(3000); // Set the duration of the animation to 1 second
        scale.setInterpolator(new LinearInterpolator()); // Set the interpolation to linear

        // Create an animation set for misspiggy
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(scale);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.removeView(misspiggy); // Remove misspiggy from the layout when the animation ends
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Start the animation for misspiggy
        misspiggy.startAnimation(animationSet);
    }

    public void elmoexplode2() {
        // Create an ImageView named spark
        ImageView spark = new ImageView(this);
        AnimationDrawable animation2 = (AnimationDrawable) getResources().getDrawable(R.drawable.spark);
        spark.setBackground(animation2);
        spark.setVisibility(View.INVISIBLE); // Set spark to be initially invisible

        spark.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        // spark to the layout
        ViewGroup layout = findViewById(R.id.elmo_boss_fight);
        layout.addView(spark);



        // Get the dimensions of the screen
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // Set the starting position of misspiggy in the middle of the screen
        misspiggy.setX(screenWidth / 2 - misspiggy.getWidth() / 2);
        misspiggy.setY(screenHeight / 2 - misspiggy.getHeight() / 2);

        // Create a rotate animation for misspiggy
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000); // Set the duration of the animation to 1 second
        rotate.setInterpolator(new LinearInterpolator()); // Set the interpolation to linear

        // Create a scale animation for misspiggy
        ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(3000); // Set the duration of the animation to 1 second
        scale.setInterpolator(new LinearInterpolator()); // Set the interpolation to linear

        // Create an animation set for misspiggy
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(scale);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                misspiggy.setVisibility(View.INVISIBLE); // Hide misspiggy when the animation ends

                // Update the position of the spark to match the final position of Miss Piggy
                int finalX = (int) misspiggy.getX() + misspiggy.getWidth() / 2 - spark.getWidth() / 2+55;
                int finalY = (int) misspiggy.getY() + misspiggy.getHeight() / 2 - spark.getHeight() / 2 +250;
                spark.setX(finalX);
                spark.setY(finalY);

                animation2.start();

                spark.setVisibility(View.VISIBLE); // Show the spark
                spark.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.removeView(spark); // Remove spark from the layout after 1 second
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Start the animation for misspiggy
        misspiggy.startAnimation(animationSet);
    }





    private Handler handlershoot = new Handler();
    private Runnable shootRunnable = new Runnable() {
        @Override
        public void run() {
            if(shooting_timing && !bossisdown){
                elmoshoot();
                final int random = new Random().nextInt(701) + 100;
                handlershoot.postDelayed(shootRunnable, random);
            }
        }
    };

    private void startShootingImageViewsfromback() {
        handlershoot.postDelayed(shootRunnable, 200);
    }

    private void falling_fireball() {
        // create a new ImageView to drop
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.falling_fireball);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));

        // add the ImageView to the parent view
        ViewGroup parentView = findViewById(R.id.elmo_boss_fight);
        parentView.addView(imageView);

        // wait for the parent view to finish laying out its children before setting the position of the ImageView
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the initial position of the ImageView to be the same as ImageView2
                int maxX = parentView.getWidth() - imageView.getWidth();
                int randomX = new Random().nextInt(maxX);
                imageView.setX(randomX);
                imageView.setY(-200);

                // animate the ImageView to drop down the screen
                ObjectAnimator dropAnimation = ObjectAnimator.ofFloat(imageView, "translationY", parentView.getHeight());
                dropAnimation.setDuration(2000); // adjust the duration as needed
                dropAnimation.setInterpolator(new AccelerateInterpolator()); // add an acceleration effect to the animation

                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Rect R1 = new Rect();
                        imageView.getHitRect(R1);
                        R1.offsetTo((int)imageView.getX(), (int)imageView.getY());
                        if (isCollisionDetected2(R1, kermit) && !untouchable) {
                            untouchable = true;
                            hurtanimation();
                            hearts--;
                            displayHearts();

                            if(hearts ==0){
                                kermit.setVisibility(View.INVISIBLE);
                                kermit.setY(1000);
                                kermit.setX(-100);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dead = true;
                                    }
                                }, 7000);
                            }
                            // Delay resetting isKermitInvincible back to false for 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    untouchable = false;
                                }
                            }, 3500);
                        }
                    }

                };
                dropAnimation.addUpdateListener(updateListener);

                dropAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        parentView.removeView(imageView);
                    }
                });

                dropAnimation.start();

                // remove the listener to avoid redundant calls
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void rightfireball() {
        // create a new ImageView to drop
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.fireball);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(130, 40));

        // add the ImageView to the parent view
        ViewGroup parentView = findViewById(R.id.elmo_boss_fight);
        parentView.addView(imageView);

        // wait for the parent view to finish laying out its children before setting the position of the ImageView
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the initial position of the ImageView offscreen to the right of the screen
                imageView.setX(parentView.getWidth());
                imageView.setY(750);
                imageView.animate().scaleX(-1).start();
                // animate the ImageView to move towards the left side of the screen
                ObjectAnimator dropAnimation = ObjectAnimator.ofFloat(imageView, "translationX", -imageView.getWidth());
                dropAnimation.setDuration(2000); // adjust the duration as needed
                dropAnimation.setInterpolator(new AccelerateInterpolator()); // add an acceleration effect to the animation

                // detect collisions with Kermit during the animation
                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Rect R1 = new Rect();
                        imageView.getHitRect(R1);

                        R1.offsetTo((int)imageView.getX(), (int)imageView.getY());
                        if (isCollisionDetected2(R1, kermit) && !untouchable) {
                            untouchable = true;
                            hurtanimation();
                            hearts--;
                            displayHearts();

                            if(hearts ==0){
                                kermit.setVisibility(View.INVISIBLE);
                                kermit.setY(1000);
                                kermit.setX(-100);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dead = true;
                                    }
                                }, 7000);
                            }
                            // Delay resetting isKermitInvincible back to false for 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    untouchable = false;
                                }
                            }, 3500);
                        }
                    }
                };
                dropAnimation.addUpdateListener(updateListener);

                dropAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        incoming_right.setVisibility(View.INVISIBLE);
                        parentView.removeView(imageView);
                    }
                });

                dropAnimation.start();

                // remove the listener to avoid redundant calls
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void leftfireball() {
        // create a new ImageView to drop
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.fireball);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(130, 40));

        // add the ImageView to the parent view
        ViewGroup parentView = findViewById(R.id.elmo_boss_fight);
        parentView.addView(imageView);

        // wait for the parent view to finish laying out its children before setting the position of the ImageView
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the initial position of the ImageView to be on the left of the screen
                imageView.setX(-imageView.getWidth());
                imageView.setY(750);
                imageView.bringToFront();

                // animate the ImageView to move to the right
                ObjectAnimator dropAnimation = ObjectAnimator.ofFloat(imageView, "translationX", parentView.getWidth());
                dropAnimation.setDuration(2000); // adjust the duration as needed
                dropAnimation.setInterpolator(new AccelerateInterpolator()); // add an acceleration effect to the animation

                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Rect R1 = new Rect();
                        imageView.getHitRect(R1);
                        R1.offsetTo((int)imageView.getX(), (int)imageView.getY());
                        if (isCollisionDetected2(R1, kermit) && !untouchable) {
                            untouchable = true;
                            hurtanimation();
                            hearts--;
                            displayHearts();

                            if(hearts ==0){
                                kermit.setVisibility(View.INVISIBLE);
                                kermit.setY(1000);
                                kermit.setX(-100);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dead = true;
                                    }
                                }, 7000);
                            }
                            // Delay resetting isKermitInvincible back to false for 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    untouchable = false;
                                }
                            }, 3500);
                        }
                    }

                };
                dropAnimation.addUpdateListener(updateListener);

                dropAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        incoming_left.setVisibility(View.INVISIBLE);
                        parentView.removeView(imageView);
                    }
                });

                dropAnimation.start();

                // remove the listener to avoid redundant calls
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void shakescreen(){
        ConstraintLayout layout = findViewById(R.id.elmo_boss_fight);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        layout.startAnimation(shake);

    }

    public void youdied(){
        AudioPlay.stopAudio();
        AudioPlay.playAudio(this,R.raw.youdied);
        Animation you_died = AnimationUtils .loadAnimation(this,R.anim.you_died);
        youdied.startAnimation(you_died);
        you_died.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                youdied.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void gifbackground(){
        ConstraintLayout layout = findViewById(R.id.elmo_boss_fight);

        Glide.with(this)
                .asGif()
                .load(R.drawable.hellback)
                .into(new CustomTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        resource.setLoopCount(GifDrawable.LOOP_FOREVER);
                        resource.start();
                        layout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Do nothing
                    }
                });
    }

    public void startfire(int num){
        if(num ==1){
            fire_platform1.setVisibility(View.VISIBLE);
            fire_platform1.setImageResource(R.mipmap.fire_small);
            danger1.setVisibility(View.VISIBLE);
            fire1last = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    danger1.setVisibility(View.INVISIBLE);
                    fire_platform1.setImageResource(R.mipmap.fire_big);
                    fire_platform1_hurts = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fire_platform1.setVisibility(View.INVISIBLE);
                            fire_platform1_hurts = false;
                        }
                    }, 2000);
                }
            }, 2000);
        }

        if(num ==2){
            fire_platform2.setVisibility(View.VISIBLE);
            fire_platform2.setImageResource(R.mipmap.fire_small);
            danger2.setVisibility(View.VISIBLE);
            fire2last = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    danger2.setVisibility(View.INVISIBLE);
                    fire_platform2.setImageResource(R.mipmap.fire_big);
                    fire_platform2_hurts = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fire_platform2.setVisibility(View.INVISIBLE);
                            fire_platform2_hurts = false;
                        }
                    }, 2000);
                }
            }, 2000);
        }

        if(num ==3){
            fire_platform3.setVisibility(View.VISIBLE);
            fire_platform3.setImageResource(R.mipmap.fire_small);
            danger3.setVisibility(View.VISIBLE);
            fire3last = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    danger3.setVisibility(View.INVISIBLE);
                    fire_platform3.setImageResource(R.mipmap.fire_big);
                    fire_platform3_hurts = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fire_platform3.setVisibility(View.INVISIBLE);
                            fire_platform3_hurts = false;
                        }
                    }, 2000);
                }
            }, 2000);
        }
    }


    public boolean kermithurtchecks(){
        if(isCollisionDetected(kermit,fire_platform1) && fire_platform1_hurts){
            return true;
        }
        if(isCollisionDetected(kermit,fire_platform2) && fire_platform2_hurts){
            return true;
        }
        if(isCollisionDetected(kermit,fire_platform3) && fire_platform3_hurts){
            return true;
        }
        return false;
    }

    private boolean Hitinthehead(ImageView imageView1, ImageView misspiggy) {
        Rect rect1 = new Rect();
        imageView1.getHitRect(rect1);
        Rect rect2 = new Rect();
        misspiggy.getHitRect(rect2);

        // Check if the x-coordinates overlap
        if (rect1.right > rect2.left && rect1.left < rect2.right) {
            // Check if the bottom of imageView1 overlaps with the top of imageView2
            if (rect1.bottom >= rect2.top && rect1.bottom <= rect2.bottom) {
                return true;
            }
        }

        return false;
    }

    public void fireplatform_timing(){
        //if too hard remove the option of both
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(fireplatform_timing){
                            final int random = new Random().nextInt(2);
                            if(random ==0){
                                Random random2 = new Random();

                                int number1 = random2.nextInt(3);
                                startfire(number1+1);

                                int number2;
                                do {
                                    number2 = random2.nextInt(3);
                                } while (number1 == number2);

                                startfire(number2+1);
                            }
                            else{
                                Random random3 = new Random();

                                int number3 = random3.nextInt(3);
                                startfire(number3+1);
                            }
                        }
                    }
                });
            }
        }, 4500, 4500);
    }

    public void fireball_timing(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(fireball_timing){
                            final int random = new Random().nextInt(2);
                            if(random ==0){
                                incoming_right.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        rightfireball();
                                    }
                                }, 1000);
                            }
                            else{
                                incoming_left.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        leftfireball();
                                    }
                                }, 1000);
                            }
                        }
                    }
                });
            }
        }, 3500, 3500);
    }

    public void falling_fireball_timing_f1(){

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(falling_fireball_timing&&faze1){
                            falling_fireball();
                        }
                    }
                });
            }
        }, 1000, 1000);

    }

    public void falling_fireball_timing_f2(){

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(falling_fireball_timing&&faze2){
                            falling_fireball();
                        }
                    }
                });
            }
        }, 1000, 600);

    }

    public void falling_fireball_timing_f3(){

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(falling_fireball_timing&&faze3){
                            falling_fireball();
                        }
                    }
                });
            }
        }, 1000, 400);

    }

    public void shooting_timing(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(shooting_timing && bossisdown){
                            elmoshoot();
                        }
                    }

                });
            }
        }, 1000, 1000);
    }

    public void shooting_timing_bossisdown(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(Shooting_timing_fromback && bossisdown){
                            elmoshootFromBack();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    @Override
    public void onBackPressed() {

    }

}
