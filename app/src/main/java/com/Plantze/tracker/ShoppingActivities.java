package com.Plantze.tracker;

public class ShoppingActivities extends AbstractActivities{
    public ShoppingActivities(String s, int value) {
        super(s, value);
    }

    @Override
    public double calculateCO2() {
        CO2=0;
        switch(sub_type){
            case "clothes":
                CO2+=5;
            case "electronics":
                switch (value){
                    case 0:
                        CO2+=0;
                    case 1:
                        CO2+=300;
                    case 2:
                        CO2+=600;
                    case 3:
                        CO2+=900;
                    default:
                        CO2+=1200;
                }
            case "other":
                CO2+=value*100;
        }
        return CO2;
    }
}
