package com.vito.websocketclient;

/**
 * Created by zlb on 1/4/2018.
 */

public class LogicClientKey {
    public static final   int MsgType_Heart=-1;
    public static final String MsgBody_Heart="HEART";
    public static final int MsgType_LaunchApp=1;
    public static final int MsgType_InstallApp=2;
    public static final int MsgType_DownloadFile=3;

    public static final int MsgType_StartDownload=4;
    public static final int MsgType_DownloadPerscent=5;
    public static final int MsgType_DownloadOver=6;
    public static final int MsgType_RequestOtherFile=7;
    public static final int MsgType_SingleDownload=8;
    public static final int MsgType_UnZipResources=9;
    public static final int MsgType_UnZipResourcesOver=10;
    public static final int MsgType_ConnectToServer=1001;
    public static final int MsgType_GetUserMsg=1002;
    public static final int MsgType_GetFile=1003;
    public static final int MsgType_WriteFile=1004;
    public static final int MsgType_UserMsg=2001;
    public static final int MsgType_ReadFile=2003;
}
