package com.Plantze.tracker;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class EcoTrackerApplication extends Application {
    private StoredData storedData;
    private String id="1";
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseApp.initializeApp(this);
        storedData=new StoredData(id);

    }
    public StoredData getStoredData(){
        return storedData;
    }
    public void setStoredData(String sid) {
        storedData = new StoredData(sid);
    }
    public static  EcoTrackerApplication getInstance(Context context){
        return (EcoTrackerApplication) context.getApplicationContext();
    }
}
