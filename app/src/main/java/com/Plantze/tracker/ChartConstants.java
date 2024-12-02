package com.Plantze.tracker;

import android.graphics.Color;

public class ChartConstants {
    public static final String[] CATEGORIES = {"Daily", "Weekly", "Monthly", "Yearly"};
    public static String UID = "123456";
    public static String SELECTED_DATE = "2024-12-01";
    public static int SELECTED_DAY = ChartConstants.getDay(SELECTED_DATE);
    public static int SELECTED_MONTH = ChartConstants.getMonth(SELECTED_DATE);
    public static int SELECTED_YEAR = ChartConstants.getYear(SELECTED_DATE);

    public static int getYear(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }

    // Method to extract the month from a date
    public static int getMonth(String date) {
        return Integer.parseInt(date.substring(5, 7));
    }

    // Method to extract the day from a date
    public static int getDay(String date) {
        return Integer.parseInt(date.substring(8, 10));
    }

    public static final int[] COLORS = {
            Color.parseColor("#a9bcd0"),
            Color.parseColor("#009999"),
            Color.parseColor("#373f51"),
            Color.parseColor("#1b1b1e")
    };
}
