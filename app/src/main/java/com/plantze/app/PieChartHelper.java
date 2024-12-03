package com.plantze.app;

import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import android.graphics.Color;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import android.graphics.Color;

import java.util.ArrayList;

public class PieChartHelper {

    private PieChart pieChart;

    public PieChartHelper(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    // Method to update the PieChart with CategoryData
    public void updatePieChart(CategoryData categoryData) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        // Adds data from CategoryData to PieEntry list, but skips entries with zero values
        if (categoryData.getTransportation() > 0) {
            entries.add(new PieEntry(categoryData.getTransportation(), "Transportation"));
            colors.add(Color.parseColor("#a9bcd0"));
        }
        if (categoryData.getEnergyUse() > 0) {
            entries.add(new PieEntry(categoryData.getEnergyUse(), "Energy Use"));
            colors.add(Color.parseColor("#009999"));
        }
        if (categoryData.getFoodConsumption() > 0) {
            entries.add(new PieEntry(categoryData.getFoodConsumption(), "Food Consumption"));
            colors.add(Color.parseColor("#373f51"));
        }
        if (categoryData.getShopping() > 0) {
            entries.add(new PieEntry(categoryData.getShopping(), "Shopping"));
            colors.add(Color.parseColor("#1b1b1e"));
        }

        // If no data exists (all values are 0), avoid rendering the chart
        if (entries.isEmpty()) {
            pieChart.setVisibility(View.GONE); // Hide the chart if no data
        } else {
            // Creates the PieDataSet with entries and colors
            PieDataSet pieDataSet = new PieDataSet(entries, "Carbon Footprint");
            pieDataSet.setColors(colors);
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueTextSize(18f);

            // Creates PieData and set it to the PieChart
            PieData pieData = new PieData(pieDataSet);
            pieChart.setUsePercentValues(true);
            pieData.setValueFormatter(new PercentFormatter(pieChart));
            pieChart.setData(pieData);

            // Optional: Customize PieChart appearance
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleRadius(16f);
            pieChart.setTransparentCircleRadius(20f);
            pieChart.animateY(1000);
            pieChart.getLegend().setEnabled(false);
            pieChart.setEntryLabelTextSize(18f);

            // Invalidate the PieChart to trigger a refresh
            pieChart.invalidate();
            pieChart.setVisibility(View.VISIBLE); // Ensure the chart is visible when there is data
        }
    }
}
