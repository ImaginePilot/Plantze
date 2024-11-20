package com.example.b07demosummer2024;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment {
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        TextView overviewMonth = view.findViewById(R.id.overviewMonth);
        double emittedCO2e = 22.10; //replace by data from database
        overviewMonth.setText("You have emitted " + emittedCO2e + " kg CO2e this month!");
        LineChart lineChartMonth = view.findViewById(R.id.lineChartMonth);
        List<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i,i));
        } //Replace by data from database

        XAxis xAxis = lineChartMonth.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setLabelCount(entries.size());
        lineChartMonth.setScaleEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, "CO2e emission");
        LineData lineData = new LineData(dataSet);

        Description description = lineChartMonth.getDescription();
        description.setText("Monthly CO2e emitted analyze");

        lineChartMonth.setData(lineData);
        lineChartMonth.invalidate();

//        testButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                testText1.setText("You have clicked on the button!");
//            }
//        });

        return view;
    }
}
