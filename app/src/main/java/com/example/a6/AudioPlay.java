package com.example.a6;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AudioPlay extends Service {

    public static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    //return to false
    public static boolean mute= false;
    public static float sound=1;
    public static boolean isplayingAudio=false;
    public static boolean isAudioPaused=false;


    public static void playAudio(Context c, int id){
        mediaPlayer = MediaPlayer.create(c,id);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        if(!mediaPlayer.isPlaying())
        {
            isplayingAudio=true;
            mediaPlayer.start();
            //return to sound!
            setsound(sound);
        }
    }
    public static void stopAudio(){
        isplayingAudio=false;
        mediaPlayer.stop();
    }

    public static void setlooping(){
        mediaPlayer.setLooping(true);
    }

    public static void setsound(float sound1) {
        sound = sound1;
        mediaPlayer.setVolume(sound1,sound1);
    }

    public static void setmute(boolean mute1){
        mute = mute1;
    }

    public static boolean isMute() {
        return mute;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
