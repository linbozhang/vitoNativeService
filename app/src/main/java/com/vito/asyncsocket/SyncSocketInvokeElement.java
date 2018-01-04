/*
package com.vito.asyncsocket;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

*/
/**
 * Created by zlb on 1/3/2018.
 *//*


public class SyncSocketInvokeElement {
    protected Socket m_tcpClient;
    protected String m_Host;
    protected int m_port;
    protected ProtocolKey.ProtocolFlag m_protocolFlag;

    public int getSocketTimeOutMS() {
        try{
            return m_tcpClient.getSoTimeout();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
            return -1;
        }
    }

    public void setSocketTimeOutMS(int socketTimeOutMS) {
        try{
            m_tcpClient.setSoTimeout(socketTimeOutMS);
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }

    }
    protected int SocketTimeOutMS;

    public boolean isM_netByteOrder() {
        return m_netByteOrder;
    }

    public void setM_netByteOrder(boolean m_netByteOrder) {
        this.m_netByteOrder = m_netByteOrder;
    }

    private boolean m_netByteOrder;
    protected OutgoingDataAssembler m_outgoingDataAssembler;
    protected DynamicBufferManager m_recvBuffer;
    protected  IncomingDataParser m_incomingDataParser;
    protected DynamicBufferManager m_sendBuffer;

    public SyncSocketInvokeElement(){
        m_tcpClient=new Socket();
        m_protocolFlag= ProtocolKey.ProtocolFlag.None;
        setSocketTimeOutMS(ProtocolKey.SocketTimeOutMS);
        m_outgoingDataAssembler=new OutgoingDataAssembler();
        m_recvBuffer=new DynamicBufferManager(ProtocolKey.ReceiveBufferSize);
        m_incomingDataParser=new IncomingDataParser();
        m_sendBuffer=new DynamicBufferManager(ProtocolKey.ReceiveBufferSize);
    }
    public void Connect(String host,int port){
        SocketAddress remoteAddr=new InetSocketAddress(host,port);
        try{
            m_tcpClient.connect(remoteAddr);
            byte[] socketFlag=new byte[1];
            socketFlag[0]=(byte)m_protocolFlag.ordinal();
            DataOutputStream dOut= new DataOutputStream(m_tcpClient.getOutputStream());
            dOut.write(socketFlag);
            m_Host=host;
            m_port=port;
        }catch (Exception e) {
            Log.e("TAG",e.getMessage());
        }
    }
    public void Disconnect(){
        try{
            m_tcpClient.close();
        }catch(Exception e){
            Log.e("TAG",e.getMessage());
        }
        m_tcpClient=new Socket();
    }
    public void SendCommand(){
        String commandText=m_outgoingDataAssembler.GetProtocolText();
        try{
            byte[] bufferUTF8=commandText.getBytes("UTF-8");
            int totalLength=Integer.SIZE+bufferUTF8.length;
            m_sendBuffer.Clear();
            m_sendBuffer.WriteInt(totalLength,false);
            m_sendBuffer.WriteInt(bufferUTF8.length,false);
            m_sendBuffer.WriteBuffer(bufferUTF8);
            DataOutputStream dOut= new DataOutputStream(m_tcpClient.getOutputStream());
            dOut.write(m_sendBuffer.Buffer,0,m_sendBuffer.DataCount);
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
    }
    public void SendCommand(byte[]buffer,int offset,int count){
        String commandText=m_outgoingDataAssembler.GetProtocolText();
        try{
            byte[] bufferUTF8=commandText.getBytes("UTF-8");
            int totalLength=Integer.SIZE+bufferUTF8.length+count;
            m_sendBuffer.Clear();
            m_sendBuffer.WriteInt(totalLength,false);
            m_sendBuffer.WriteInt(bufferUTF8.length,false);
            m_sendBuffer.WriteBuffer(bufferUTF8);
            m_sendBuffer.WriteBuffer(buffer,offset,count);
            DataOutputStream dOut= new DataOutputStream(m_tcpClient.getOutputStream());
            dOut.write(m_sendBuffer.Buffer,0,m_sendBuffer.DataCount);
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
    }
    public boolean RecvCommand(){
        m_recvBuffer.Clear();
        try{
            InputStream stream = m_tcpClient.getInputStream();
            //byte[] data = new byte[Integer.SIZE/Byte.SIZE];
            int count = stream.read(m_recvBuffer.Buffer);
            ByteBuffer wrapped = ByteBuffer.wrap(m_recvBuffer.Buffer);
            int packetLength= wrapped.getInt();

        }catch(Exception e){

        }

    }
}




























































*/
