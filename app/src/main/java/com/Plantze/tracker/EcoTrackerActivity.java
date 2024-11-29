package com.Plantze.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EcoTrackerActivity extends AppCompatActivity{
    private String selectedDate;
    private Button transportationAdd;
    private Button foodAdd;
    private Button shoppingAdd;

    private Button monthlyBill;
    private Button selectDateButton;
    private StoredData d;
    private EcoTrackerRecyclerAdapter  adapter;
    private TextView CO2Text;

    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        d=EcoTrackerApplication.getInstance(this).getStoredData();

        setContentView(R.layout.activity_eco_tracker);
        transportationAdd=findViewById(R.id.transportationActivityAddButton);
        foodAdd=findViewById(R.id.foodActivityAddButton);
        shoppingAdd=findViewById(R.id.shoppingActivityAddButton);
        CO2Text=findViewById(R.id.CO2TextView);
        monthlyBill=findViewById(R.id.monthlyBillButton);
        selectedDate = getIntent().getStringExtra("date");

        if (selectedDate != null) {
            TextView dateTextView = findViewById(R.id.ecoTrackerDateTextView);
            dateTextView.setText("Date: " + selectedDate);
        } else {
            // Handle case where no date was passed
            Toast.makeText(this, "No date selected", Toast.LENGTH_SHORT).show();
        }
        selectDateButton=findViewById(R.id.calendar);
        selectDateButton.setOnClickListener(v->{
            Log.d("TAG","pressed SelectDate");
            Intent calendarIntent=new Intent(this,CalendarActivity.class);
            startActivity(calendarIntent);

        });
        transportationAdd.setOnClickListener(v -> {
            Intent intent=new Intent(this, TransportationPageActivity.class);
            intent.putExtra("date",selectedDate);
            startActivity(intent);
        });
        monthlyBill=findViewById(R.id.monthlyBillButton);
        monthlyBill.setOnClickListener(v->{
            Intent intent=new Intent(this,BillPageActivity.class);
            intent.putExtra("date",selectedDate);
            startActivity(intent);
        });
        foodAdd.setOnClickListener(v->{
            Intent intent=new Intent(this,FoodPageActivity.class);
            intent.putExtra("date",selectedDate);
            startActivity(intent);
        });
        shoppingAdd.setOnClickListener(v->{
            Intent intent=new Intent(this,ShoppingPageActivity.class);
            intent.putExtra("date",selectedDate);
            startActivity(intent);
        });

        recyclerView=findViewById(R.id.recyclerViewIndividualActivities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new EcoTrackerRecyclerAdapter(this,selectedDate);
        recyclerView.setAdapter(adapter);
        adapter.setExternalCO2TextView(CO2Text);


        CO2Text.setText("Total Emissions Today: "+(int)d.getCO2(selectedDate)+" Kg");



    }
    @Override
    public void onPause(){
        super.onPause();
        d.upload_to_database();

    }


}
