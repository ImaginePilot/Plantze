package com.Plantze.tracker;

public class FoodConsumptionActivities extends AbstractActivities{
    public FoodConsumptionActivities(String s, int value) {
        super(s, value);
    }

    private int filter_values(int a, int b, int c){
        if(value<=2){
            return a;
        }else if(value<=5){
            return b;
        }else{
            return c;
        }
    }
    public double calculateCO2(){
        CO2=0;
        if(value==0){
            CO2=0;
            return CO2;
        }
        switch(sub_type){
            case "beef":
                CO2+=filter_values(1300,1900,2500);
                break;
            case "pork":
                CO2+=filter_values(450,860,1450);
                break;
            case "chicken":
                CO2+=filter_values(200,600,950);
                break;
            case "fish":
                CO2+=filter_values(150,500,800);
                break;
        }
        return CO2;
    }
}
