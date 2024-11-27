package com.example.b07demosummer2024;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * This class is the formatter for numbers from 0-11 to Jan-Dec
 */
public class MonthValueFormatter extends ValueFormatter{
    private final String[] months = {
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV","DEC"
    }; //Shorthand of months;

    @Override
    public String getFormattedValue(float value) {
        int index = (int) value;
        if (index >= 0 && index < 12) {
            return months[index];
        } else {
            return "";
        }
    }
}
