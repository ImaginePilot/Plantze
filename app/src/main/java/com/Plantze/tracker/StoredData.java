package com.Plantze.tracker;



import android.content.Context;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StoredData {
    //Stores Data and add data to FireBase
    private FirebaseStorage storage;
    private StorageReference global_db;
    public Map<String,List<List<String>>> s_data=new HashMap<>();
    public List<AbstractActivities> activities=new ArrayList<>();
    public StoredData(String uid){
        FirebaseApp.initializeApp();
        this.storage = FirebaseStorage.getInstance();
        this.global_db=storage.getReference(uid);
    }
    public Map<String,List<List<String>>> read_from_file(){
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            File file=new File("data.json");
            Map<String,List<List<String>>> local_s_data=objectMapper.readValue(file,new TypeReference<Map<String, List<List<String>>>>() {});
            s_data=local_s_data;
        }catch(IOException e){
            e.printStackTrace();
        }
        return s_data;
    }
    public void write_to_mapping(String date,List<List<String>> params){
        if(s_data.containsKey(date)){
            List<List<String>> new_params=new ArrayList<>(s_data.get(date));
            new_params.addAll(params);
            s_data.put(date,new_params);
        }else{
            s_data.put(date,params);
        }

    }
    public void single_write_to_mapping(AbstractActivities a){
        String date=Integer.toString(a.year)+Integer.toString(a.month)+Integer.toString(a.day);
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
        }
        ArrayList<String> res=new ArrayList<>();
        res.add(class_name);
        res.add(sub_type);
        res.add(value);
        if(marker){
            res.add(extra_value);
        }
        List<List<String>> l=new ArrayList<>();
        l.add(res);
        write_to_mapping(date,l);
    }
    public void flush_to_file(){
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            File file=new File("data.json");
            objectMapper.writeValue(file,s_data);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> decode_date(String date){
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(9,11);
        ArrayList<Integer> res=new ArrayList<>();
        res.add(Integer.parseInt(year));
        res.add(Integer.parseInt(month));
        res.add(Integer.parseInt(day));
        return res;
    }

    public void create_activities() {
        s_data.forEach((key, value) -> {
            value.forEach(item -> {
                String s = item.get(0);
                int l = item.size();
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
                    case "foo":
                        abs = new FoodConsumptionActivities(item.get(1), Integer.parseInt(item.get(2)));
                    case "sho":
                        abs = new ShoppingActivities(item.get(1), Integer.parseInt(item.get(2)));
                }
                ArrayList<Integer> date = decode_date(key);
                try {
                    abs.year = date.get(0);
                    abs.month = date.get(1);
                    abs.day = date.get(2);
                    activities.add(abs);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        });
    }
}
