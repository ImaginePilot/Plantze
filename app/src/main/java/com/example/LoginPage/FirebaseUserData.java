package com.example.LoginPage;

// Contains the simplified data to be uploaded to firebase
public class FirebaseUserData {
    public double Transportation,Food,Housing,Consumption;
    FirebaseUserData(double Transportation, double Food, double Housing, double Consumption){
        this.Transportation = Transportation;
        this.Consumption= Consumption;
        this.Food = Food;
        this.Housing = Housing;
    }
}
