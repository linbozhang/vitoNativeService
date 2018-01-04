/*
package com.vito.asyncsocket;

*/
/**
 * Created by zlb on 12/22/2017.
 *//*


public class ProtocolKey {

    public enum ProtocolFlag
    {
        None ,
        SQL , //SQL查询协议
        Upload, //上传协议
        Download , //下载协议
        RemoteStream, //远程文件流协议
        Throughput, //吞吐量测试协议
        Control ,
        LogOutput,
    }

    public enum RemoteStreamSocketCommand
    {
        None,
        FileExists,
        OpenFile,
        SetSize,
        GetSize,
        SetPosition ,
        GetPosition,
        Read ,
        Write ,
        Seek ,
        CloseFile ,
    }

    public enum RemoteStreamMode
    {
        Read ,
        ReadWrite,
    }

    public enum ControlSocketCommand
    {
        None,
        Login ,
        Active ,
        GetClients ,
    }

    public enum UploadSocketCommand
    {
        None ,
        Login ,
        Active ,
        Dir,
        CreateDir ,
        DeleteDir ,
        FileList ,
        DeleteFile ,
        Upload ,
        Data ,
        Eof ,
    }

    public enum DownloadSocketCommand
    {
        None ,
        Login ,
        Active ,
        Dir ,
        FileList ,
        Download ,
    }

    public enum SQLSocketCommand
    {
        None ,
        Login ,
        Active ,
        SQLOpen ,
        SQLExec ,
        BeginTrans ,
        CommitTrans ,
        RollbackTrans ,
    }

    public enum ThroughputSocketCommand
    {
        None ,
        CyclePacket ,
    }



    //ProtoclConst
    public static int InitBufferSize=1024*4;
    public static int ReceiveBufferSize=1024*4;
    public static int SocketTimeOutMS=60*1000;

    //ProtocolKey
    public static String Request="Request";
    public static String Response="Response";
    public static String LeftBrackets="[";
    public static String RightBrackets="]";
    public static String ReturnWrap="\r\n";
    public static String EqualSign="=";
    public static String Command="Command";
    public static String Code="Code";
    public static String Message="Mesasge";
    public static String UserName="UserName";
    public static String Password="Password";
    public static String FileName="FileName";
    public static String Item="Item";
    public static String ParentDir="ParentDir";
    public static String DirName="DirName";
    public static char TextSeperator=(char)1;
    public static String FileSize="FileSize";
    public static String PacketSize="PacketSize";

    public static String FileExists="FileExists";
    public static String OpenFile="OpenFile";
    public static String SetSize="SetSize";
    public static String GetSize="GetSize";
    public static String SetPosition = "SetPosition";
    public static String GetPosition = "GetPosition";
    public static String Read = "Read";
    public static String Write = "Write";
    public static String Seek = "Seek";
    public static String CloseFile = "CloseFile";
    public static String Mode = "Mode";
    public static String Size = "Size";
    public static String Position = "Position";
    public static String Count = "Count";
    public static String Offset = "Offset";
    public static String SeekOrigin = "SeekOrigin";
    public static String Login = "Login";
    public static String Active = "Active";
    public static String GetClients = "GetClients";
    public static String Dir = "Dir";
    public static String CreateDir = "CreateDir";
    public static String DeleteDir = "DeleteDir";
    public static String FileList = "FileList";
    public static String DeleteFile = "DeleteFile";
    public static String Upload = "Upload";
    public static String Data = "Data";
    public static String Eof = "Eof";
    public static String Download = "Download";
    public static String SendFile = "SendFile";
    public static String CyclePacket = "CyclePacket";

    //Protocol Code
    public static int Success = 0x00000000;
    public static int NotExistCommand = Success + 0x01;
    public static int PacketLengthError = Success + 0x02;
    public static int PacketFormatError = Success + 0x03;
    public static int UnknowError = Success + 0x04;
    public static int CommandNoCompleted = Success + 0x05;
    public static int ParameterError = Success + 0x06;
    public static int UserOrPasswordError = Success + 0x07;
    public static int UserHasLogined = Success + 0x08;
    public static int FileNotExist = Success + 0x09;
    public static int NotOpenFile = Success + 0x0A;
    public static int FileIsInUse = Success + 0x0B;

    public static int DirNotExist = 0x02000001;
    public static int CreateDirError = 0x02000002;
    public static int DeleteDirError = 0x02000003;
    public static int DeleteFileFailed = 0x02000007;
    public static int FileSizeError = 0x02000008;

    public static String GetErrorCodeString(int errorCode){
        String errorString=null;
        if(errorCode==NotExistCommand)
            errorString="Not Exist Command";
        return errorString;
    }




















}

*/
