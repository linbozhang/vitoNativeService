package com.vito.vitonativeservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zlb on 12/11/2017.
 */

public class ServiceTwo extends Service {
    public final static String TAG="ServiceTwo";
    @Override
    public int onStartCommand(Intent intent, int flags ,int startId){
        Log.e(TAG,"onStartCommand");
        thread.start();
        return START_REDELIVER_INTENT;
    }
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Timer timer =new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    //Log.e(TAG,"ServerTwo Run:"+System.currentTimeMillis());
                    boolean b = MainActivity.isServiceWorked(ServiceTwo.this,"com.vito.vitonativeservice.ServiceOne");
                    if(!b){
                        Intent service = new Intent(ServiceTwo.this,ServiceOne.class);
                        startService(service);
                    }
                }
            };
            timer.schedule(task,0,1000);
        }
    });
    @Override
    public void onDestroy() {
        Log.e(TAG,"Destroy Service Two");
        Intent service = new Intent(ServiceTwo.this,ServiceOne.class);
        startService(service);
        Log.e(TAG,"Start ServiceOne");
        // The service is no longer used and is being destroyed
    }
    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }
}

































