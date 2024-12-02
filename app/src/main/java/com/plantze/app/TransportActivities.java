package com.plantze.app;

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
                        break;
                    case "diesel":
                        CO2+=value*0.27;
                        break;
                    case "hybrid":
                        CO2+=value*0.16;
                        break;
                    default:
                        CO2+=value*0.24;
                        break;
                }
                break;
            case "plane":
                CO2+=calculate_flight_CO2();
                break;
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
                break;
            case "cycling_or_walking":
                CO2+=value;
                break;

        }
        return CO2;
    }

    public void generateDescription(){
        switch (sub_type){
            case "car":
                switch(vehicle){
                    case "electric":
                        description="The most economic kind of car.";
                        break;
                    case "diesel":
                        description="The least economic kind of car.";
                        break;
                    case "hybrid":
                        description="Produces amount of CO2 in between electric and gasoline.";
                        break;
                    default:
                        description="Produces large amount of CO2, but better than diesel cars.";
                        break;
                }
                break;
            case "plane":
                description="Plane produces large amounts of CO2 when flying, but do you know it is better than cars when coming to per kilometer emission?.";
                break;
            case "public_transport":
                description="Sharing vehicles with others greatly reduces CO2 emission";
                break;
            case "cycling_or_walking":
                description="Produces almost no CO2. The best way of travelling!";
                break;

        }
    }
}
