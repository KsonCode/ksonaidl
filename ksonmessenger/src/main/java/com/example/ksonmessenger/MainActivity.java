package com.example.ksonmessenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection serviceConnection;
    private Messenger messenger;//客户端messenger
    private Messenger serverMessenger;//服务端

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MessengerService.class));
        initService();

        messenger = new Messenger(new MyHandler());//客户端配置接收
    }

    private void initService() {

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serverMessenger = new Messenger(service);





            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(new Intent("com.example.ksonmessenger.MessengerService"), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void send(View view) {
        try {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("key", "我是客户端");
            msg.setData(bundle);
            msg.replyTo = messenger;
            serverMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, msg.getData().getString("key"), Toast.LENGTH_SHORT).show();

        }
    }

    public void receive(View view) {
//        Toast.makeText(this,"接受消息："+serverMessenger.)
    }
}
