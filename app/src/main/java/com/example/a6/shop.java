package com.example.a6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class shop extends AppCompatActivity {

    Button buy_profilepic1, buy_profilepic2, buy_profilepic3, buy_profilepic4, buy_profilepic5, buy_profilepic6, buy_profilepic7, buy_profilepic8;
    ImageView returntomenu;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    TextView tv_teamoney;
    Query checkuser;
    int teamoney=0,intprofilepic;
    String currentuserID;
    int price;
    boolean Haveppic;
    boolean Haveppic1,Haveppic2,Haveppic3,Haveppic4,Haveppic5,Haveppic6,Haveppic7,Haveppic8;


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
        setContentView(R.layout.shop);
        returntomenu = findViewById(R.id.return_to_menu);
        tv_teamoney = findViewById(R.id.tv_teamoney);
        buy_profilepic1 = findViewById(R.id.btn_profilepic1);
        buy_profilepic2 = findViewById(R.id.btn_profilepic2);
        buy_profilepic3 = findViewById(R.id.btn_profilepic3);
        buy_profilepic4 = findViewById(R.id.btn_profilepic4);
        buy_profilepic5 = findViewById(R.id.btn_profilepic5);
        buy_profilepic6 = findViewById(R.id.btn_profilepic6);
        buy_profilepic7 = findViewById(R.id.btn_profilepic7);
        buy_profilepic8 = findViewById(R.id.btn_profilepic8);






        // authentication
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
                    if (snapshot.exists()) {
                        teamoney = snapshot.child(currentuserID).child("score").getValue(int.class);
                        tv_teamoney.setText("" + teamoney);
                        Haveppic1 = snapshot.child(currentuserID).child("ppic1").getValue(boolean.class);
                        Haveppic2 = snapshot.child(currentuserID).child("ppic2").getValue(boolean.class);
                        Haveppic3 = snapshot.child(currentuserID).child("ppic3").getValue(boolean.class);
                        Haveppic4 = snapshot.child(currentuserID).child("ppic4").getValue(boolean.class);
                        Haveppic5 = snapshot.child(currentuserID).child("ppic5").getValue(boolean.class);
                        Haveppic6 = snapshot.child(currentuserID).child("ppic6").getValue(boolean.class);
                        Haveppic7 = snapshot.child(currentuserID).child("ppic7").getValue(boolean.class);
                        Haveppic8 = snapshot.child(currentuserID).child("ppic8").getValue(boolean.class);


                        changeText();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


        }






    }







    public void onClick(View view) {
        if(view == returntomenu){
            startActivity(new Intent(this, menu.class));
            onRestart();
        }

        if(view == buy_profilepic1){
            price =0;
            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                if (HaveEnoughMoney(teamoney, price) && !Haveppic1) {
                    intprofilepic = 1;
                    teamoney = teamoney - price;
                    HashMap user = new HashMap();
                    user.put("profilepic", intprofilepic);
                    user.put("score", teamoney);
                    user.put("ppic1", true);
                    reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
                else if (!HaveEnoughMoney(teamoney, price) && !Haveppic1) {
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
                else if (Haveppic1) {
                    Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                    intprofilepic = 1;
                    HashMap user = new HashMap();
                    user.put("profilepic", intprofilepic);
                    reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
            }
        }

        if(view == buy_profilepic2){
            price =20;

            if(HaveEnoughMoney(teamoney,price) && !Haveppic2){
                intprofilepic =2;
                teamoney = teamoney - price;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                user.put("score", teamoney);
                user.put("ppic2", true);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
            else if(!HaveEnoughMoney(teamoney,price)  && !Haveppic2){
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
               else{
                    Toast.makeText(shop.this, "Log in or Sign up to collect tea and buy pictures!", Toast.LENGTH_LONG).show();
                }
            }
            else if(Haveppic2){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =2;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }

        }

        if(view == buy_profilepic3){
            price =20;

            if(HaveEnoughMoney(teamoney,price) && !Haveppic3){
                intprofilepic =3;
                teamoney = teamoney - price;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                user.put("score", teamoney);
                user.put("ppic3", true);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
            else if(!HaveEnoughMoney(teamoney,price) && !Haveppic3){
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(shop.this, "Log in or Sign up to collect tea and buy pictures!", Toast.LENGTH_LONG).show();
                }
            }
            else if(Haveppic3){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =3;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }



        }

        if(view == buy_profilepic4){
            price =25;

            if(HaveEnoughMoney(teamoney,price) && !Haveppic4){
                intprofilepic =4;
                teamoney = teamoney - price;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                user.put("score", teamoney);
                user.put("ppic4", true);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
            else if(!HaveEnoughMoney(teamoney,price)&& !Haveppic4){
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(shop.this, "Log in or Sign up to collect tea and buy pictures!", Toast.LENGTH_LONG).show();
                }
            }
            else if(Haveppic4){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =4;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }


        }

        if(view == buy_profilepic5){
            price =25;

            if(HaveEnoughMoney(teamoney,price) && !Haveppic5){
                intprofilepic =5;
                teamoney = teamoney - price;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                user.put("score", teamoney);
                user.put("ppic5", true);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
            else if(!HaveEnoughMoney(teamoney,price)&& !Haveppic5){
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(shop.this, "Log in or Sign up to collect tea and buy pictures!", Toast.LENGTH_LONG).show();
                }
            }
            else if(Haveppic5){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =5;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }

        }

        if(view == buy_profilepic6){
            price =30;

            if(HaveEnoughMoney(teamoney,price) && !Haveppic6){
                intprofilepic =6;
                teamoney = teamoney - price;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                user.put("score", teamoney);
                user.put("ppic6", true);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
            else if(!HaveEnoughMoney(teamoney,price)&& !Haveppic6){
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(shop.this, "Log in or Sign up to collect tea and buy pictures!", Toast.LENGTH_LONG).show();
                }
            }
            else if(Haveppic6){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =6;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }

        }

        if(view == buy_profilepic7){
            price =40;

            if(HaveEnoughMoney(teamoney,price) && !Haveppic7){
                intprofilepic =7;
                teamoney = teamoney - price;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                user.put("score", teamoney);
                user.put("ppic7", true);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
            else if(!HaveEnoughMoney(teamoney,price)&& !Haveppic7){
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Toast.makeText(shop.this, "You don't have enough money dumbass", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(shop.this, "Log in or Sign up to collect tea and buy pictures!", Toast.LENGTH_LONG).show();
                }
            }
            else if(Haveppic7){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =7;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }

        }

        if(view == buy_profilepic8){
             if(Haveppic8){
                Toast.makeText(shop.this, "use", Toast.LENGTH_SHORT).show();
                intprofilepic =8;
                HashMap user = new HashMap();
                user.put("profilepic", intprofilepic);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
             else{
                 Toast.makeText(shop.this, "It can only be unlocked in a mysterious way", Toast.LENGTH_SHORT).show();
             }
        }
    }


    public boolean HaveEnoughMoney(int intprofilepic_, int price){

        int moneyleft = intprofilepic_ - price;

        if(moneyleft>=0){
            return true;
        }

        if(moneyleft<0){
            return false;
        }

        return false;

    }


    public void changeText(){



        if(Haveppic1){
            buy_profilepic1.setText("use");
        }
        if(Haveppic2){
            buy_profilepic2.setText("use");
        }
        if(Haveppic3){
            buy_profilepic3.setText("use");
        }
        if(Haveppic4){
            buy_profilepic4.setText("use");
        }
        if(Haveppic5){
            buy_profilepic5.setText("use");
        }
        if(Haveppic6){
            buy_profilepic6.setText("use");
        }
        if(Haveppic7){
            buy_profilepic7.setText("use");
        }
        if(Haveppic8){
            buy_profilepic8.setText("use");
        }

    }

    @Override
    public void onBackPressed() {

    }

}