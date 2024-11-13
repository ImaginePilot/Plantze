package com.Plantze.tracker;

public class TransportActivities extends AbstractActivities{
    public String vehicle;
    public boolean is_long_haul_flight;
    public TransportActivities(String s, int value){
        super(s,value);
    }
    public TransportActivities(String s,int value, String v){
        //can be used for both car or public transportation
        super(s,value);
        this.vehicle=v;
    }
    public TransportActivities(String s,int value, boolean i){
        super(s,value);
        this.is_long_haul_flight=i;
    }
    private double calculate_flight_CO2(){
        if(is_long_haul_flight){
            if(value==0){
                return 0;
            }else if(value <=2 ){
                return  225;
            }else if(value <=5){
                return 600;
            }else if(value <=10){
                return 1200;
            }else return 1800;
        }else{
            if(value==0){
                return 0;
            }else if(value <=2 ){
                return  825;
            }else if(value <=5){
                return 2200;
            }else if(value <=10){
                return 4400;
            }else return 6600;
        }
    }
    public double calculateCO2(){
        this.CO2=0;
        switch (sub_type){
            case "car":
                switch(vehicle){
                    case "electric":
                        CO2+=value*0.05;
                    case "diesel":
                        CO2+=value*0.27;
                    case "hybrid":
                        CO2+=value*0.16;
                    default:
                        CO2+=value*0.24;
                }
            case "plane":
                CO2+=calculate_flight_CO2();
            case "public_transport":
                if(value<=1){
                    CO2+=246;
                }else if(value<=3){
                    CO2+=819;
                }else if(value<=5){
                    CO2+=1638;
                }else if(value<=10){
                    CO2+=3071;
                }else{
                    CO2+=4095;
                }
            case "cycling_or_walking":
                CO2+=0;

        }
        return CO2;
    }
}
