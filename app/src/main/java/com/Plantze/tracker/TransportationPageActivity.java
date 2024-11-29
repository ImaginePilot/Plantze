package com.Plantze.tracker;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TransportationPageActivity extends AppCompatActivity {
    Button saveButton;
    Spinner spinner1;
    Spinner spinner2;
    EditText editText;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    private ArrayList<String> items;
    String sub_type="";
    String value;
    int int_value;
    String sub_sub_type;
    String date;
    StoredData d;
    AbstractActivities a;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_trans);
        saveButton=findViewById(R.id.shoppingPageSaveButton);
        editText=findViewById(R.id.editTextDistance);
        date=getIntent().getStringExtra("date");
        spinner1=findViewById(R.id.transportationPageSpinner);
        spinner2=findViewById(R.id.transportationPageSpinner2);
        items=new ArrayList<>();
        //adapter1=new ArrayAdapter<>(this,R.layout.spinner_item_layout,R.id.transportationPageSpinner,items);
        adapter2=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        d=EcoTrackerApplication.getInstance(this).getStoredData();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        sub_type="car";
                        setCarItems();
                        break;
                    case 1:
                        sub_type="plane";
                        setPlaneItems();
                        break;
                    case 2:
                        sub_type="public_transport";
                        setPublicItems();
                        break;
                    case 3:
                        sub_type="cycling_or_walking";
                        setEcoItems();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (sub_type){
                    case "car":
                        switch (position){
                            case 0:
                                sub_sub_type="electricity";
                                break;
                            case 1:
                                sub_sub_type="diesel";
                                break;
                            case 2:
                                sub_sub_type="hybrid";
                                break;
                            case 3:
                                sub_sub_type="gasoline";
                                break;
                            case 4:
                                sub_sub_type="other";
                                break;
                        }
                        break;
                    case "public_transport":
                        sub_sub_type="N/A";
                        break;
                    case "cycling_or_walking":
                        sub_sub_type="N/A";
                        break;
                    case "plane":
                        switch (position){
                            case 0:
                                sub_sub_type="short_haul";
                                break;
                            case 1:
                                sub_sub_type="long_haul";
                                break;
                        }
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveButton.setOnClickListener(v->{
            try{
                value=editText.getText().toString().trim();
                int_value=Integer.parseInt(value);

                createInstance();
                Intent intent=new Intent(this,EcoTrackerActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this,"Please enter a number!",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void createInstance(){
        if(sub_type.equals("car")){
            a=new TransportActivities(sub_type,int_value,sub_sub_type);
        } else if (sub_type.equals("public_transport")) {
            a=new TransportActivities(sub_type,int_value);
        }else if (sub_type.equals("plane")){
            a=new TransportActivities(sub_type,int_value,sub_sub_type.equals("long_haul"));
        }else if(sub_type.equals("cycling_or_walking")){
            a=new TransportActivities(sub_type,int_value);
        }
        try{
            Log.d(TAG, "createInstance: date"+date);
            ArrayList<Integer> decode_date=StoredData.decode_date(date);
            a.year=decode_date.get(0);
            a.month=decode_date.get(1);
            a.day=decode_date.get(2);
            d.single_write_to_mapping(a);
            //d.flush_to_file(this);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Instance not created or mapped successfully!",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "createInstance: Error, instance not created or mapped");
        }

    }
    private void setCarItems(){
        items.clear();
        items.add("electric");
        items.add("diesel");
        items.add("hybrid");
        items.add("gasoline");
        items.add("other");
        adapter2.setNotifyOnChange(true);
        adapter2.notifyDataSetChanged();
        spinner2.setSelection(3);
        editText.setHint("Distance(km)");
        Log.d(TAG, items.toString());

    }
    private void setPublicItems(){
        items.clear();
        items.add("bus/metro");
        items.add("taxi");
        items.add("train");
        adapter2.notifyDataSetChanged();
        spinner2.setSelection(0);
        editText.setHint("Times");
    }
    private void setEcoItems(){
        items.clear();
        items.add("bicycle/e-bike");
        items.add("walk");
        adapter2.notifyDataSetChanged();
        spinner2.setSelection(0);
        editText.setHint("Hours(h)");

    }
    private void setPlaneItems(){
        items.clear();
        items.add("short haul");
        items.add("long haul");
        adapter2.notifyDataSetChanged();
        spinner2.setSelection(0);
        editText.setHint("Legs(times)");
    }
}
