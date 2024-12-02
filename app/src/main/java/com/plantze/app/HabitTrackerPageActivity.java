package com.plantze.app;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitTrackerPageActivity extends AppCompatActivity {
    private String date;
    private String keyw="";
    private EditText editText;
    private Spinner typeSpinner;
    private Spinner impactSpinner;
    private StoredData d;
    private StoredData local_d=new StoredData(new HashMap<>());
    private List<List<String>> filtered_activities;
    private Map<String,List<List<String>>> mapped_filtered_activities;
    public HabitTrackerRecyclerAdapter adapter;
    public RecyclerView recyclerView;
    public int type;
    public int impact;

    public List<String> keywords;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        d=EcoTrackerApplication.getInstance(this).getStoredData();
        setContentView(R.layout.activity_habit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();
            date=today.format(formatter);
        }
        editText=findViewById(R.id.searchHabitEditText);
        typeSpinner=findViewById(R.id.spinnerType);
        impactSpinner=findViewById(R.id.spinnerImpact);
        recyclerView=findViewById(R.id.recyclerHabits);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        adapter=new HabitTrackerRecyclerAdapter(this,date,local_d);
        recyclerView.setAdapter(adapter);
        typeSpinner.setSelection(0);
        impactSpinner.setSelection(0);
        populateKeywords();
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=position;
                refreshRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        impactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                impact=position;
                refreshRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyw=editText.getText().toString();
                refreshRecyclerView();
            }
        });


    }
    public static Map<String, Boolean> checkSubstrings(String text, List<String> substrings) {
        Map<String, Boolean> results = new HashMap<>();

        for (String substring : substrings) {
            results.put(substring, text.contains(substring));
        }

        return results;
    }
    public void populateKeywords(){
        keywords=new ArrayList<>();
        keywords.addAll(Arrays.asList("trans","shop","food","consumption","car","plane","cycle","public","plane","walk","electronic","cloth","beef","pork","chicken","fish"));
    }
    public void refreshRecyclerView(){
        mapped_filtered_activities=new HashMap<>();
        List<List<String>> final_filter=getFilteredActivitiesByKeyword();

        if(type==0&&impact==0){
        } else if (type==0) {
            final_filter=getFilteredActivities(final_filter,filter_impact(impact));
        } else if (impact==0) {
            final_filter=getFilteredActivities(final_filter,filter_type(type));
        }else {
            final_filter=getFilteredActivities(getFilteredActivities(filter_impact(impact),filter_type(type)),final_filter);
        }
        mapped_filtered_activities.put(date,final_filter);
        local_d=new StoredData(mapped_filtered_activities);
        adapter.updateData(local_d);
    }

    public List<List<String>> getFilteredActivitiesByKeyword(){
        Map<String,Boolean> mapRes=checkSubstrings(keyw,keywords);
        List<List<String>> res=new ArrayList<>();
        if(mapRes.get("trans")){res.addAll(type_transportation());}
        if(mapRes.get("shop")){res.addAll(type_shopping());}
        if(mapRes.get("food")||mapRes.get("consumption")){res.addAll(type_food());}
        if(mapRes.get("car")){
            res.add(fillList4("tra","car",20,"diesel"));
            res.add(fillList4("tra","car",20,"gasoline"));
            res.add(fillList4("tra","car",20,"hybrid"));
            res.add(fillList4("tra","car",20,"electric"));
        }
        if(mapRes.get("plane")){
            res.add(fillList4("tra","plane",1,"false"));
        }
        if(mapRes.get("cycle")||mapRes.get("walk")){
            res.add(fillList("tra","cycling_or_walking",4));
        }
        if(mapRes.get("public")){
            res.add(fillList("tra","public_transport",4));
        }
        if(mapRes.get("electronic")){
            res.add(fillList("sho","electronics",1));
        }
        if(mapRes.get("cloth")){
            res.add(fillList("sho","clothes",1));
        }
        if(mapRes.get("beef")){
            res.add(fillList("foo","pork",1));
        }
        if(mapRes.get("pork")){
            res.add(fillList("foo","beef",1));
        }
        if(mapRes.get("chicken")){
            res.add(fillList("foo","chicken",1));
        }
        if(mapRes.get("fish")){
            res.add(fillList("foo","fish",1));
        }
        return res;

    }
    public List<List<String>> getFilteredActivities(List<List<String>> X,List<List<String>> Y){
        List<List<String>> result = new ArrayList<>();
        for(List<String> x:X){
            if(Y.contains(x)){
                result.add(x);
            }
        }
        return result;
    }


    public List<String> fillList(String type,String subtype,Integer value){
        List<String> res=new ArrayList<>();
        res.add(type);
        res.add(subtype);
        res.add(""+value);
        return res;
    }
    public List<String>fillList4(String type,String subtype,Integer value,String extra){
        List<String> res=fillList(type,subtype,value);
        res.add(extra);
        return res;
    }

    public List<List<String>> filter_type(int pos){
        switch (pos){
            case 0:
                return new ArrayList<>();
            case 1:
                return type_transportation();
            case 2:
                return type_food();
            case 3:
                return type_shopping();
        }
        return Collections.emptyList();
    }

    public List<List<String>> filter_impact(int pos){
        switch (pos){
            case 0:
                return new ArrayList<>();
            case 1:
                return impact_low();
            case 2:
                return impact_medium();
            case 3:
                return impact_large();
        }
        return Collections.emptyList();
    }

    public List<List<String>> type_transportation(){
        List<List<String>> filtered=new ArrayList<>();
        filtered.add(fillList4("tra","car",20,"diesel"));
        filtered.add(fillList4("tra","car",20,"gasoline"));
        filtered.add(fillList4("tra","car",20,"hybrid"));
        filtered.add(fillList4("tra","car",20,"electric"));
        filtered.add(fillList("tra","public_transport",4));
        filtered.add(fillList("tra","cycling_or_walking",4));
        filtered.add(fillList4("tra","plane",1,"false"));
        return filtered;
    }

    public List<List<String>> type_shopping(){
        List<List<String>> filtered=new ArrayList<>();
        filtered.add(fillList("sho","clothes",1));
        filtered.add(fillList("sho","electronics",1));
        filtered.add(fillList("sho","other",1));
        return filtered;
    }

    public List<List<String>> type_food(){
        List<List<String>> filtered=new ArrayList<>();
        filtered.add(fillList("foo","beef",1));
        filtered.add(fillList("foo","pork",1));
        filtered.add(fillList("foo","chicken",1));
        filtered.add(fillList("foo","fish",1));
        return filtered;
    }

    public List<List<String>> impact_low(){
        List<List<String>> filtered=new ArrayList<>();
        filtered.add(fillList("tra","public_transport",4));
        filtered.add(fillList("tra","cycling_or_walking",4));
        filtered.add(fillList("sho","clothes",1));
        filtered.add(fillList("foo","fish",1));
        return filtered;

    }
    public List<List<String>> impact_medium(){
        List<List<String>> filtered=new ArrayList<>();
        filtered.add(fillList4("tra","car",20,"diesel"));
        filtered.add(fillList4("tra","car",20,"gasoline"));
        filtered.add(fillList4("tra","car",20,"hybrid"));
        filtered.add(fillList4("tra","car",20,"electric"));
        filtered.add(fillList("sho","other",1));
        filtered.add(fillList("foo","pork",1));
        filtered.add(fillList("foo","chicken",1));
        return filtered;
    }

    public List<List<String>> impact_large(){
        List<List<String>> filtered=new ArrayList<>();
        filtered.add(fillList4("tra","plane",1,"false"));
        filtered.add(fillList("sho","electronics",1));
        filtered.add(fillList("foo","beef",1));
        return filtered;
    }



}
