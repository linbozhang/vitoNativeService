package com.vito.vitonativeservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.network.DownloadProgressListener;
import com.android.network.FileDownloader;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.vito.websocketclient.LogicClient;
import com.vito.websocketclient.LogicClientKey;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by zlb on 12/11/2017.
 */

public class ServiceOne extends Service {
    public final static String TAG="ServiceOne";
    public LogicClient logicClient;
    public static Context context;

    public String configFileName="/sdcard/vitoresources/ServiceConfig.txt";
    public static String deviceFileName="/sdcard/vitoresources/DeviceBind.txt";

    public static String readSDFile(String fileName) throws IOException {

        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte [] buffer = new byte[length];
        fis.read(buffer);
        String res=new String(buffer, "UTF-8");
        fis.close();
        return res;
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        Log.e(TAG,"onStartCommand");
        thread.start();
        threadMain.start();
        context=this;
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);

        // Enabling database for resume support even after the application is killed:
       /* PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);*/

// Setting timeout globally for the download network requests:
       /* PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30_000)
                .setConnectTimeout(30_000)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);*/
        return START_STICKY;
    }


    long trick=0;
    Thread threadMain = new Thread(new Runnable(){
        @Override
        public void run(){
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    trick++;
                    if(logicClient==null)
                    {
                        try{
                            String msgBody= readSDFile(configFileName);
                            String msgBody2=readSDFile(deviceFileName);
                            JSONObject object = new JSONObject(msgBody);
                            //JSONObject object2=new JSONObject(msgBody2);
                            String serverURL=object.getString("serverurl");
                            logicClient=new LogicClient();
                            logicClient.ConnectServer(context,serverURL,msgBody2);
                        }catch(Exception e){
                            Log.e(TAG,e.getMessage());
                        }

                    }else {
                        if(logicClient.isConnectiong()) {

                        }else {
                            if(logicClient.isSocketOpen()) {
                                if(trick%50==0)
                                {
                                    logicClient.SendMessage(LogicClientKey.MsgType_Heart,LogicClientKey.MsgBody_Heart,false);
                                }
                            }else {
                                //reconnect to server
                                if(trick%100==0)
                                {
                                    try {
                                        logicClient.Close();
                                        logicClient=new LogicClient();
                                        String msgBody= readSDFile(configFileName);
                                        Log.i(TAG,msgBody);
                                        String msgBody2=readSDFile(deviceFileName);
                                        JSONObject object = new JSONObject(msgBody);
                                        //JSONObject object2=new JSONObject(msgBody2);
                                        String serverURL=object.getString("serverurl");
                                        logicClient.ConnectServer(context,serverURL,msgBody2);
                                    }catch (Exception e){
                                        Log.e(TAG,e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                    //Log.e(TAG,"ServiceOne Run:"+System.currentTimeMillis());

                }
            };
            timer.schedule(task,0,100);
        }
    });

    Thread thread = new Thread(new Runnable(){
        @Override
        public void run(){
            Timer timer = new Timer();
             TimerTask task = new TimerTask() {
                 @Override
                 public void run() {
                    //logicClient.SendMessage(-1,"heart");
                     //Log.e(TAG,"ServiceOne Run:"+System.currentTimeMillis());
                    boolean b = MainActivity.isServiceWorked(ServiceOne.this,"com.vito.vitonativeservice.ServiceTwo");
                     if(!b){
                         Intent service = new Intent(ServiceOne.this,ServiceTwo.class);
                         startService(service);
                         Log.e(TAG,"Start ServiceTwo");
                     }
                 }
             };
            timer.schedule(task,0,1000);
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






































