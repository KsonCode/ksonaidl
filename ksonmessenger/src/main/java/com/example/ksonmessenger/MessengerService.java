package com.example.ksonmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class MessengerService extends Service {


    Messenger messenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null){
                Toast.makeText(getApplicationContext(),"收到客户端信息："+msg.getData().getString("key"),Toast.LENGTH_SHORT).show();
            }

            Message replyMsg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("key","服务端收到你的消息了");
            replyMsg.setData(bundle);
            try {
                msg.replyTo.send(replyMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    });
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
