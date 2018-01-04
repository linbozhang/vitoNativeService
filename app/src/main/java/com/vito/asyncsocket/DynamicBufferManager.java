/*
package com.vito.asyncsocket;

import android.util.Log;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

*/
/**
 * Created by zlb on 1/3/2018.
 *//*


public class DynamicBufferManager {

    public byte[] Buffer;
    public int DataCount;
    public DynamicBufferManager(int bufferSize){
        DataCount=0;
        Buffer=new byte[bufferSize];
    }
    public int GetDataCount(){
        return DataCount;
    }
    public int GetReserveCount(){
        return Buffer.length-DataCount;
    }
    public void Clear(){
        DataCount=0;
    }
    public void Clear(int count){
        if(count>=DataCount){
            DataCount=0;
        }else{
            for(int i=0;i<DataCount-count;i++){
                Buffer[i]=Buffer[count+i];
            }
            DataCount=DataCount-count;
        }
    }
    public void SetBufferSize(int size){
        if(Buffer.length<size){
            byte[] tmpBuffer=new byte[size];
            System.arraycopy(Buffer,0,tmpBuffer,0,DataCount);
            Buffer=tmpBuffer;
        }
    }
    public void WriteBuffer(byte[] buffer,int offset,int count){
        if(GetReserveCount()>=count){
            System.arraycopy(buffer,offset,Buffer,DataCount,count);
            DataCount=DataCount+count;
        }else{
            int totalSize=Buffer.length+count-GetReserveCount();
            byte[] tmpBuffer=new byte[totalSize];
            System.arraycopy(Buffer,0,tmpBuffer,0,DataCount);
            System.arraycopy(buffer,offset,tmpBuffer,DataCount,count);
            DataCount=DataCount+count;
            Buffer=tmpBuffer;
        }
    }
    public void WriteBuffer(byte[] buffer){
        WriteBuffer(buffer,0,buffer.length);
    }
    public void WriteShort(short value,boolean convert){
        if(convert)
        {
            value=Short.reverseBytes(value);
        }
        ByteBuffer b = ByteBuffer.allocate(Short.SIZE/Byte.SIZE);
        b.putShort(value);
        byte[] tmpBuffer=b.array();
        WriteBuffer(tmpBuffer);
    }

    public void WriteInt(int value,boolean convert){
        if(convert)
        {
            value=Integer.reverseBytes(value);
        }
        ByteBuffer b = ByteBuffer.allocate(Integer.SIZE/Byte.SIZE);
        b.putInt(value);
        byte[] tmpBuffer=b.array();
        WriteBuffer(tmpBuffer);
    }
    public void WriteLong(long value,boolean convert){
        if(convert)
        {
            value=Long.reverseBytes(value);
        }
        ByteBuffer b = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
        b.putLong(value);
        byte[] tmpBuffer=b.array();
        WriteBuffer(tmpBuffer);
    }
    public void WriteString(String value) {
        try{
            byte[] tmpBuffer=value.getBytes("UTF-8");
            WriteBuffer(tmpBuffer);
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }


    }



}











































*/
