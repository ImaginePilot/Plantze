package com.example.b07demosummer2024;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OverviewFragment extends Fragment {
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        //Overview title
        double emittedCO2e;
        emittedCO2e = 1122.10; //replace by data from database
        TextView overviewMonth = view.findViewById(R.id.overviewMonth);
        overviewMonth.setText("You have emitted " + emittedCO2e + " kg CO2e this month!");

        //Line graph
        //Setup data variables
        final double AVGAMITTEDCO2 = 1166.67; //Avg CO2e emission per person monthly
        LineChart lineChartMonth = view.findViewById(R.id.lineChartMonth);
        List<Entry> entriesLine = new ArrayList<Entry>();
        LimitLine avgEmittedCO2eLimit = new LimitLine((float)AVGAMITTEDCO2, "Global Average");

        //Set values
        for (int i = 0; i < 12; i++) {
            entriesLine.add(new Entry((float) i, (float) (1200 + Math.pow(-1, i) * 20 * i)));
        } //Replace by data from database

        //Set axises
        XAxis xAxis = lineChartMonth.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setLabelCount(entriesLine.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MonthValueFormatter());
        lineChartMonth.getAxisRight().setEnabled(false);

        //Set graph
        LineDataSet dataSetLine = new LineDataSet(entriesLine, "Emission Monthly");
        LineData lineData = new LineData(dataSetLine);
        lineChartMonth.setScaleEnabled(false);

        Description description = lineChartMonth.getDescription();
        description.setText("CO2e emitted analyze");

        lineChartMonth.setData(lineData);

        //Set limit line
        avgEmittedCO2eLimit.setLineColor(Color.RED);
        avgEmittedCO2eLimit.setLineWidth(2f);
        avgEmittedCO2eLimit.setTextColor(Color.BLACK);
        avgEmittedCO2eLimit.setTextSize(7f);
        lineChartMonth.getAxisLeft().addLimitLine(avgEmittedCO2eLimit);

        //Refresh to draw
        lineChartMonth.invalidate();

        //Pie chart
        //Set up data variables
        PieChart pieChart = view.findViewById(R.id.pieChart);
        ArrayList<PieEntry> entriesPie = new ArrayList<>();

        //Set values
        entriesPie.add(new PieEntry(500f, "Drive"));
        entriesPie.add(new PieEntry(400f, "Plane"));
        entriesPie.add(new PieEntry(20f, "Cook"));
        entriesPie.add(new PieEntry(102.1f, "Breathe"));
        /* Replace by data form database */

        PieDataSet dataSetPie = new PieDataSet(entriesPie, "");
        dataSetPie.setValueTextSize(8f);
        dataSetPie.setColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
//        dataSetPie.setDrawValues(false);
        dataSetPie.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        dataSetPie.setValueLinePart1Length(0.3f);
        dataSetPie.setValueLinePart2Length(0.4f);

        //Set chart
        PieData pieData = new PieData(dataSetPie);
        pieChart.setData(pieData);

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
//        pieChart.setDrawHoleEnabled(false);
        pieChart.animateY(1000);

        Description titlePie = new Description();
        titlePie.setText("Monthly Emission Breakdown");
        pieChart.setDescription(titlePie);

        //Refresh to draw
        pieChart.invalidate();

        return view;
    }
}
