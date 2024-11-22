package com.Plantze.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainAddActivityAdapter.OnItemClickListener{
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
        RecyclerView recyclerView = findViewById(R.id.recycler_view_activities);
        setContentView(R.layout.activity_eco_tracker);
        startBackgroundTasks();
        MainAddActivityAdapter mainAddActivityAdapter = new MainAddActivityAdapter(this);
        Button selectDateButton=findViewById(R.id.calendar);
        selectDateButton.setOnClickListener(v->{
            Log.d("TAG","pressed SelectDate");
            Intent calendarIntent=new Intent(this,CalendarActivity.class);
            startActivity(calendarIntent);

        });
    }

    @Override
    public void onSelectDateButtonClick(int position){

    }

    private void initializeCore() {
        //needs more work on stored data
        stored_data=new StoredData("123456");
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