package com.plantze.app;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class EcoTrackerApplication extends Application {
    private StoredData storedData;
    public String id;
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseApp.initializeApp(this);

    }
    public StoredData getStoredData(){
        return storedData;
    }
    public void createStoredData(String newUID){

        this.id=newUID;
        storedData=new StoredData(newUID);
    }
    public void changeStoredData(String newUID){
        this.storedData=new StoredData(newUID);
    }
    public void setStoredData(String sid) {
        storedData = new StoredData(sid);
    }
    public static  EcoTrackerApplication getInstance(Context context){
        return (EcoTrackerApplication) context.getApplicationContext();
    }
}
