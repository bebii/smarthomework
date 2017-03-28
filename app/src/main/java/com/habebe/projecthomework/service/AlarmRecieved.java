package com.habebe.projecthomework.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmRecieved extends BroadcastReceiver {
    public static final String ACTION_REFRESH_ACTION_REFRESH_SERVICE_ALARM =
            "com.habebe.projecthomework.ACTION_REFRESH_SERVICE_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntent = new Intent(context, UpdateService.class);
        context.startService(startIntent);
    }

}
