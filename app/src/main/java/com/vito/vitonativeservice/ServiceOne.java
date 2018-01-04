package com.vito.vitonativeservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.vito.com.vito.websocketclient.LogicClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zlb on 12/11/2017.
 */

public class ServiceOne extends Service {
    public final static String TAG="ServiceOne";
    public LogicClient logicClient;
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        Log.e(TAG,"onStartCommand");
        thread.start();
        logicClient=new LogicClient();
        logicClient.ConnectServer();
        /*
        Intent notificationIntent=new Intent(this,MainActivity.class);
        Notification notification = new Notification(R.mipmap.ic_launcher,getText(R.string.app_name),System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("Title");
        builder.setContentText("Content text");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("This is subtext...");
        //builder.setNumber(100);

        notification=builder .build();//builder.getNotification();
       //NotificationManager  manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //manager.notify(11,notification);
        //notification.setLatestEventInfo(this,getText(R.string.app_name),getText(R.string.app_name),pendingIntent);
        startForeground( 1235,notification);
        */
        return START_STICKY;
    }
    Thread thread = new Thread(new Runnable(){
        @Override
        public void run(){
            Timer timer = new Timer();
             TimerTask task = new TimerTask() {
                 @Override
                 public void run() {
                    logicClient.SendMessage(-1,"heart");
                     Log.e(TAG,"ServiceOne Run:"+System.currentTimeMillis());
                    boolean b = MainActivity.isServiceWorked(ServiceOne.this,"com.vito.vitonativeservice.ServiceTwo");
                     if(!b){
                         Intent service = new Intent(ServiceOne.this,ServiceTwo.class);
                         startService(service);
                         Log.e(TAG,"Start ServiceTwo");
                     }
                 }
             };
            timer.schedule(task,0,5000);
        }
    });

    @Override
    public void onDestroy() {
        Log.e(TAG,"Destroy Service One");
        Intent service = new Intent(ServiceOne.this,ServiceTwo.class);
        startService(service);
        Log.e(TAG,"Start ServiceTwo");
        // The service is no longer used and is being destroyed
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

}






































