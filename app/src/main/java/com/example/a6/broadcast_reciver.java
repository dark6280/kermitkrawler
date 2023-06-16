package com.example.a6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;

public class broadcast_reciver extends BroadcastReceiver {

    private TextView batteryTextView;

    public broadcast_reciver(TextView batteryTextView) {
        this.batteryTextView = batteryTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        batteryTextView.setText(batteryLevel + "%");
    }

}
