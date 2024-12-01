package com.plantze.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button saveButton;
    private String selectedDate;
@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        calendarView=findViewById(R.id.calendarView);
        saveButton=findViewById(R.id.calendarSaveButton);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate=sdf.format(new Date(calendarView.getDate()));
        calendarView.setOnDateChangeListener((view,year,month,dayOfMonth)->{
            selectedDate=String.format("%d-%02d-%02d",year,(month+1),dayOfMonth);
        });
        saveButton.setOnClickListener(v->{
            saveSelectedDate();
        });

    }
    private void saveSelectedDate(){
        Intent intent=new Intent(this,EcoTrackerActivity.class);
        intent.putExtra("date",selectedDate);
        startActivity(intent);
        Toast.makeText(this, "Date saved: " + selectedDate, Toast.LENGTH_SHORT).show();
    }
}
