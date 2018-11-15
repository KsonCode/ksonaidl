package com.example.kson.ksonaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class PersonService extends Service {
    private final String TAG = "Server";

    private List<Person> personList;

    public PersonService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        personList = new ArrayList<>();
        initData();
    }

    private void initData() {
        Person Person1 = new Person("风清扬");
        Person Person2 = new Person("2");
        Person Person3 = new Person("3");
        personList.add(Person1);
        personList.add(Person2);
        personList.add(Person3);
    }


    class MyBinder extends PersonController.Stub{
        @Override
        public List<Person> getList() throws RemoteException {
            return personList;
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            personList.add(person);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    
}
