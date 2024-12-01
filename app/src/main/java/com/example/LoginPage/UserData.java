package com.example.LoginPage;

import java.io.Serializable;

public class UserData implements Serializable {
    public String Uid;
    public String Location;
    public double Country_Emissions=0;
    public int[] UserAnswers = new int[24];
    public double personal_vehicle, public_Transportation, flights, Transportation;
    public double Beef, Pork, Chicken, Fish, Leftovers, Food;
    public double RenewableEnergy, Housing;
    public double Clothes, Electronics, Consumption,Consumption1,SecondHand;
    public double Total, avgComparision;


    public UserData() {

    }

    public void compute_results() {
        Compute_Transportation();
        ComputeFood();
        ComputeConsumption();
        Compute_Housing();
        TotalComputation();
    }

    public double value_returner(int choice, double[] values) {
        double generic_value = 0;
        if (values.length > 0 && choice == 1)
            generic_value = values[0];
        if (values.length > 1 && choice == 2)
            generic_value = values[1];
        if (values.length > 2 && choice == 3)
            generic_value = values[2];
        if (values.length > 3 && choice == 4)
            generic_value = values[3];
        if (values.length > 4 && choice == 5)
            generic_value = values[4];
        if (values.length > 5 && choice == 6)
            generic_value = values[5];
        return generic_value;
    }

    void Compute_Transportation() {
        if (UserAnswers[0] == 2)
            personal_vehicle = 0;
        else {
            double[] Fuel_values = {0.24, 0.27, 0.16, 0.05};
            double Emission_factor = value_returner(UserAnswers[1], Fuel_values);
            double[] Distance_values = {5000, 10000, 15000, 20000, 25000, 35000};
            double Distance_factor = value_returner(UserAnswers[2], Distance_values);
            personal_vehicle = Emission_factor * Distance_factor;
        }
        if (UserAnswers[3] == 1)
            public_Transportation = 0;
        if (UserAnswers[3] == 2) {
            double[] T = {246, 819, 1638, 3071, 4095};
            public_Transportation = value_returner(UserAnswers[4], T);
        }
        if (UserAnswers[3] == 3) {
            double[] T = {573, 1191, 3822, 7166, 9555};
            public_Transportation = value_returner(UserAnswers[4], T);
        }
        if (UserAnswers[3] == 4) {
            double[] T = {573, 1191, 3822, 7166, 9555};
            public_Transportation = value_returner(UserAnswers[4], T);
        }

        double[] S_flightValues = {0, 225, 600, 1200, 1800};
        flights = value_returner(UserAnswers[5], S_flightValues);
        double[] L_flightValues = {0, 825, 2200, 4400, 6600};
        flights = flights + value_returner(UserAnswers[6], L_flightValues);
        Transportation = personal_vehicle + public_Transportation + flights;
    }

    void ComputeFood() {
        if (UserAnswers[7] == 1)
            Food = 1000;
        else if (UserAnswers[7] == 2)
            Food = 500;
        else if (UserAnswers[7] == 3) {
            Food = 1500;
        }
        else {
            double[] BeefValues = {2500, 1900, 1300, 0};
            Beef = value_returner(UserAnswers[8], BeefValues);
            double[] PorkValues = {1450, 860, 450, 0};
            Pork = value_returner(UserAnswers[9], PorkValues);
            double[] ChickenValues = {950, 600, 200, 0};
            Chicken = value_returner(UserAnswers[10], ChickenValues);
            double[] FishValues = {800, 500, 150, 0};
            Fish = value_returner(UserAnswers[11], FishValues);
            Food = Beef + Pork + Chicken + Fish;
        }
        double[] Food_waste_values = {0, 23.4, 70.7, 140.4};
        Food = Food + value_returner(UserAnswers[12], Food_waste_values);
    }
    void ComputeConsumption(){
        double[] Clothes_values = {360,120,100,5};
        Clothes = value_returner(UserAnswers[20],Clothes_values);
        if(UserAnswers[21] == 1)
           {SecondHand = Clothes/2;}
        if(UserAnswers[21] == 2)
           {SecondHand = Clothes*0.3;}
        double[] Electronic_values = {0,300,600,900,1200};
        Electronics = value_returner(UserAnswers[22],Electronic_values);
        Consumption = Clothes+Electronics-SecondHand;
        if (UserAnswers[23] == 2 )
          {Consumption1 = Consumption*0.15;}
        if(UserAnswers[23] == 3)
           {Consumption1 = Consumption*0.30;}
        if(UserAnswers[23] == 4)
            {Consumption1 = Consumption*0.40;}
        Consumption = Consumption - Consumption1;
    }
    void Compute_Housing(){
        if(!(UserAnswers[16] == UserAnswers[18]))
        {
            Housing = Housing+233;
        }
        if(UserAnswers[19] == 1) {
            RenewableEnergy = 6000;
        }
        if(UserAnswers[19] ==2){
            RenewableEnergy =3000;
        }
        Housing = Housing - RenewableEnergy;
    }
    void TotalComputation(){
        Total = Transportation+Food+Housing+Consumption;
        avgComparision = (Total-Country_Emissions)/100;
    }
    String getMessage(){
        if(avgComparision > 0){
            return "Your emissions are "+avgComparision+"% greater than the average for "+Location;
        }
        else if (avgComparision == 0) {

            return "Your emissions are the same as average for "+Location;
        }
        else{
            double temp = -1*avgComparision;
            return "Your emissions are "+temp+"% lower that the average for "+Location;
        }
    }
}
