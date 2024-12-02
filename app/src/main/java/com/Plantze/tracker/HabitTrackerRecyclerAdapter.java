package com.Plantze.tracker;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HabitTrackerRecyclerAdapter extends RecyclerView.Adapter<HabitTrackerRecyclerAdapter.ViewHolder> {
    public StoredData d;
    public StoredData local_d;
    public String date;
    public Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button trackButton;
        public TextView title;
        public TextView description;

        public ViewHolder(@NonNull View v) {
            super(v);
            trackButton = v.findViewById(R.id.trackHabitButton);
            title = v.findViewById(R.id.textHabitTitle);
            description = v.findViewById(R.id.descriptionHabitTextView);
        }

    }

    public HabitTrackerRecyclerAdapter(Context context, String date, StoredData local_d) {
        d = EcoTrackerApplication.getInstance(context).getStoredData();
        this.context = context;
        this.date = date;
        this.local_d = local_d;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_habit, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AbstractActivities a = local_d.activities.get(position);
        a.generateDescription();
        String activityType = "";
        String subSubType = "";
        Log.d(TAG, "onBindViewHolder: working with activitity"+a.toString());
        if (a.getClass() == TransportActivities.class) {
            activityType = "Transportation";
            if (a.sub_type == "car") {
                subSubType = ((TransportActivities) a).vehicle;
            } else if (a.sub_type == "plane") {
                if (((TransportActivities) a).is_long_haul_flight) {
                    subSubType = "long haul";
                } else {
                    subSubType = "short haul";
                }
            }
        } else if (a.getClass() == ShoppingActivities.class) {
            activityType = "Shopping";
        } else if (a.getClass() == FoodConsumptionActivities.class) {
            activityType = "Food";
        }
        Log.d(TAG, "onBindViewHolder: Holder"+holder);
        Log.d(TAG, "onBindViewHolder: title"+holder.title);

        holder.title.setText(activityType + " " + a.sub_type + " " + subSubType);
        holder.description.setText(a.description + "\n Default value: " + a.value);
        holder.trackButton.setOnClickListener(v -> {
            a.is_habit=true;
            List<String> next7days=getNext7Days(date);
            for(String day:next7days){
                AbstractActivities copy=DeepCopyUtil.deepCopy(a);
                copy.year=StoredData.decode_date(day).get(0);
                copy.month=StoredData.decode_date(day).get(1);
                copy.day=StoredData.decode_date(day).get(2);
                d.single_write_to_mapping(copy);
            }
            Toast.makeText(context,"Successfully added new habit for next 7 days", Toast.LENGTH_SHORT);
        });

    }
    @Override
    public int getItemCount(){return local_d.activities.size();}


    public List<String> getNext7Days(String startDate) {
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }

        // Parse the start date
        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.parse(startDate, formatter);
        }

        // Add next 7 days (including start date)
        for (int i = 0; i < 7; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dates.add(date.plusDays(i).format(formatter));
            }
        }

        return dates;
    }

    public void updateData(StoredData new_d){
        this.local_d=new_d;
        Log.d(TAG, "updateData: "+local_d.s_data.toString());
        notifyDataSetChanged();
    }
}