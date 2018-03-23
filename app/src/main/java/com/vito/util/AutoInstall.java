package com.vito.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zlb on 1/8/2018.
 */

public class AutoInstall {
    private static String mUrl;
    private static Context mContext;
    private static ExecutorService mInstaller = Executors.newFixedThreadPool(1);
    public static void setUrl(String url){
        mUrl=url;
    }
    public static String install(Context context){
        String result="";
       /* new Thread(new Runnable() {
            @Override
            public void run() {*/
                mInstaller.execute(new Runnable() {
                    public synchronized void run() {
                        String[] args = new String[]{"pm", "install", "-r", mUrl};
                        String result = "";
                        ProcessBuilder processBuilder = new ProcessBuilder(args);
                        Process process = null;
                        InputStream errIs = null;
                        InputStream inIs = null;

                        try {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            //int readx = true;
                            process = processBuilder.start();
                            errIs = process.getErrorStream();

                            int read;
                            while((read = errIs.read()) != -1) {
                                baos.write(read);
                            }

                            baos.write(10);
                            inIs = process.getInputStream();

                            while((read = inIs.read()) != -1) {
                                baos.write(read);
                            }

                            byte[] data = baos.toByteArray();
                            result = new String(data);
                            Log.i("PicoVRPowerManger", "silentInstall result is : " + result);
                        } catch (IOException var20) {
                            var20.printStackTrace();
                        } catch (Exception var21) {
                            var21.printStackTrace();
                        } finally {
                            try {
                                if(errIs != null) {
                                    errIs.close();
                                }

                                if(inIs != null) {
                                    inIs.close();
                                }
                            } catch (IOException var19) {
                                var19.printStackTrace();
                            }

                            if(process != null) {
                                process.destroy();
                            }

                        }

                    }
                });
            //}
       /* }).start();*/


        return ""+result;

    }
    private static boolean returnResult(int value){
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }
}
