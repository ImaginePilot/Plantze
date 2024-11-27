package com.Plantze.tracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class BillPageActivity extends AppCompatActivity {
    private EditText electricityEditText;
    private EditText gasEditText;
    private EditText waterEditText;
    private Button saveButton;
    private Button deleteButton;
    private String date;
    private StoredData d;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bills_input);
        electricityEditText=findViewById(R.id.editTextElectricity);
        gasEditText=findViewById(R.id.editTextGas);
        waterEditText=findViewById(R.id.editTextWater);
        deleteButton=findViewById(R.id.billPageDeleteButton);
        saveButton=findViewById(R.id.shoppingPageSaveButton);
        date=this.getIntent().getStringExtra("date");
        d=EcoTrackerApplication.getInstance(this).getStoredData();
        setDefaultText();
        saveButton.setOnClickListener(v->{
            deleteMonthlyBillActivities("electricity");
            deleteMonthlyBillActivities("gas");
            deleteMonthlyBillActivities("water");
            String s_electric=getEditText(electricityEditText);
            String s_gas=getEditText(gasEditText);
            String s_water=getEditText(waterEditText);
            if(!s_water.isEmpty()){
                createMonthlyBillActivities("water",Integer.parseInt(s_water));
            }
            if(!s_gas.isEmpty()){
                createMonthlyBillActivities("gas",Integer.parseInt(s_gas));
            }
            if(!s_electric.isEmpty()){
                createMonthlyBillActivities("electricity",Integer.parseInt(s_electric));
            }
            Intent intent=new Intent(this,EcoTrackerActivity.class);
            intent.putExtra("date",date);
            startActivity(intent);

        });
        deleteButton.setOnClickListener(v -> {
            deleteMonthlyBillActivities("electricity");
            deleteMonthlyBillActivities("gas");
            deleteMonthlyBillActivities("water");
            Intent intent=new Intent(this,EcoTrackerActivity.class);
            intent.putExtra("date",date);
            startActivity(intent);
        });

    }
    private String getEditText(EditText e){
        String res=e.getText().toString().trim();
        return  res;
    }
    public static List<Integer> getAllDaysInMonth(int year, int month) {
        YearMonth yearMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yearMonth = YearMonth.of(year, month);
        }
        List<Integer> days = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
                days.add(day);
            }
        }
        return days;
    }
    public static void writeDate(AbstractActivities a,String s_d){
        a.year=StoredData.decode_date(s_d).get(0);
        a.month=StoredData.decode_date(s_d).get(1);
        a.day=StoredData.decode_date(s_d).get(2);
    }
    private List<String> getAllStringifiedDates(){
        int year=StoredData.decode_date(date).get(0);
        int month=StoredData.decode_date(date).get(1);
        List<Integer> day=getAllDaysInMonth(year,month);
        List<String> dates=new ArrayList<>();
        for(int i_d:day){
            dates.add(d.encode_date(year,month,i_d));
        }
        return dates;
    }
    private int getMonthlyBillValue(String type){
        if(d.s_data.get(date)!=null){
            List<List<String>> s_activities=d.s_data.get(date);
            for(List<String> activity:s_activities){
                if(activity.get(0).equals("bill")&&activity.get(1).equals(type)){
                    return Integer.parseInt(activity.get(2));
                }
            }
        }
        return -1;

    }
    private void setDefaultText(){
        if(getMonthlyBillValue("electricity")!=-1){
            electricityEditText.setText(""+getMonthlyBillValue("electricity"));
        }
        if(getMonthlyBillValue("gas")!=-1){
            gasEditText.setText(""+getMonthlyBillValue("gas"));
        }
        if(getMonthlyBillValue("water")!=-1){
            waterEditText.setText(""+getMonthlyBillValue("water"));
        }

    }


    private void deleteMonthlyBillActivities(String type){
        List<String> dates=getAllStringifiedDates();
        int value=getMonthlyBillValue(type);
        for(String s_d:dates){
            MonthlyBillActivities a=new MonthlyBillActivities(type,value);
            writeDate(a,s_d);
            d.delete_activity(a);
        }
    }

    private void createMonthlyBillActivities(String type,int value){
        List<String> dates=getAllStringifiedDates();
        for(String s_d:dates){
            MonthlyBillActivities a=new MonthlyBillActivities(type,value);
            writeDate(a,s_d);
            d.single_write_to_mapping(a);
        }

    }

}
