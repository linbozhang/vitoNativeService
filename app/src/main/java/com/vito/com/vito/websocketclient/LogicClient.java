package com.vito.com.vito.websocketclient;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by zlb on 1/4/2018.
 */

public class LogicClient {
    private WebSocketClient client;
    private Draft selectDraft;

    public void SendMessage(int type, String msg)
    {
        if(client!=null&&client.isOpen())
        {
            client.send("{\"t\":"+type+",\"b\":"+"\""+msg+"\"}");
        }
    }

    public void ConnectServer()
    {
        try{
            client=new WebSocketClient(new URI("ws://192.168.70.188:3001")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.i("TAG","OnOpen");
                }

                @Override
                public void onMessage(String message) {
                    Log.i("TAG","receive messgae :"+message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.i("TAG","OnClose:"+code+" reason "+reason+" remote:"+remote);
                }

                @Override
                public void onError(Exception ex) {
                    Log.i("TAG","OnError:"+ex.getMessage());
                }
            };
            client.connect();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }

    }
}
