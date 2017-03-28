package com.habebe.projecthomework.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.activity.MainActivity;
import com.habebe.projecthomework.activity.SubjectActivity;
import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.manager.LoginManager;

import static android.R.attr.content;
import static android.R.attr.visibility;
import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION_NOTIFICATION = "com.habebe.projecthomework.ACTION_NOTIFICATION";
    public static final String ACTION_NOTIFICATION_DONOTHOMEWORK = "com.habebe.projecthomework.ACTION_NOTIFICATION_DONOTHOMEWORK";
    public static boolean NOTIFICATION_ACTION = false;  // The request code

    public MyReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            User user = LoginManager.getInstance().getUser(context);
            if (user.getUserID() == null) user.setUserID("");
            if (!user.getUserID().equalsIgnoreCase("")) {
                Intent startIntent = new Intent(context, MyService.class);
                context.startService(startIntent);
            }
        } else if (intent.getAction().equalsIgnoreCase(ACTION_NOTIFICATION)) {
            showNotificationNewExercise(context);
        } else if (intent.getAction().equalsIgnoreCase(ACTION_NOTIFICATION_DONOTHOMEWORK)) {
            showNotificationDonotHomework(context);
        }
    }

    public void showNotificationNewExercise(Context context) {

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, SubjectActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        MyReceiver.NOTIFICATION_ACTION = true;

        Notification notification = new NotificationCompat.Builder(context)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getText(R.string.newexercise))
                .setSmallIcon(R.drawable.ic_work_white_24dp)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVisibility(visibility)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showNotificationDonotHomework(Context context) {

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(context)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle(context.getString(R.string.warning))
                .setContentText(context.getText(R.string.noti_donot_homework))
                .setSmallIcon(R.drawable.ic_work_white_24dp)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVisibility(visibility)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
