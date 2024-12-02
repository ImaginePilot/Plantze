package com.Plantze.tracker;

public class CategoryData {
    private float transportation;
    private float energyUse;
    private float foodConsumption;
    private float shopping;


    // Constructors, getters, and setters

    public CategoryData() {
        // Default constructor required for Firebase
    }
    public CategoryData(float transportation, float energyUse, float foodConsumption, float shopping) {
        this.transportation = transportation;
        this.energyUse = energyUse;
        this.foodConsumption = foodConsumption;
        this.shopping = shopping;
    }

    public float getTransportation() {
        return transportation;
    }

    public void setTransportation(float transportation) {
        this.transportation = transportation;
    }

    public float getEnergyUse() {
        return energyUse;
    }

    public void setEnergyUse(float energyUse) {
        this.energyUse = energyUse;
    }

    public float getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(float foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public float getShopping() {
        return shopping;
    }

    public void setShopping(float shopping) {
        this.shopping = shopping;
    }

    //public calculateTotalDailyCO2(){
        //StoredData s = new StoredData();

    //}
    //public calculateTotalWeeklyCO2(){

    //}
   // public calculateTotalMonthlyCO2(){

    //}
    //public calculateTotalYearlyCO2(){

    //}
}

