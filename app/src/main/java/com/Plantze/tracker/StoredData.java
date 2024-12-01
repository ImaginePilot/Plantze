package com.Plantze.tracker;



import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class StoredData implements Serializable {
    //Stores Data and add data to FireBase
    private DatabaseReference reference;
    public Map<String,List<List<String>>> s_data=new HashMap<>();
    public List<AbstractActivities> activities=new ArrayList<>();
    private String uid;
    public StoredData(String uid){
        //FirebaseApp.initializeApp();
        this.uid=uid;
        reference=FirebaseDatabase.getInstance().getReference();
        download_from_database();
    }
    public StoredData(Map<String,List<List<String>>> l_data){
        s_data=l_data;
        create_activities();
    }

    public Map<String,List<List<String>>> read_from_file(Context context){
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            File file=new File(context.getFilesDir(),"data.json");
            Map<String,List<List<String>>> local_s_data=objectMapper.readValue(file,new TypeReference<Map<String, List<List<String>>>>() {});
            s_data=local_s_data;
        }catch(IOException e){
            e.printStackTrace();
        }
        return s_data;
    }

    public void upload_to_database(){
        //usually this should be tracker
        reference.child(uid).setValue(s_data).addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Map uploaded successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error uploading map: " + e.getMessage());
                });;
    }

    public void write_to_mapping(String date,List<List<String>> params){
        //This does not write anything to activity instances
        if(s_data.containsKey(date)){
            List<List<String>> new_params=new ArrayList<>(s_data.get(date));
            new_params.addAll(params);
            s_data.put(date,new_params);
        }else{
            s_data.put(date,params);
        }

    }
    public static ArrayList<String> stringifyActivity(AbstractActivities a){
        String date=encode_date(a.year,a.month,a.day);
        String sub_type=a.sub_type;
        String value=Integer.toString(a.value);
        String extra_value = "";
        String class_name = "";
        boolean marker=false;
        if(a.getClass()==FoodConsumptionActivities.class){
            class_name="foo";
        }else if(a.getClass()==ShoppingActivities.class){
            class_name="sho";
        }else if(a.getClass()==TransportActivities.class){
            class_name="tra";
            marker=true;
            if(sub_type=="car"||sub_type=="public_transport"){
                extra_value=((TransportActivities) a).vehicle;
            } else if (sub_type=="plane") {
                extra_value=Boolean.toString(((TransportActivities) a).is_long_haul_flight);
            }
        }else if(a.getClass()== MonthlyBillActivities.class){
            class_name="bill";
        }
        ArrayList<String> res=new ArrayList<>();
        res.add(class_name);
        res.add(sub_type);
        res.add(value);
        if(marker){
            res.add(extra_value);
        }
        if(a.is_habit){
            res.add("is_habit");
        }
        return res;
    }


    public void single_write_to_mapping(AbstractActivities a){
        String date=encode_date(a.year,a.month,a.day);
        activities.add(a);
        ArrayList<String> res=stringifyActivity(a);
        List<List<String>> l=new ArrayList<>();
        l.add(res);
        write_to_mapping(date,l);
    }
    public void flush_to_file(Context context){
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            File file=new File(context.getFilesDir(),"data.json");
            Log.d("FilePath", "File location: " + file.getAbsolutePath());
            objectMapper.writeValue(file,s_data);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void download_from_database() {
        reference.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                GenericTypeIndicator<Map<String, List<List<String>>>> typeIndicator =
                        new GenericTypeIndicator<Map<String, List<List<String>>>>() {};
                Map<String, List<List<String>>> result = task.getResult().getValue(typeIndicator);
                if(result==null){
                    //todo:finish this
                    result=new HashMap<>();
                }
                Log.d(TAG, "download_from_database: Successfully downloaded data"+result.toString());
                s_data=result;
            } else {
                // Handle the error here
                Log.e(TAG, "download_from_database: Error, cannot download from database" );
            }
        });
    }

    public void delete_activity(AbstractActivities a){

        String date=encode_date(a.year,a.month,a.day);
        List<String> s_a= stringifyActivity(a);
        if(s_data.get(date)==null){
            return;
        }
        try{
            boolean finished_delete_s_data=false;
            boolean finished_delete_activities=false;
            if(s_data.get(date).contains(s_a)){
                List<List<String>> new_data=new ArrayList<>();
                for(List<String> item:s_data.get(date)) {
                    if (!item.equals(s_a) || finished_delete_s_data) {
                        new_data.add(item);
                    }else{
                        finished_delete_s_data=true;
                    }
                }
                s_data.put(date,new_data);

                List<AbstractActivities> new_activities=new ArrayList<>();
                for(AbstractActivities item:activities){
                    String d_item=encode_date(item.year, item.month, item.day);
                    List<String> s_item=stringifyActivity(item);
                    if(((!s_item.equals(s_a))&&(!date.equals(d_item)))||finished_delete_activities){
                        new_activities.add(item);
                    }else{
                        finished_delete_activities=true;
                    }
                }
                activities=new_activities;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public double getCO2(String date){
        Map<String,List<List<String>>>activityStrings=new HashMap<>();
        if(this.s_data.containsKey(date)){
            activityStrings.put(date,this.s_data.get(date));
        }
        StoredData d=new StoredData(activityStrings);
        double CO2=0;
        for(AbstractActivities a:d.activities){
            CO2+=a.calculateCO2();
        }
        return CO2;
    }

    public static ArrayList<Integer> decode_date(String date){
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        ArrayList<Integer> res=new ArrayList<>();
        res.add(Integer.parseInt(year));
        res.add(Integer.parseInt(month));
        res.add(Integer.parseInt(day));
        return res;
    }
    public static String encode_date(int year,int month,int day){
        String s_month;
        String s_day;
        if(day<10){
            s_day="0"+day;
        }else{
            s_day=""+day;
        }
        if(month<10){
            s_month="0"+month;
        }else{
            s_month=""+month;
        }
        return year+"-"+s_month+"-"+s_day;
    }
    public void create_activities() {
        s_data.forEach((key, value) -> {
            value.forEach(item -> {
                String s = item.get(0);
                int l = item.size();
                boolean is_habit=false;
                if(item.get(l-1).equals("is_habit")){
                    is_habit=true;
                    l--;
                }
                AbstractActivities abs = null;
                switch (s) {
                    case "tra":
                        if (l == 3) {
                            abs = new TransportActivities(item.get(1), Integer.parseInt(item.get(2)));
                        } else if (Objects.equals(item.get(3), "true")) {
                            abs = new TransportActivities(item.get(1), Integer.parseInt(item.get(2)), true);
                        } else if (Objects.equals(item.get(3), "false")) {
                            abs = new TransportActivities(item.get(1), Integer.parseInt(item.get(2)), false);
                        } else {
                            abs = new TransportActivities(item.get(1), Integer.parseInt(item.get(2)), item.get(3));
                        }
                        break;
                    case "foo":
                        abs = new FoodConsumptionActivities(item.get(1), Integer.parseInt(item.get(2)));
                        break;
                    case "sho":
                        abs = new ShoppingActivities(item.get(1), Integer.parseInt(item.get(2)));
                        break;
                    case "bill":
                        abs = new MonthlyBillActivities(item.get(1),Integer.parseInt(item.get(2)));
                        break;
                }
                ArrayList<Integer> date = decode_date(key);
                try {
                    abs.year = date.get(0);
                    abs.month = date.get(1);
                    abs.day = date.get(2);
                    abs.is_habit=is_habit;
                    activities.add(abs);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        });
    }
}
