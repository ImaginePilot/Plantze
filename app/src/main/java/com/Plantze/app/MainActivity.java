package com.plantze.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{
    StoredData stored_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 1. Initialize essential components
        initializeCore();

        // 2. Setup UI
        setupUI();

        // 3. Load data
        loadData();

        // 4. Start background tasks

        setContentView(R.layout.activity_eco_tracker);
        startBackgroundTasks();
        //MainAddActivityAdapter mainAddActivityAdapter = new MainAddActivityAdapter(this);
        Button selectDateButton=findViewById(R.id.calendar);
        selectDateButton.setOnClickListener(v->{
            Log.d("TAG","pressed SelectDate");
            Intent calendarIntent=new Intent(this,CalendarActivity.class);
            startActivity(calendarIntent);

        });
    }

    //@Override
    //public void onSelectDateButtonClick(int position){

    //}

    private void initializeCore() {
        //needs more work on stored data
        EcoTrackerApplication.getInstance(this).setStoredData("123456");
        // Essential initialization
    }

    private void setupUI() {
        //we create mainaddactivityadapter first
        setContentView(R.layout.activity_main);
        // UI setup
    }

    private void loadData() {
        // Data loading
    }

    private void startBackgroundTasks() {
        // Background operations
    }
}