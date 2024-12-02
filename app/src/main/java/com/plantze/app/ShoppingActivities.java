package com.plantze.app;

public class ShoppingActivities extends AbstractActivities{
    public ShoppingActivities(String s, int value) {
        super(s, value);
    }

    public void generateDescription(){
        switch (sub_type){
            case "clothes":
                description="Buying clothes are normal. Each shirt creates 5kg of CO2 emission!";
                break;
            case "electronics":
                description="Buying electronics creates a lot of CO2 emission, each one creates 300kg of emission.";
                break;
            case "others":
                description="Any other stuff will do! We will assume it creates 100kg of emission.";
                break;
        }
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
