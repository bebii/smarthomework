package com.habebe.projecthomework.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class MyService extends Service {
    public static MyService myService;
    //public UpdateTaskApprove updateTaskApprove;
    public Handler handlerTask;
    public static final String Actionapprove = "action_approve";
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(getApplication(), UpdateService.class));
        //updateTaskApprove = new UpdateTaskApprove(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(myService == null){
            this.myService = this;
            //handlerTask.post(updateTaskApprove);
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myService = null;
    }

}
