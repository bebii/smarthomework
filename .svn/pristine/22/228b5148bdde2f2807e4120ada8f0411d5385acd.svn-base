package com.habebe.projecthomework.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.UserManager;
import android.support.annotation.Nullable;

import com.habebe.projecthomework.activity.LoginActivity;
import com.habebe.projecthomework.activity.MainActivity;
import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.manager.NetworkConnection;
import com.habebe.projecthomework.util.Position;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateService extends IntentService {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private boolean isDownload = false;

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (NetworkConnection.isAvailable(getApplicationContext())) {
            try {
                if (intent != null) {
                    SharedPreferences pref = getSharedPreferences("login", 1);
                    if (pref.getBoolean("login_flag", false)) {
                        if (MyService.myService != null) {

                            User user = LoginManager.getInstance().getUser(getApplicationContext());

                            if (user.getPosition().equalsIgnoreCase(Position.STATUS_STUDENT)) {
                                new WebService().getDataForStudent(getApplicationContext());

                            } else if (user.getPosition().equalsIgnoreCase(Position.STATUS_TEACHER)) {
                                new WebService().getDataForTeacher(getApplicationContext());

                            }
                            if (user.getPosition().equalsIgnoreCase(Position.STATUS_PARENT)) {
                                new WebService().getDataForParent(getApplicationContext());

                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.toString();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        String ALARM_ACTION;
        ALARM_ACTION =
                AlarmRecieved.ACTION_REFRESH_ACTION_REFRESH_SERVICE_ALARM;
        Intent intentToFire = new Intent(ALARM_ACTION);
        alarmIntent =
                PendingIntent.getBroadcast(this, 0, intentToFire, 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int updateFrequency = 3;

        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long timeToRefresh = SystemClock.elapsedRealtime() +
                updateFrequency * 60 * 1000;
        alarmManager.setInexactRepeating(alarmType, timeToRefresh,
                updateFrequency * 60 * 1000, alarmIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
