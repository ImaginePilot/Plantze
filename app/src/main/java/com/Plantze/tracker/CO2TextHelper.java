package com.Plantze.tracker;

import android.widget.TextView;

public class CO2TextHelper {

    private TextView co2TextView;

    public CO2TextHelper(TextView co2TextView) {
        this.co2TextView = co2TextView;
    }

    // Method to update the CO2 value text based on selected category
    public void updateCO2Text(CategoryData categoryData) {
        String co2Value = "";

        float transportationValue = categoryData.getTransportation();
        float energyUseValue = categoryData.getEnergyUse();
        float foodConsumptionValue = categoryData.getFoodConsumption();
        float shoppingValue = categoryData.getShopping();

        co2Value = (int)(transportationValue + energyUseValue + foodConsumptionValue + shoppingValue) + "kg CO2";

        // Update the TextView with the new CO2 value
        co2TextView.setText(co2Value);
    }
}

