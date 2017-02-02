package com.example.somoto.aws_appmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class StartFirebaseAtBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(FirebaseBackgroundService.class.getName()));
    }
}