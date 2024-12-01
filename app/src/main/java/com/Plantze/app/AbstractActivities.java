package com.plantze.app;

import java.io.Serializable;

public abstract class AbstractActivities implements Serializable {
    public int year;
    public int month;
    public int day;
    public String sub_type;
    public String description;
    public int value;
    public double CO2=0;
    public boolean is_habit=false;
    public AbstractActivities(String s,int value){
        sub_type=s;
        this.value=value;
    }
    public abstract double calculateCO2();
    public abstract void generateDescription();

}

