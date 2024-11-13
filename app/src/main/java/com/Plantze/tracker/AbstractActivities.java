package com.Plantze.tracker;

public abstract class AbstractActivities {
    public int year;
    public int month;
    public int day;
    public String sub_type;
    public int value;
    public double CO2=0;
    public AbstractActivities(String s,int value){
        sub_type=s;
        this.value=value;
    }
    public abstract double calculateCO2();
}

