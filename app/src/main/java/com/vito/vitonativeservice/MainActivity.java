package com.vito.vitonativeservice;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( !isServiceWorked(this,"com.vito.vitonativeservice.ServiceOne"))
        {
            Intent serviceOne = new Intent(MainActivity.this,ServiceOne.class);
            //serviceOne.setClass(MainActivity.this,ServiceOne.class);
            startService(serviceOne);
        }

        if(!isServiceWorked(this,"com.vito.vitonativeservice.ServiceTwo"))
        {
            Intent serviceTwo = new Intent();
            serviceTwo.setClass(MainActivity.this,ServiceTwo.class);
            startService(serviceTwo);
        }


        this.finish();
    }
    @Override
    protected void onDestroy()
    {
        Log.e("MainActivity","OnDestroy");
        super.onDestroy();
    }

    public static boolean isServiceWorked (Context context ,String serviceName)
    {
        ActivityManager myManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>)  myManager.getRunningServices(Integer.MAX_VALUE);
        for(int i=0;i<runningService.size();i++){
            if(runningService.get(i).service.getClassName().toString().equals(serviceName)){
                return true;
            }
        }
        return false;
    }







}
