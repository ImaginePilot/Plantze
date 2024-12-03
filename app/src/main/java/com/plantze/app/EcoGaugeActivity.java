package com.plantze.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EcoGaugeActivity extends AppCompatActivity {
    private PieChart pieChart;
    private PieChartHelper pieChartHelper;
    private TextView co2TextView;
    private CO2TextHelper co2TextHelper;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button calendarButton;
    private Button lineChartButton;
    private String globalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_gauge);
        globalDate = getIntent().getStringExtra("date");
        calendarButton=findViewById(R.id.calendarButton);
        lineChartButton=findViewById(R.id.lineChartButton);
        if(globalDate==null) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            globalDate = currentDate.format(formatter);
        }
        calendarButton.setOnClickListener(v->{
            Intent intent=new Intent(this,GaugeCalendarActivity.class);
            startActivity(intent);
        });
        lineChartButton.setOnClickListener(v->{

        });


        // Initializing Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("categories"); // Path in Firebase Database

        // Initializing PieChart
        pieChart = findViewById(R.id.chart);
        pieChartHelper = new PieChartHelper(pieChart);

        // Initializing CO2 TextView
        co2TextView = findViewById(R.id.co2TextView);
        co2TextHelper = new CO2TextHelper(co2TextView); // Initialize the CO2TextHelper

        // Initializing Spinner
        initSpinner();

        // Setting default chart data and CO2 values
        pieChartHelper.updatePieChart(new CategoryData(0f, 0f, 0f, 0f)); // Example data
        co2TextHelper.updateCO2Text(new CategoryData(0f, 0f, 0f, 0f)); // Set initial CO2 value to "Daily"
    }

    // Method to initialize Spinner
    private void initSpinner() {
        Spinner spinnerPie = findViewById(R.id.spinner);
        String[] categoriesPie = {"Daily", "Weekly", "Monthly", "Yearly"};
        ArrayAdapter<String> adapterPie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesPie);
        adapterPie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPie.setAdapter(adapterPie);

        spinnerPie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Gets category data from Firebase for the selected category
                fetchCategoryData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }

    // Gets category data from Firebase and updates the chart
    private void fetchCategoryData(int categoryIndex) {
        StoredData s = EcoTrackerApplication.getInstance(this).getStoredData();
        s.create_activities();
        Log.d("EcoGaugeActivity", "Fetching category data for " + categoryIndex);
        if(categoryIndex == 0) {
            Log.d("EcoGaugeActivity", "Fetching category data for Daily");
            float transportationCO2 = s.getCO2ForDayByCategory(globalDate, TransportActivities.class.toString());
            float energyUseCO2 = s.getCO2ForDayByCategory(globalDate, MonthlyBillActivities.class.toString());
            float foodConsumptionCO2 = s.getCO2ForDayByCategory(globalDate, FoodConsumptionActivities.class.toString());
            float shoppingCO2 = s.getCO2ForDayByCategory(globalDate, ShoppingActivities.class.toString());
            CategoryData categoryData = new CategoryData(transportationCO2, energyUseCO2, foodConsumptionCO2, shoppingCO2);
            Log.d("EcoGaugeActivity", "Category data: " + categoryData.toString());
            pieChartHelper.updatePieChart(categoryData); // Pass the CategoryData
            co2TextHelper.updateCO2Text(categoryData);
            Log.d("EcoGaugeActivity", "Updated pie chart with data: " + categoryData.toString());
        }
        else if(categoryIndex == 1) {
            float transportationCO2 = s.getCO2ForWeekByCategory(globalDate, TransportActivities.class.toString());
            float energyUseCO2 = s.getCO2ForWeekByCategory(globalDate, MonthlyBillActivities.class.toString());
            float foodConsumptionCO2 = s.getCO2ForWeekByCategory(globalDate, FoodConsumptionActivities.class.toString());
            float shoppingCO2 = s.getCO2ForWeekByCategory(globalDate, ShoppingActivities.class.toString());
            CategoryData categoryData = new CategoryData(transportationCO2, energyUseCO2, foodConsumptionCO2, shoppingCO2);
            pieChartHelper.updatePieChart(categoryData); // Pass the CategoryData
            co2TextHelper.updateCO2Text(categoryData);
        }
        else if(categoryIndex == 2) {
            float transportationCO2 = s.getCO2ForMonthByCategory(StoredData.decode_date(globalDate).get(0),StoredData.decode_date(globalDate).get(1), TransportActivities.class.toString());
            float energyUseCO2 = s.getCO2ForMonthByCategory(StoredData.decode_date(globalDate).get(0),StoredData.decode_date(globalDate).get(1), MonthlyBillActivities.class.toString());
            float foodConsumptionCO2 = s.getCO2ForMonthByCategory(StoredData.decode_date(globalDate).get(0),StoredData.decode_date(globalDate).get(1), FoodConsumptionActivities.class.toString());
            float shoppingCO2 = s.getCO2ForMonthByCategory(StoredData.decode_date(globalDate).get(0),StoredData.decode_date(globalDate).get(1), ShoppingActivities.class.toString());
            CategoryData categoryData = new CategoryData(transportationCO2, energyUseCO2, foodConsumptionCO2, shoppingCO2);
            pieChartHelper.updatePieChart(categoryData); // Pass the CategoryData
            co2TextHelper.updateCO2Text(categoryData);
        }
        else if(categoryIndex == 3) {
            float transportationCO2 = s.getCO2ForYearByCategory(StoredData.decode_date(globalDate).get(0), TransportActivities.class.toString());
            float energyUseCO2 = s.getCO2ForYearByCategory(StoredData.decode_date(globalDate).get(0), MonthlyBillActivities.class.toString());
            float foodConsumptionCO2 = s.getCO2ForYearByCategory(StoredData.decode_date(globalDate).get(0), FoodConsumptionActivities.class.toString());
            float shoppingCO2 = s.getCO2ForYearByCategory(StoredData.decode_date(globalDate).get(0), ShoppingActivities.class.toString());
            CategoryData categoryData = new CategoryData(transportationCO2, energyUseCO2, foodConsumptionCO2, shoppingCO2);
            pieChartHelper.updatePieChart(categoryData); // Pass the CategoryData
            co2TextHelper.updateCO2Text(categoryData);
        }
    }
}
