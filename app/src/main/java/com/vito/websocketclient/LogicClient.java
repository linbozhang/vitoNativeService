package com.vito.websocketclient;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.android.network.FileDownloader;
import com.android.network.DownloadProgressListener;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.vito.util.AutoInstall;
import com.vito.util.Decompress;
import com.vito.vitonativeservice.ServiceOne;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.security.MessageDigest;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by zlb on 1/4/2018.
 */

public class LogicClient {
    private WebSocketClient client;
    private Draft selectDraft;
    private Context context;
    public boolean isConnectiong() {
        return isConnectiong;
    }
    public static String TAG="LogicClient";
    private static LogicClient current;
    private boolean isConnectiong=false;
    public boolean isSocketOpen()
    {
        return client.isOpen();
    }

    public LogicClient(){
        current=this;
    }
    public void Close()
    {
        client.close();
    }
    public void SendMessage(int type, String msg,boolean isType)
    {
        if(client!=null&&client.isOpen())
        {
            if(isType){
                String result="{\"t\":"+type+",\"b\":"+msg+"}";
                client.send("{\"t\":"+type+",\"b\":"+msg+"}");
                Log.i(TAG,"sendMsg:"+result);
            }else{
                client.send("{\"t\":"+type+",\"b\":"+"\""+msg+"\"}");
            }
        }
    }
    public void SendMessage(int type,JSONObject msg){
        try{
            JSONObject r=new JSONObject();
            r.put("t",type);
            r.put("b",msg.toString());
            client.send( r.toString());
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }


    }
    private void ParseMsg(int msgType,String msgBody)
    {
        switch (msgType)
        {
            case  LogicClientKey.MsgType_Heart:
                //Log.i("TAG",msgBody);
                break;
            case LogicClientKey.MsgType_LaunchApp:
                Log.i(TAG,"LaunchApp:"+msgBody);
                PackageManager pmi = context.getPackageManager();
                Intent intent = null;

                intent = pmi.getLaunchIntentForPackage(msgBody);
                if (intent != null){
                 context.startActivity(intent);
                }
                break;
            case LogicClientKey.MsgType_InstallApp:
                InstallAPK(context,msgBody);
                break;
            case  LogicClientKey.MsgType_WriteFile:
                try{
                    Log.i(TAG,"write file:"+msgBody);
                    JSONObject object = new JSONObject(msgBody);
                    String path= object.getString("p");
                    String remoteId=object.getString("id");
                    Log.i(TAG,"remoteId:"+remoteId+" localId:"+bindId);
                    {
                        String content=object.getString("c");
                        File file = new File(path);
                        String jName= file.getName();
                        String jpath= file.getParent();
                        byte[] buffer=content.getBytes();
                        Log.i(TAG,"path:"+jpath+"name:"+jName);
                        writeFileToSDCard(buffer,jpath,jName,false,false);
                    }
                }catch (Exception e){
                    Log.i(TAG,e.getMessage());
                }
                break;
            case LogicClientKey.MsgType_GetFile:
            {
                try{
                    JSONObject object = new JSONObject(msgBody);
                    String path= object.getString("p");
                    Log.i(TAG,"request file:"+path);
                    String result=ServiceOne.readSDFile(path);
                    JSONObject js=new JSONObject();
                    js.put("id",bindId);
                    js.put("p",path);
                    js.put("c",result);
                    SendMessage(LogicClientKey.MsgType_ReadFile,js);

                }catch (Exception e){
                    Log.i(TAG,e.getMessage());
                }
            }
                break;
            case LogicClientKey.MsgType_SingleDownload:
            case LogicClientKey.MsgType_DownloadFile:
            {
                try{
                    JSONObject object = new JSONObject(msgBody);
                    String downloadURL=object.getString("url");
                    String remotemd5=object.getString("md5");
                    String appid=object.getString("appid");
                    String versionCode=object.getString("versioncode");
                    int fileType=object.getInt("file_type");
                    String boundleId=object.getString("boundle_id");
                    String targetPath=object.getString("target_path");
                    String filePath=Environment.getExternalStorageDirectory().getPath()+"/vitoresources/update/"+appid +"/";
                    File dir= new File( filePath);
                    if(!dir.exists())
                    {
                        dir.mkdirs();
                    }

                    String fileName="";
                    if(fileType==1){
                        fileName=appid+"_"+versionCode+".apk";
                    }else if(fileType==2) {
                        fileName=appid+"_"+versionCode+".zip";
                    }
                    File localfile= new File( filePath+fileName);
                    String md5="-1";
                    int theversionCode=-1;
                    if(fileType==1) {
                        try {
                            PackageInfo pinfo = context.getPackageManager().getPackageInfo(boundleId, 0);
                            theversionCode = pinfo.versionCode;
                            Log.i(TAG, "request" + boundleId + "exist" + theversionCode + ":" + versionCode);
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.i(TAG, "request" + boundleId + "not exist");
                        }
                    }
                    boolean appexist=false;
                    if((theversionCode!=-1)&& (theversionCode==Integer.parseInt( versionCode))){
                        appexist=true;
                        Log.i(TAG,"request"+boundleId+"exist and version ok");
                    }
                    if(!appexist&&localfile.exists())
                    {
                        md5= getMD5(localfile);
                    }
                    Log.i(TAG,"remoteMd5:"+remotemd5+" localMd5:"+md5);
                    if(!appexist&&!md5.equals(remotemd5))
                    {
                        Log.i(TAG,"downloadURL:"+downloadURL+" target:"+filePath+" filename:"+fileName);
                        StartDownload(downloadURL,filePath,fileName,fileType,appid,targetPath);
                    }else{
                        Log.i(TAG,"本地文件或者APP已存在，不需要下载");
                        if(fileType==1)
                        {
                            if(!appexist){
                                Log.i(TAG,"APP文件存在但没有安装");
                                InstallAPK(context,filePath+fileName);
                            }
                            {
                                Log.i(TAG, "APP文件已存在");
                                try {
                                    JSONObject js = new JSONObject();
                                    js.put("id", bindId);
                                    js.put("p", 1);
                                    js.put("appid", appid);
                                    SendMessage(LogicClientKey.MsgType_RequestOtherFile, js);
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }else if(fileType==2) {
                            SendMessage(LogicClientKey.MsgType_UnZipResources,"",false);
                            new Decompress(context,localfile).unzip(targetPath,this);
                            //Unzip(filePath+fileName,targetPath);
                        }
                    }
                }catch (Exception e){
                    Log.i(TAG,e.getMessage());
                }
            }

                break;
            default:
                Log.e(TAG,"un catched message:"+msgBody+"withtype:"+msgType);
                break;
        }
    }
/* 此方法为android程序写入sd文件文件，用到了android-annotation的支持库@
 *
  * @param buffer   写入文件的内容
 * @param folder   保存文件的文件夹名称,如log；可为null，默认保存在sd卡根目录
 * @param fileName 文件名称，默认app_log.txt
 * @param append   是否追加写入，true为追加写入，false为重写文件
 * @param autoLine 针对追加模式，true为增加时换行，false为增加时不换行
 */
    public synchronized static void writeFileToSDCard(@NonNull final byte[] buffer, @Nullable final String folder,
                                                      @Nullable final String fileName, final boolean append, final boolean autoLine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean sdCardExist = Environment.getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED);
                String folderPath = "";
                if (sdCardExist) {
                    //TextUtils为android自带的帮助类
                    if (TextUtils.isEmpty(folder)) {
                        //如果folder为空，则直接保存在sd卡的根目录
                        folderPath = Environment.getExternalStorageDirectory()
                                + File.separator;
                    } else {
                        folderPath =folder+ File.separator;;//  Environment.getExternalStorageDirectory()+ File.separator + folder + File.separator;
                    }
                } else {
                    return;
                }

                File fileDir = new File(folderPath);
                if (!fileDir.exists()) {
                    if (!fileDir.mkdirs()) {
                        return;
                    }
                }
                File file;
                //判断文件名是否为空
                if (TextUtils.isEmpty(fileName)) {
                    file = new File(folderPath + "app_log.txt");
                } else {
                    file = new File(folderPath + fileName);
                }
                RandomAccessFile raf = null;
                FileOutputStream out = null;
                try {
                    if (append) {
                        //如果为追加则在原来的基础上继续写文件
                        raf = new RandomAccessFile(file, "rw");
                        raf.seek(file.length());
                        raf.write(buffer);
                        if (autoLine) {
                            raf.write("\n".getBytes());
                        }
                    } else {
                        //重写文件，覆盖掉原来的数据
                        out = new FileOutputStream(file);
                        out.write(buffer);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (raf != null) {
                            raf.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public static void InstallAPK(Context context, String name)
    {
        Log.i(TAG,"InstallApp:"+name);
        //String path="sdcard/vitoresources/update/"+name;
        AutoInstall.setUrl(name);
        AutoInstall.install(context);
        //Log.i(TAG,"install"+ name+" result:"+ );
    }
    /**
     * 获取一个文件的md5值(可处理大文件)
     * @return md5 value
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return bytesToHexString(MD5.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }



    int downloadPersent=0;
    private void StartDownload(String downloadURL, final String dirPath, final String fileName, final int fileType, final String appid,final String targetPath)
    {
        downloadPersent=0;
        File dir= new File( dirPath);
        if(!dir.exists())
        {
            dir.mkdirs();
            Log.i(TAG,"文件夹不存在");
        }else{
            Log.i(TAG,"文件夹已存在");
        }
        final int downloadId= PRDownloader.download(downloadURL,dirPath,fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Log.i(TAG,"download:"+fileName+"onStartOrResume");
                        SendMessage(LogicClientKey.MsgType_StartDownload,"true",false);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        Log.i(TAG,"download:"+fileName+"onPause");
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Log.i(TAG,"download:"+fileName+"onCancel");
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        int curdownloadPersent=(int)((progress.currentBytes*1.0/progress.totalBytes)*100);

                        if(curdownloadPersent>downloadPersent)
                        {
                            downloadPersent=curdownloadPersent;
                            Log.i(TAG,""+downloadPersent);
                            SendMessage(LogicClientKey.MsgType_DownloadPerscent,""+downloadPersent,false);
                        }

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Log.i(TAG,"download:"+fileName+"download success");

                        if(fileType==1)
                        {
                            InstallAPK(context,dirPath+fileName);
                            try{
                                JSONObject js=new JSONObject();
                                js.put("id",bindId);
                                js.put("p",1);
                                js.put("appid",appid);
                                SendMessage(LogicClientKey.MsgType_RequestOtherFile,js);
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                            }
                        } else if(fileType==2) {
                            SendMessage(LogicClientKey.MsgType_UnZipResources,"",false);
                            new Decompress(context,new File(dirPath+fileName)).unzip(targetPath,current);
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        Log.i(TAG,"download:"+fileName+"  Error,server err: "+error.isServerError()+" conn err:"+error.isConnectionError());
                        SendMessage(LogicClientKey.MsgType_DownloadOver,"false",false);
                    }
                });
    }
    private String bindId="";
    public void ConnectServer(Context context, String url, final String boundleId)
    {
        this.context=context;
        isConnectiong=true;
        try{
            client=new WebSocketClient(new URI("ws://"+url)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    isConnectiong=false;
                     bindId =boundleId.trim();
                    bindId=bindId.replaceAll("\n","");

                    SendMessage(LogicClientKey.MsgType_ConnectToServer,"{\\\"platform\\\":"+"1 ,"+"\\\"usertype\\\":"+"1 ,"+"\\\"id\\\":"+"\\\""+bindId+"\\\"}",false);
                    Log.i("TAG","OnOpen");
                }

                @Override
                public void onMessage(String message) {

                    //Log.i("TAG","receive messgae :"+message);
                    try{
                        JSONObject object = new JSONObject(message);
                        int msgType=object.getInt("t");
                        String msgBody=object.getString("b");
                        ParseMsg(msgType,msgBody);
                    }catch (Exception e)
                    {
                        Log.e("TAG","parse message error:"+e.getMessage());
                    }


                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    isConnectiong=false;
                    Log.i("TAG","OnClose:"+code+" reason "+reason+" remote:"+remote);
                }

                @Override
                public void onError(Exception ex) {
                    //isConnectiong=false;
                    Log.i("TAG","OnError:"+ex.getMessage());
                }
            };
            client.connect();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }

    }
}
