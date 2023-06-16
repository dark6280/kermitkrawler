package com.example.a6;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class lb_bestscore extends AppCompatActivity {

    ImageView ivnextpage,ivlastpage,ivback;
    TextView tvrank1,tvrank2,tvrank3,tvrank4,tvrank5,tvrank6,tvrank7,tvrank8,tvrank9,tvrank10;
    TextView tvrank1score,tvrank2score,tvrank3score,tvrank4score,tvrank5score,tvrank6score,tvrank7score,tvrank8score,tvrank9score,tvrank10score;
    TextView tvrank1name,tvrank2name,tvrank3name,tvrank4name,tvrank5name,tvrank6name,tvrank7name,tvrank8name,tvrank9name,tvrank10name;
    TextView tvplace1,tvplace2,tvplace3,tvplace4,tvplace5,tvplace6,tvplace7,tvplace8,tvplace9,tvplace10;
    ImageView ivrank1pic,ivrank2pic,ivrank3pic,ivrank4pic,ivrank5pic,ivrank6pic,ivrank7pic,ivrank8pic,ivrank9pic,ivrank10pic;


    TextView tvranking;
    Button btnnormal,btnextreme,btnlvl1,btnlvl2,btnlvl3,btnlvl4,btn_boss_battle;

    String currentuserID,Uemail,username;
    int totalscore,Upic,bestscore;


    HashMap<String, Integer> hashMap = new HashMap<>();
    HashMap<String, Integer> hashMapbestscore = new HashMap<>();
    Map<String, Integer> sortedMapAsc;
    Iterator myVeryOwnIterator;
    int i=1;
    String key,value,name,email;

    int page=1,totalranks=0,totalpages=0;
    int removeextras=0;

    ActionBar actionBar;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DAOUser dao = new DAOUser();

    Query checkuser;
    FirebaseDatabase db;
    DatabaseReference database;


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
        setContentView(R.layout.leaderboard);
        ivnextpage = findViewById(R.id.ivnextpage);
        ivlastpage = findViewById(R.id.ivlastpage);

        tvranking=findViewById(R.id.tvranking);
        btnnormal=findViewById(R.id.btn_totalscore);
        btnextreme=findViewById(R.id.btn_bestscore);
        btnlvl1=findViewById(R.id.btn_lvl1);
        btnlvl2=findViewById(R.id.btn_lvl2);
        btnlvl3=findViewById(R.id.btn_lvl3);
        btnlvl4=findViewById(R.id.btn_lvl4);
        btn_boss_battle = findViewById(R.id.btn_boss_battle);
        ivback=findViewById(R.id.ivback);

        tvrank1 = findViewById(R.id.tvrank1);
        tvrank2 = findViewById(R.id.tvrank2);
        tvrank3 = findViewById(R.id.tvrank3);
        tvrank4 = findViewById(R.id.tvrank4);
        tvrank5 = findViewById(R.id.tvrank5);
        tvrank6 = findViewById(R.id.tvrank6);
        tvrank7 = findViewById(R.id.tvrank7);
        tvrank8 = findViewById(R.id.tvrank8);
        tvrank9 = findViewById(R.id.tvrank9);
        tvrank10 = findViewById(R.id.tvrank10);

        tvrank1score = findViewById(R.id.tvrank1score);
        tvrank2score = findViewById(R.id.tvrank2score);
        tvrank3score = findViewById(R.id.tvrank3score);
        tvrank4score = findViewById(R.id.tvrank4score);
        tvrank5score = findViewById(R.id.tvrank5score);
        tvrank6score = findViewById(R.id.tvrank6score);
        tvrank7score = findViewById(R.id.tvrank7score);
        tvrank8score = findViewById(R.id.tvrank8score);
        tvrank9score = findViewById(R.id.tvrank9score);
        tvrank10score = findViewById(R.id.tvrank10score);


        tvrank1name = findViewById(R.id.tvrank1name);
        tvrank2name = findViewById(R.id.tvrank2name);
        tvrank3name = findViewById(R.id.tvrank3name);
        tvrank4name = findViewById(R.id.tvrank4name);
        tvrank5name = findViewById(R.id.tvrank5name);
        tvrank6name = findViewById(R.id.tvrank6name);
        tvrank7name = findViewById(R.id.tvrank7name);
        tvrank8name = findViewById(R.id.tvrank8name);
        tvrank9name = findViewById(R.id.tvrank9name);
        tvrank10name = findViewById(R.id.tvrank10name);

        tvplace1 = findViewById(R.id.tvplace1);
        tvplace2 = findViewById(R.id.tvplace2);
        tvplace3 = findViewById(R.id.tvplace3);
        tvplace4 = findViewById(R.id.tvplace4);
        tvplace5 = findViewById(R.id.tvplace5);
        tvplace6 = findViewById(R.id.tvplace6);
        tvplace7 = findViewById(R.id.tvplace7);
        tvplace8 = findViewById(R.id.tvplace8);
        tvplace9 = findViewById(R.id.tvplace9);
        tvplace10 = findViewById(R.id.tvplace10);


        ivrank1pic = findViewById(R.id.ivrank1pic);
        ivrank2pic = findViewById(R.id.ivrank2pic);
        ivrank3pic = findViewById(R.id.ivrank3pic);
        ivrank4pic = findViewById(R.id.ivrank4pic);
        ivrank5pic = findViewById(R.id.ivrank5pic);
        ivrank6pic = findViewById(R.id.ivrank6pic);
        ivrank7pic = findViewById(R.id.ivrank7pic);
        ivrank8pic = findViewById(R.id.ivrank8pic);
        ivrank9pic = findViewById(R.id.ivrank9pic);
        ivrank10pic = findViewById(R.id.ivrank10pic);

        mAuth = FirebaseAuth.getInstance();
        mUser =mAuth.getCurrentUser();

        //screen
        actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Ranking();
        btnextreme.setTextColor(Color.RED);
        tvranking.setText("Universe Ranking (best score)");
        tvranking.setTextColor(Color.parseColor("#05783c"));
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), menu.class);
        startActivity(i);
    }


    public void ShowRanks(){
        sortedMapAsc = sortByValue(hashMap, false);
        myVeryOwnIterator=sortedMapAsc.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            key=(String)myVeryOwnIterator.next();
            String[] separated = key.split(":");
            email=separated[0];
            name=separated[1];
            Upic = Integer.parseInt(separated[2]);

            value=((Integer)sortedMapAsc.get(key)).toString();
            if(i==1+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank1.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }

                profilepicchanger(Upic,ivrank1pic);
                tvrank1score.setText(value);
                tvrank1name.setText(name);
                tvplace1.setText(i+".");
                tvrank1.setText("");
            }
            else if(i==2+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank2.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank2pic);
                tvrank2score.setText(value);
                tvrank2name.setText(name);
                tvplace2.setText(i+".");
                tvrank2.setText("");
            }
            else if(i==3+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank3.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank3pic);
                tvrank3score.setText(value);
                tvrank3name.setText(name);
                tvplace3.setText(i+".");
                tvrank3.setText("");
            }
            else if(i==4+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank4.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank4pic);
                tvrank4score.setText(value);
                tvrank4name.setText(name);
                tvplace4.setText(i+".");
                tvrank4.setText("");
            }
            else if(i==5+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank5.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank5pic);
                tvrank5score.setText(value);
                tvrank5name.setText(name);
                tvplace5.setText(i+".");
                tvrank5.setText("");
            }
            else if(i==6+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank6.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank6pic);
                tvrank6score.setText(value);
                tvrank6name.setText(name);
                tvplace6.setText(i+".");
                tvrank6.setText("");
            }
            else if(i==7+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank7.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank7pic);
                tvrank7score.setText(value);
                tvrank7name.setText(name);
                tvplace7.setText(i+".");
                tvrank7.setText("");
            }
            else if(i==8+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank8.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank8pic);
                tvrank8score.setText(value);
                tvrank8name.setText(name);
                tvplace8.setText(i+".");
                tvrank8.setText("");
            }
            else if(i==9+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank9.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank9pic);
                tvrank9score.setText(value);
                tvrank9name.setText(name);
                tvplace9.setText(i+".");
                tvrank9.setText("");
            }
            else if(i==10+(page-1)*10){
                if(mUser!=null){
                    if(mUser.getEmail().equals(email)){
                        tvrank10.setBackgroundColor(Color.parseColor("#dbc1a9"));
                    }
                }
                profilepicchanger(Upic,ivrank10pic);
                tvrank10score.setText(value);
                tvrank10name.setText(name);
                tvplace10.setText(i+".");
                tvrank10.setText("");
            }
            i++;
        }
        if(page==totalpages){
            if(removeextras==1){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==2){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==3){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==4){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
                tvrank7.setText("-------------");
                tvrank7score.setText("");
                tvrank7name.setText("");
                tvplace7.setText("");
                ivrank7pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==5){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
                tvrank7.setText("-------------");
                tvrank7score.setText("");
                tvrank7name.setText("");
                tvplace7.setText("");
                ivrank7pic.setVisibility(View.INVISIBLE);
                tvrank6.setText("-------------");
                tvrank6score.setText("");
                tvrank6name.setText("");
                tvplace6.setText("");
                ivrank6pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==6){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
                tvrank7.setText("-------------");
                tvrank7score.setText("");
                tvrank7name.setText("");
                tvplace7.setText("");
                ivrank7pic.setVisibility(View.INVISIBLE);
                tvrank6.setText("-------------");
                tvrank6score.setText("");
                tvrank6name.setText("");
                tvplace6.setText("");
                ivrank6pic.setVisibility(View.INVISIBLE);
                tvrank5.setText("-------------");
                tvrank5score.setText("");
                tvrank5name.setText("");
                tvplace5.setText("");
                ivrank5pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==7){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
                tvrank7.setText("-------------");
                tvrank7score.setText("");
                tvrank7name.setText("");
                tvplace7.setText("");
                ivrank7pic.setVisibility(View.INVISIBLE);
                tvrank6.setText("-------------");
                tvrank6score.setText("");
                tvrank6name.setText("");
                tvplace6.setText("");
                ivrank6pic.setVisibility(View.INVISIBLE);
                tvrank5.setText("-------------");
                tvrank5score.setText("");
                tvrank5name.setText("");
                tvplace5.setText("");
                ivrank5pic.setVisibility(View.INVISIBLE);
                tvrank4.setText("-------------");
                tvrank4score.setText("");
                tvrank4name.setText("");
                tvplace4.setText("");
                ivrank4pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==8){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
                tvrank7.setText("-------------");
                tvrank7score.setText("");
                tvrank7name.setText("");
                tvplace7.setText("");
                ivrank7pic.setVisibility(View.INVISIBLE);
                tvrank6.setText("-------------");
                tvrank6score.setText("");
                tvrank6name.setText("");
                tvplace6.setText("");
                ivrank6pic.setVisibility(View.INVISIBLE);
                tvrank5.setText("-------------");
                tvrank5score.setText("");
                tvrank5name.setText("");
                tvplace5.setText("");
                ivrank5pic.setVisibility(View.INVISIBLE);
                tvrank4.setText("-------------");
                tvrank4score.setText("");
                tvrank4name.setText("");
                tvplace4.setText("");
                ivrank4pic.setVisibility(View.INVISIBLE);
                tvrank3.setText("-------------");
                tvrank3score.setText("");
                tvrank3name.setText("");
                tvplace3.setText("");
                ivrank3pic.setVisibility(View.INVISIBLE);
            }
            if(removeextras==9){
                tvrank10.setText("-------------");
                tvrank10score.setText("");
                tvrank10name.setText("");
                tvplace10.setText("");
                ivrank10pic.setVisibility(View.INVISIBLE);
                tvrank9.setText("-------------");
                tvrank9score.setText("");
                tvrank9name.setText("");
                tvplace9.setText("");
                ivrank9pic.setVisibility(View.INVISIBLE);
                tvrank8.setText("-------------");
                tvrank8score.setText("");
                tvrank8name.setText("");
                tvplace8.setText("");
                ivrank8pic.setVisibility(View.INVISIBLE);
                tvrank7.setText("-------------");
                tvrank7score.setText("");
                tvrank7name.setText("");
                tvplace7.setText("");
                ivrank7pic.setVisibility(View.INVISIBLE);
                tvrank6.setText("-------------");
                tvrank6score.setText("");
                tvrank6name.setText("");
                tvplace6.setText("");
                ivrank6pic.setVisibility(View.INVISIBLE);
                tvrank5.setText("-------------");
                tvrank5score.setText("");
                tvrank5name.setText("");
                tvplace5.setText("");
                ivrank5pic.setVisibility(View.INVISIBLE);
                tvrank4.setText("-------------");
                tvrank4score.setText("");
                tvrank4name.setText("");
                tvplace4.setText("");
                ivrank4pic.setVisibility(View.INVISIBLE);
                tvrank3.setText("-------------");
                tvrank3score.setText("");
                tvrank3name.setText("");
                tvplace3.setText("");
                ivrank3pic.setVisibility(View.INVISIBLE);
                tvrank2.setText("-------------");
                tvrank2score.setText("");
                tvrank2name.setText("");
                tvplace2.setText("");
                ivrank2pic.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void Ranking(){


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    bestscore = ds.child("bestscore").getValue(int.class);
                    username = ds.child("username").getValue(String.class);
                    Uemail = ds.child("email").getValue(String.class);
                    Upic = ds.child("profilepic").getValue(int.class);

                    if(bestscore!=0){
                        totalranks++;
                        hashMap.put(Uemail+":"+username +":" + Upic,bestscore);
                    }
                    if(totalranks%10==0){
                        totalpages=totalranks/10;
                    }
                    else{
                        totalpages=totalranks/10+1;
                    }
                }
                if(totalpages==1){
                    ivnextpage.setVisibility(View.INVISIBLE);
                }
                removeextras=totalpages*10-totalranks;
                sortedMapAsc = sortByValue(hashMap, false);
                ShowRanks();
                i=1;
                printMap(sortedMapAsc);

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

    }

    public void makeallvisible(){
        ivrank10pic.setVisibility(View.VISIBLE);
        ivrank9pic.setVisibility(View.VISIBLE);
        ivrank8pic.setVisibility(View.VISIBLE);
        ivrank7pic.setVisibility(View.VISIBLE);
        ivrank6pic.setVisibility(View.VISIBLE);
        ivrank5pic.setVisibility(View.VISIBLE);
        ivrank4pic.setVisibility(View.VISIBLE);
        ivrank3pic.setVisibility(View.VISIBLE);
        ivrank2pic.setVisibility(View.VISIBLE);
        ivrank1pic.setVisibility(View.VISIBLE);
    }

    public void profilepicchanger(int intprofilepic,ImageView profilepic){

        if(intprofilepic == 1){
            profilepic.setImageResource(R.mipmap.kermit_profilepic1);
        }
        if(intprofilepic == 2){
            profilepic.setImageResource(R.mipmap.kermit_profilepic2);
        }
        if(intprofilepic == 3){
            profilepic.setImageResource(R.mipmap.kermit_profilepic3);
        }
        if(intprofilepic == 4){
            profilepic.setImageResource(R.mipmap.kermit_profilepic4);
        }
        if(intprofilepic == 5){
            profilepic.setImageResource(R.mipmap.kermit_profilepic5);
        }
        if(intprofilepic == 6){
            profilepic.setImageResource(R.mipmap.kermit_profilepic6);
        }
        if(intprofilepic == 7){
            profilepic.setImageResource(R.mipmap.kermit_profilepic7);
        }
        if(intprofilepic == 8){
            profilepic.setImageResource(R.mipmap.kermit_profilepic8);
        }

    }

    private static void printMap(Map<String, Integer> map) {
        map.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order){ //true - small to big, false - big to small
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    public void onClick1(View view){
        if(view==ivnextpage){
            if(page!=totalpages) {
                page++;
                tvrank1.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank2.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank3.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank4.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank5.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank6.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank7.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank8.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank9.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank10.setBackgroundColor(Color.parseColor("#00000000"));
                if(page==totalpages){
                    ivnextpage.setVisibility(View.INVISIBLE);
                }
                i=1;
                makeallvisible();
                ShowRanks();
                ivlastpage.setVisibility(View.VISIBLE);
            }
        }
        if(view==ivlastpage){
            if(page!=1) {
                page--;
                tvrank1.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank2.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank3.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank4.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank5.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank6.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank7.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank8.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank9.setBackgroundColor(Color.parseColor("#00000000"));
                tvrank10.setBackgroundColor(Color.parseColor("#00000000"));
                i=1;
                if(page==1){
                    ivlastpage.setVisibility(View.INVISIBLE);
                }
                makeallvisible();
                ShowRanks();
                ivnextpage.setVisibility(View.VISIBLE);
            }
        }

        if(view==btnnormal){
            startActivity(new Intent(this, lb_totalscore.class));
        }
        if(view == btnlvl1){
            startActivity(new Intent(this, lb_lvl1.class));
        }
        if(view==btnlvl2){
            startActivity(new Intent(this, lb_lvl2.class));
        }
        if(view==btnlvl3){
            startActivity(new Intent(this, lb_lvl3.class));
        }
        if(view==btnlvl4){
            startActivity(new Intent(this, lb_lvl4.class));
        }

        if(view==ivback){
            Intent i = new Intent(getApplicationContext(), menu.class);
            startActivity(i);
        }
    }
}
