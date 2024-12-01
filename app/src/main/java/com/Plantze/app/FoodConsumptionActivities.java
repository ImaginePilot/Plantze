package com.plantze.app;

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

    @Override
    public void generateDescription() {
        switch (sub_type){
            case "beef":
                description="Beef generates the most CO2 during production.";
                break;
            case "pork":
                description="Pork generates less CO2 than beef but more than chicken.";
                break;
            case "chicken":
                description="Chicken generates less CO2 than pork but more than fish.";
                break;
            case "fish":
                description="Fish generates the least CO2 during production";
                break;
        }
    }
}
