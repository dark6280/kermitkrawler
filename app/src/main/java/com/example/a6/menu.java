package com.example.a6;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

public class menu extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    boolean doubleBackToExitPressedOnce = false;
    ImageView settings,iv_editname;
    Button buttonok,sign_up_accept,login_accept,editname_accept,btn_speedrunmode;
    RadioGroup soundcontrol_rg;
    RadioButton radioButtonmute,radioButtonunmute;
    float volume=1;
    String username,email;
    String currentuserID;
    int teamoney,intprofilepic;
    TextView tv_teamoney;
    Query checkuser;
    boolean updated= false;
    TextView username_tv;
    Dialog dialog;
    SeekBar seekbarvolume;




    TextView batteryTextView;
    broadcast_reciver batteryReceiver;





    FirebaseDatabase db;
    DatabaseReference reference;
    EditText signup_email, signup_password,Signup_username;
    EditText login_email, login_password;
    EditText tv_editname;
    FirebaseAuth auth;




   Button shop,start,leaderboard;
   ImageView kermits_eye,profilepic;
     Menu menu1;




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


        setContentView(R.layout.menu);
        shop = findViewById(R.id.btn_shop);
        start = findViewById(R.id.btn_start);
        leaderboard = findViewById(R.id.btn_leaderboard);
        kermits_eye = findViewById(R.id.kermits_eye);
        profilepic = findViewById(R.id.profilepic);
        settings = findViewById(R.id.Settings);
        tv_teamoney = findViewById(R.id.tv_teamoney);
        username_tv = findViewById(R.id.username_edittext);
        iv_editname =  findViewById(R.id.iv_editname);
        btn_speedrunmode = findViewById(R.id.btn_speedrun);


        batteryTextView = findViewById(R.id.batteryTextView);
        batteryReceiver = new broadcast_reciver(batteryTextView);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);


        // authentication
        auth = FirebaseAuth.getInstance();

        //db
        db = FirebaseDatabase.getInstance();
        //reference
        reference = db.getReference("userData");

        if (!AudioPlay.isplayingAudio) {
            AudioPlay.playAudio(this, R.raw.menusong);
            AudioPlay.setlooping();
        }
        

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
             currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


             if(!updated){
                 checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
                 checkuser.addValueEventListener(new ValueEventListener() {
                     @Override

                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         if (snapshot.exists()) {
                             teamoney = snapshot.child(currentuserID).child("score").getValue(int.class);
                             username = snapshot.child(currentuserID).child("username").getValue(String.class);
                             tv_teamoney.setText("" + teamoney);
                             username_tv.setText(username);
                             updated = true;
                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {
                     }
                 });
             }








             profilepicchanger();

         }

    }




    @SuppressLint("SetTextI18n")
    public void onClick(View view)
    {
      if(view == start){
          AudioPlay.stopAudio();
         startActivity(new Intent(this, MainActivity.class));
          //startActivity(new Intent(this, elmo_boss_fight.class));
      }

      if(view == shop){
          startActivity(new Intent(this, shop.class));
      }

      if(view == kermits_eye){
          AudioPlay.stopAudio();
          startActivity(new Intent(this, kermit_rolled.class));
      }

      if(view  == settings){
         menu.this.openOptionsMenu(); // activity's onCreateOptionsMenu gets called
          menu1.performIdentifierAction(R.id.Settings, 0);
      }

      if(view == buttonok){
          AudioPlay.setsound((float) volume);
          if(radioButtonmute.isChecked()){
              AudioPlay.setmute(true);
              AudioPlay.setsound((float) 0);
          }
          if(radioButtonunmute.isChecked()){
              AudioPlay.setmute(false);
          }
      }

      if(view == leaderboard){
          startActivity(new Intent(this, lb_totalscore.class));
      }

      if(view == sign_up_accept) {
          username = Signup_username.getText().toString().trim();
          email = signup_email.getText().toString().trim();
          String password = signup_password.getText().toString().trim();

          if(TextUtils.isEmpty(username)){
              Signup_username.setError("username is required");
              return;
          }
          if(username.length() >18){
              Signup_username.setError("username can't be longer then 18 characters");
              return;
          }
          if(TextUtils.isEmpty(email)){
              signup_email.setError("email is required");
              return;
          }
          if(!TextUtils.isEmpty(email)){
              if (!email.toLowerCase().endsWith("@gmail.com")) {
                  login_email.setError("The email does not end with '@gmail.com'");
                  return;
              }
          }
          if(TextUtils.isEmpty(password)){
              signup_password.setError("password is required");
              return;
          }
          if(password.length() < 6){
              signup_password.setError("password is too short");
              return;
          }


          //the email has to be in the format of agag@jaja.com, otherwise it will not work
          auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      Toast.makeText(menu.this, "user created", Toast.LENGTH_SHORT).show();
                      //get user ID
                      currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                      users users = new users(5,5,username,email,1,currentuserID,false,false,false,false,false,false,false,false,0,0,0,0,0,0);
                      db = FirebaseDatabase.getInstance();
                      reference = db.getReference("users");
                      reference.child(currentuserID).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {

                          }

                      });
                      RestartActivity();
                  }
                  else {
                      Toast.makeText(menu.this, "email already in use", Toast.LENGTH_SHORT).show();
                  }
              }
          });




      }

      if(view == login_accept){

          String email = login_email.getText().toString().trim();
          String password = login_password.getText().toString().trim();

          if(TextUtils.isEmpty(email)){
              login_email.setError("email is required");
              return;
          }
          if(!TextUtils.isEmpty(email)){
              if (!email.toLowerCase().endsWith("@gmail.com")) {
                  login_email.setError("The email does not end with '@gmail.com'");
                  return;
              }
          }
          if(TextUtils.isEmpty(password)){
              login_password.setError("password is required");
              return;
          }
          if(password.length() < 6){
              login_password.setError("password is too short");
              return;
          }

          auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(menu.this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      Toast.makeText(menu.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                      RestartActivity();
                  }
                  else {
                      Toast.makeText(menu.this, "password or email is wrong", Toast.LENGTH_SHORT).show();
                  }
              }
          });



      }

      if(view== iv_editname){
          if(FirebaseAuth.getInstance().getCurrentUser() != null) {
              openEditNameDialog();
          }
          else{
              Toast.makeText(this, "You need to have a user to change your name", Toast.LENGTH_SHORT).show();
          }
      }

      if(view == editname_accept){



            String New_Username = tv_editname.getText().toString().trim();

            if(TextUtils.isEmpty(New_Username)){
                tv_editname.setError("are you stupid?");
                return;
            }
            if(New_Username.length() >18){
              tv_editname.setError("username can't be longer then 18 characters");
              return;
            }


              currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


              HashMap user = new HashMap();
              user.put("username", New_Username);
              reference = FirebaseDatabase.getInstance().getReference("users");
              reference.child(currentuserID).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                  }
              });
              dialog.dismiss();


        }

      if(view ==btn_speedrunmode){
          startActivity(new Intent(this, speedrun_mode.class));
      }

    }

    private void openSettingsDialog() {
        Dialog dialog = new Dialog(menu.this);
        dialog.setContentView(R.layout.settings_dialog);

        buttonok = dialog.findViewById(R.id.buttonok);
        soundcontrol_rg = dialog.findViewById(R.id.radiogroup);
        radioButtonmute = dialog.findViewById(R.id.radioButtonmute);
        radioButtonunmute =dialog.findViewById(R.id.radioButtonunmute);
        seekbarvolume = dialog.findViewById(R.id.seekBarvolume);

        int progress = (int) (volume * seekbarvolume.getMax());
        seekbarvolume.setProgress(progress);

        seekbarvolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 volume = (float) progress / 100.0f;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This method is called when the user starts dragging the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        if(soundcontrol_rg.getCheckedRadioButtonId() == -1)
        {
          if(AudioPlay.isMute()){
              radioButtonmute.setChecked(true);
          }
          else{
              radioButtonunmute.setChecked(true);
          }
        }


        dialog.show();
        dialog.getWindow().setLayout(1400, 1000);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void Popupmenu(View view) {
        PopupMenu popup  = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_in:
                openLogInDialog();
                return true;
            case R.id.Settings1:
                openSettingsDialog();
                return true;
            case R.id.sign_up:
                openSignUpDialog();
                return true;
            case R.id.log_out:
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    FirebaseAuth.getInstance().signOut();
                    RestartActivity();
                }
                return true;

        }
        return true;
    }

    private void openLogInDialog() {
        dialog = new Dialog(menu.this);
        dialog.setContentView(R.layout.login_dialog);
        login_email= dialog.findViewById(R.id.login_email);
        login_password = dialog.findViewById(R.id.login_password);
        login_accept = dialog.findViewById(R.id.login_accept);


        dialog.show();
        dialog.getWindow().setLayout(1400, 1000);
    }

    private void openSignUpDialog() {
        dialog = new Dialog(menu.this);
        dialog.setContentView(R.layout.signup_dialog);
        signup_email= dialog.findViewById(R.id.sign_up_email);
        signup_password = dialog.findViewById(R.id.signup_password);
        Signup_username = dialog.findViewById(R.id.sign_up_username);
        sign_up_accept = dialog.findViewById(R.id.sign_up_accept);



        dialog.show();
        dialog.getWindow().setLayout(1400, 1000);
    }

    private void openEditNameDialog() {
        dialog = new Dialog(menu.this);
        dialog.setContentView(R.layout.edit_name_dialog);
        tv_editname= dialog.findViewById(R.id.tv_editname);
        editname_accept= dialog.findViewById(R.id.editname_accept);


        dialog.show();
        dialog.getWindow().setLayout(1000, 500);
    }

    public void profilepicchanger(){


        currentuserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(currentuserID);
        checkuser.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    intprofilepic = snapshot.child(currentuserID).child("profilepic").getValue(int.class);

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void RestartActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the receiver when the activity is destroyed
        unregisterReceiver(batteryReceiver);
    }
}


