package com.Plantze.tracker;

public class MonthlyBillActivities extends AbstractActivities{


    public MonthlyBillActivities(String sub_type,int value){
        super(sub_type,value);

    }
    @Override
    public double calculateCO2() {
        CO2=0;
        switch (sub_type){
            case "electricity":
                CO2+=2.8*value/30;
                break;
            case "water":
                CO2+=0.45*value/30;
                break;
            case "gas":
                CO2+=4.42*value/30;
                break;
        }
        return  CO2;
    }
}
