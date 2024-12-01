package com.Plantze.tracker;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcoTrackerRecyclerAdapter extends RecyclerView.Adapter<EcoTrackerRecyclerAdapter.ViewHolder>{

    public StoredData d;
    public StoredData local_d;
    public String date;
    private WeakReference<TextView> externalCO2TextView;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public Button deleteButton;
        public Button editButton;
        public TextView activityType;
        public TextView subType;
        public TextView value;
        public TextView sub_sub_type;

        Button selectDateButton;
        public ViewHolder(@NonNull View v) {
            super(v);
            deleteButton=v.findViewById(R.id.itemLayoutDelete);
            editButton=v.findViewById(R.id.itemLayoutEdit);
            activityType=v.findViewById(R.id.textViewActivityType);
            subType=v.findViewById(R.id.textViewSubType);
            value=v.findViewById(R.id.textViewValue);
            sub_sub_type=v.findViewById(R.id.textViewSubSubType);
        }


    }
    public EcoTrackerRecyclerAdapter(Context context,String date){

        d=EcoTrackerApplication.getInstance(context).getStoredData();
        this.context=context;
        this.date=date;
        Map<String,List<List<String>>>activityStrings=new HashMap<>();
        Log.d(TAG, "EcoTrackerRecyclerAdapter: read s_data"+d.s_data.toString());
        if(d.s_data.containsKey(date)){
            activityStrings.put(date,d.s_data.get(date));
            Log.d(TAG, "EcoTrackerRecyclerAdapter: "+d.s_data.get(date).toString());
        }
        local_d=new StoredData(activityStrings);
        

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AbstractActivities a=local_d.activities.get(position);
        Log.d(TAG, "onBindViewHolder: activity_created"+ a.getClass());
        //Log.d(TAG, "onBindViewHolder: transportation_activity_test"+((TransportActivities)a).vehicle);

        String activityType = "";
        String subSubType="";
        holder.subType.setText(a.sub_type);
        holder.value.setText("" + a.value);
        if(a.getClass()==TransportActivities.class){
            activityType="Transportation";
            if(a.sub_type=="car"){
                subSubType=((TransportActivities) a).vehicle;
            }else if(a.sub_type=="plane"){
                if(((TransportActivities) a).is_long_haul_flight){
                    subSubType="long haul";
                }else{
                    subSubType="short haul";
                }
            } else if (a.sub_type=="") {
                
            }
        } else if (a.getClass()==ShoppingActivities.class) {
            activityType="Shopping";
        }else if (a.getClass()==FoodConsumptionActivities.class){
            activityType="Food";
        }else if(a.getClass()==MonthlyBillActivities.class){
            activityType="Monthly Bill";
        }
        if(a.is_habit){
            activityType=activityType+" (Unfinished Habit)";
        }
        holder.activityType.setText(activityType);
        holder.sub_sub_type.setText(subSubType);
        if(activityType.equals("Monthly Bill")){
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }

        //TODO:finish the two buttons
        holder.editButton.setOnClickListener(v->{
            Intent intent = null;
            if(a.getClass()==TransportActivities.class){
                intent=new Intent(context,TransportationPageActivity.class);
            } else if (a.getClass()==ShoppingActivities.class) {
                intent=new Intent(context,ShoppingPageActivity.class);
            } else if (a.getClass()==FoodConsumptionActivities.class) {
                intent=new Intent(context,FoodPageActivity.class);
            }

            intent.putExtra("date",date);
            d.delete_activity(a);
            refresh_local_dataset();
            notifyItemRemoved(position);
            TextView CO2TextView=externalCO2TextView.get();
            CO2TextView.setText("Total Emissions Today: "+(int)d.getCO2(date)+" Kg");
            context.startActivity(intent);

        });
        holder.deleteButton.setOnClickListener(v->{
            d.delete_activity(a);
            refresh_local_dataset();
            notifyItemRemoved(position);
            TextView CO2TextView=externalCO2TextView.get();
            CO2TextView.setText("Total Emissions Today: "+(int)d.getCO2(date)+" Kg");
        });

    }

    @Override
    public int getItemCount() {
        return local_d.activities.size();
    }

    public void setExternalCO2TextView(TextView textView){
        this.externalCO2TextView=new WeakReference<>(textView);
    }
    private void refresh_local_dataset(){
        Map<String,List<List<String>>>activityStrings=new HashMap<>();
        Log.d(TAG, "EcoTrackerRecyclerAdapter: read s_data"+d.s_data.toString());
        if(d.s_data.containsKey(date)){
            activityStrings.put(date,d.s_data.get(date));
            Log.d(TAG, "EcoTrackerRecyclerAdapter: "+d.s_data.get(date).toString());
        }
        local_d=new StoredData(activityStrings);

    }
}
