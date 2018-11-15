package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kson.ksonaidl.Person;
import com.example.kson.ksonaidl.PersonController;

public class MainActivity extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private PersonController personController;
    private boolean isConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
    }

    private void initService() {

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                personController = PersonController.Stub.asInterface(service);
                isConnect = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                isConnect = false;

            }
        };
        bindService(new Intent("com.example.kson.ksonaidl.PersonService"),serviceConnection,Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(serviceConnection);

    }


    public void getList(View view) {
        try {
            if (isConnect){

                Toast.makeText(this,personController.getList().size()+"",Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addPerson(View view) {
        try {
            if (isConnect){

                personController.addPerson(new Person("kson"));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
