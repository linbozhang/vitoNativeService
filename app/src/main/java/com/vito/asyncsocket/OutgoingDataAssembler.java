/*
package com.vito.asyncsocket;

import android.support.annotation.ArrayRes;

import java.util.ArrayList;

*/
/**
 * Created by zlb on 1/3/2018.
 *//*


public class OutgoingDataAssembler {
    private ArrayList<String> m_protocolText;
    public OutgoingDataAssembler(){
        m_protocolText=new ArrayList<String>();
    }
    public void Clear(){
        m_protocolText.clear();
    }
    public String GetProtocolText(){
        String tmpString="";
        if(m_protocolText.size()>0){
            tmpString=m_protocolText.get(0);
            for(int i=1;i<m_protocolText.size();i++){
                tmpString=tmpString+ProtocolKey.ReturnWrap+m_protocolText.get(i);
            }
        }
        return tmpString;
    }
    public void AddRequest(){
        m_protocolText.add(ProtocolKey.LeftBrackets+ProtocolKey.Request+ProtocolKey.RightBrackets);
    }
    public void AddResponse(){
        m_protocolText.add(ProtocolKey.LeftBrackets+ProtocolKey.Response+ProtocolKey.RightBrackets);
    }
    public void AddCommand(String commandKey){
        m_protocolText.add(ProtocolKey.Command+ProtocolKey.EqualSign+commandKey);
    }
    public void AddSuccess(){
        m_protocolText.add(ProtocolKey.Code+ProtocolKey.EqualSign+ProtocolKey.Success);
    }
    public void AddFailure(int errorCode,String message){
        m_protocolText.add(ProtocolKey.Code+ProtocolKey.EqualSign+errorCode);
        m_protocolText.add(ProtocolKey.Message+ProtocolKey.EqualSign+message);
    }
    public void AddValue(String protocolKey,String value){
        m_protocolText.add(protocolKey+ProtocolKey.EqualSign+value);
    }
    public void AddValue(String protocolKey,short value){
        m_protocolText.add(protocolKey+ProtocolKey.EqualSign+value);
    }
    public void AddValue(String protocolKey,int value){
        m_protocolText.add(protocolKey+ProtocolKey.EqualSign+value);
    }
    public void AddValue(String protocolKey,long value){
        m_protocolText.add(protocolKey+ProtocolKey.EqualSign+value);
    }
    public void AddValue(String protocolKey,float value){
        m_protocolText.add(protocolKey+ProtocolKey.EqualSign+value);
    }
    public void AddValue(String protocolKey,double value){
        m_protocolText.add(protocolKey+ProtocolKey.EqualSign+value);
    }
}








































*/
