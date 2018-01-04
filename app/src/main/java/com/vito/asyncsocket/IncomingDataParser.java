/*
package com.vito.asyncsocket;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by zlb on 12/22/2017.
 *//*


public class IncomingDataParser {
    private String m_header;
    private String m_command;
    private ArrayList<String> m_names;
    private ArrayList<String> m_values;

    public IncomingDataParser(){
        m_names=new ArrayList<String>();
        m_values=new ArrayList<String>();
    }
    public boolean DecodeProtocolText(String protocolText){
        m_values.clear();
        m_names.clear();
        m_header="";
        int speIndex=protocolText.indexOf(ProtocolKey.ReturnWrap);
        if(speIndex<0){
            return false;
        }else{
            String[] tmpNameValues=protocolText.split(ProtocolKey.ReturnWrap);
            if(tmpNameValues.length<2)
                return false;
            for(int i=0;i<tmpNameValues.length;i++){
                String [] tmpStr=tmpNameValues[i].split(ProtocolKey.EqualSign);
                if(tmpStr.length>1){
                    if(tmpStr.length>2)
                        return false;
                    if(tmpStr[0].equals(ProtocolKey.Command)){
                        m_command=tmpStr[1];
                    }else{
                        m_names.add(tmpStr[0].toLowerCase());
                        m_values.add(tmpStr[1]);
                    }
                }
            }
            return true;
        }
    }
    public boolean GetValue(String protocolKey, String[] value){
        int index=m_names.indexOf((protocolKey.toLowerCase()));
        if(index>-1){
            value[0]=m_values.get(index);
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<String> GetValue(String protocolKey){
        ArrayList<String> result=new ArrayList<String>();
        for(int i=0;i<m_names.size();i++ ){
            if(protocolKey.equals(m_names.get(i))){
                result.add(m_values.get(i));
            }
        }
        return result;
    }

    public boolean GetValue(String protocolKey,short[] value){
        int index=m_names.indexOf(protocolKey.toLowerCase());
        if(index>-1){
            short tempValue=0;
            try{
                tempValue = Short.parseShort(m_values.get(index));
            }catch(Exception e){
                return false;
            }
            value[0]=tempValue;
            return true;
        }else{
            return false;
        }
    }
    public boolean GetValue(String protocolKey,int[] value){
        int index= m_names.indexOf(protocolKey.toLowerCase());
        if(index>-1)
        {
            int tempValue=0;
            try{
                tempValue=Integer.parseInt((m_values.get(index)));
            }catch (Exception e){
                return false;
            }
            value[0]=tempValue;
            return true;
        }else{
            return false;
        }
    }
    public boolean GetValue(String protocolKey,long[] value){
        int index= m_names.indexOf(protocolKey.toLowerCase());
        if(index>-1)
        {
            long tempValue=0;
            try{
                tempValue= Long.parseLong(m_values.get(index));
            }catch (Exception e){
                return false;
            }
            value[0]=tempValue;
            return true;
        }else{
            return false;
        }
    }
    public boolean GetValue(String protocolKey,float[] value){
        int index= m_names.indexOf(protocolKey.toLowerCase());
        if(index>-1)
        {
            float tempValue=0;
            try{
                tempValue= Float.parseFloat(m_values.get(index));
            }catch (Exception e){
                return false;
            }
            value[0]=tempValue;
            return true;
        }else{
            return false;
        }
    }
    public boolean GetValue(String protocolKey,double[] value){
        int index= m_names.indexOf(protocolKey.toLowerCase());
        if(index>-1)
        {
            double tempValue=0;
            try{
                tempValue= Double.parseDouble(m_values.get(index));
            }catch (Exception e){
                return false;
            }
            value[0]=tempValue;
            return true;
        }else{
            return false;
        }
    }
}

























*/
