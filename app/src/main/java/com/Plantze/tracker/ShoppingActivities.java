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
                break;
            case "electronics":
                switch (value){
                    case 0:
                        CO2+=0;
                        break;
                    case 1:
                        CO2+=300;
                        break;
                    case 2:
                        CO2+=600;
                        break;
                    case 3:
                        CO2+=900;
                        break;
                    default:
                        CO2+=1200;
                        break;
                }
            case "other":
                CO2+=value*100;
                break;
        }
        return CO2;
    }
}
