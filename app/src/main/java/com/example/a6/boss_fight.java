package com.example.a6;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class boss_fight extends AppCompatActivity {

    TextView fueltime,youdied;
    boolean moveright = false, moveleft = false, hitleft=false,hitright=false,jump=false,onground=true,maxheight = false,gotea = false,dead = false,gotfuel = true;
    int height=0,score,teafuelcount = 30, count=0;;
    TextView tvscore;
    int hearts =3,piggyhearts =10;
    float piggystartX, piggystartY;
    ImageView kermit,misspiggy,wallright, wallleft,ground,tea;
    ImageView heart1,heart2,heart3;
    ImageView piggyheart1,piggyheart2,piggyheart3, piggyheart4,piggyheart5,piggyheart6, piggyheart7,piggyheart8,piggyheart9,piggyheart10;
    ImageView platform1,lw_platform1,rw_platform1,dw_platform1;
    ImageView platform2,lw_platform2,rw_platform2,dw_platform2;
    ImageView platform3,lw_platform3,rw_platform3,dw_platform3;


    ImageView aroundfueltime;
    float piggymovementspeed= 0.5F;

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
        setContentView(R.layout.boss_fight);
        kermit = findViewById(R.id.IV_Kermit);
        btnleft = findViewById(R.id.btnleft);
        btnright = findViewById(R.id.btnright);
        btnjump = findViewById(R.id.btn_jump);
        wallleft = findViewById(R.id.ivwall);
        wallright = findViewById(R.id.ivwall2);
        ground = findViewById(R.id.ivground);
        misspiggy = findViewById(R.id.miss_piggy);
        aroundfueltime = findViewById(R.id.aroundfueltime);
        fueltime = findViewById(R.id.fueltime);
        youdied = findViewById(R.id.you_died);

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

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


        score = (getIntent().getExtras().getInt("score"));


        fueltime.setVisibility(View.INVISIBLE);
        aroundfueltime.setVisibility(View.INVISIBLE);

        misspiggy.animate().scaleX(-1).start();

        piggystartY = misspiggy.getY();
        piggystartX = misspiggy.getX();

        AudioPlay.playAudio(this,R.raw.piggybossmusic);
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
                        misspiggymovement();
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
        if(isCollisionDetected(kermit,wallleft)||isCollisionDetected(kermit,rw_platform1)|| isCollisionDetected(kermit,rw_platform3)|| isCollisionDetected(kermit,rw_platform2)){
            hitleft= true;
        }
        else
            hitleft= false;
    }

    public void hitwalltoright(){
        //write here every right wall looooooooool
        if(isCollisionDetected(kermit,wallright) || isCollisionDetected(kermit,lw_platform1)|| isCollisionDetected(kermit,lw_platform3)|| isCollisionDetected(kermit,lw_platform2)){
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
        Intent i = new Intent(this, gameover.class);
        i.putExtra("score", score);
        startActivity(i);

    }

    public void AyyoNextLevel(){
        AudioPlay.stopAudio();
        Intent i = new Intent(this, was_it_worth_it.class);
        i.putExtra("score", score);
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
            misspiggy.animate().scaleX(1).start();
            piggymoveleft = false;
            piggymoveright = true;
        }
    }

    public void hitrightpiggy(){
        if(isCollisionDetected(misspiggy,wallright)){
            misspiggy.animate().scaleX(-1).start();
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
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
        anim.setDuration(2000); // Set the duration of the animation
        kermit.startAnimation(anim);
    }

    public void piggyhurt(){
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
            else if(isCollisionDetected(kermit,misspiggy) && !Hitinthehead(kermit,misspiggy) && hearts >=2){
                Untouchable();
                if(hearts>0) {
                    hearts--;
                }
                displayHearts();
                knockback(130);
                hurtanimation();
            }
            if(Hitinthehead(kermit,misspiggy)){
                onground= true;
                height =0;
                maxheight = false;
                jump = true;
                Untouchable();
                piggyhurt();
                if(piggyhearts>0)
                    piggyhearts--;

                displaypiggyhearts();
            }
        }

    }

    public void Untouchable(){
        untouchable = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                untouchable = false;
            }
        }, 1200);
    }

    public void displayHearts(){
        if(hearts ==3){
            heart3.setImageResource(R.mipmap.heartfull);
            heart2.setImageResource(R.mipmap.heartfull);
            heart1.setImageResource(R.mipmap.heartfull);
        }
        if(hearts ==2){
            heart3.setImageResource(R.mipmap.heartlost);
            heart2.setImageResource(R.mipmap.heartfull);
            heart1.setImageResource(R.mipmap.heartfull);
            shakescreen();
        }
        if(hearts ==1){
            heart3.setImageResource(R.mipmap.heartlost);
            heart2.setImageResource(R.mipmap.heartlost);
            heart1.setImageResource(R.mipmap.heartfull);
            shakescreen();
        }
        if(hearts ==0){
            heart3.setImageResource(R.mipmap.heartlost);
            heart2.setImageResource(R.mipmap.heartlost);
            heart1.setImageResource(R.mipmap.heartlost);
            shakescreen();
            youdied();
        }
    }

    public void displaypiggyhearts(){
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
            piggymovementspeed = 3F;
            piggyteleport();
            startDroppingImageViews();
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
            piggymovementspeed = 3F;
            piggyteleport();
            startDroppingImageViews();
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
            piggymovementspeed = 3F;
            piggyteleport();
            startDroppingImageViews();
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
            piggymovementspeed = 3F;
            piggyteleport();
            startDroppingImageViews();
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
            piggyexplode();
            AudioPlay.stopAudio();
            AudioPlay.playAudio(this,R.raw.birds);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   gotea=true;
                }
            }, 10000);

        }
    }



    public void piggyteleport(){
        // get the parent view that contains the image view
        ViewGroup parentView = findViewById(R.id.boss_fight);

        // create an AlphaAnimation to fade out ImageView2
        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1f, 0.2f);
        fadeOutAnimation.setDuration(1200); // adjust the duration as needed
        fadeOutAnimation.setFillAfter(true); // keep the alpha value after the animation ends
        misspiggy.startAnimation(fadeOutAnimation);

        // set the new position of ImageView2 to the top right of the screen
        int parentWidth = parentView.getWidth();
        int parentHeight = parentView.getHeight();
        int imageWidth = misspiggy.getWidth();
        int imageHeight = misspiggy.getHeight();
        int newX = parentWidth - imageWidth;
        int newY = 0;
        misspiggy.setX(newX);
        misspiggy.setY(newY);

        // create another AlphaAnimation to fade in ImageView2
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.2f, 1f);
        fadeInAnimation.setDuration(1200); // adjust the duration as needed
        fadeInAnimation.setFillAfter(true); // keep the alpha value after the animation ends
        misspiggy.startAnimation(fadeInAnimation);

        // post a delayed runnable to teleport Miss Piggy back to its original position after 10 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int startX =0;
                final int random = new Random().nextInt(3);
                if(random ==1){
                    startX = 0;
                }
                else{
                    startX =  (parentWidth - imageWidth);
                }

                int startY = parentHeight - 2*imageHeight - 55;
                piggymovementspeed = 0.5F;
                handlerdrop.removeCallbacks(dropRunnable);
                misspiggy.setX(startX);
                misspiggy.setY(startY);
            }
        }, 20000);
    }


    public void piggyexplode(){
        // create a ScaleAnimation to scale up the view
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500); // set the duration of the animation

// create an AlphaAnimation to fade out the view
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(500); // set the duration of the animation

// create an AnimationSet to combine the scale and alpha animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

// set the AnimationListener to hide the view when the animation finishes
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                misspiggy.setVisibility(View.GONE); // hide the view when the animation finishes
                piggymovementspeed=0;
                misspiggy.setX(0);
                misspiggy.setY(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // do nothing
            }
        });

        misspiggy.startAnimation(animationSet); // start the animation on the view
    }


    private Handler handlerdrop = new Handler();
        private Runnable dropRunnable = new Runnable() {
            @Override
            public void run() {
                dropImageView();
                final int random = new Random().nextInt(501) + 100;
                handlerdrop.postDelayed(dropRunnable, random); // run the same Runnable after 1 second
            }
        };

    private void startDroppingImageViews() {
        handlerdrop.postDelayed(dropRunnable, 200); // start the timer to drop the first ImageView after 1 second
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
                        if (isCollisionDetected2(R1, kermit) && !isKermitInvincible) {
                            isKermitInvincible = true;
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
                                    isKermitInvincible = false;
                                }
                            }, 2000);
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

    public void shakescreen(){
        ConstraintLayout layout = findViewById(R.id.boss_fight);

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
        ConstraintLayout layout = findViewById(R.id.boss_fight);

        Glide.with(this)
                .asGif()
                .load(R.drawable.firebackground)
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
    @Override
    public void onBackPressed() {

    }

}
