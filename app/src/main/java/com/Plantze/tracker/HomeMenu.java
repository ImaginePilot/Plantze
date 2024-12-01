package com.Plantze.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeMenu extends AppCompatActivity {
    private Button annualCarbonButton;
    private Button ecoTrackerButton;
    private Button ecoGuageButton;
    private Button ecoBalanceButton;
    private Button ecoHubButton;
    private Button ecoAgentButton;
    private Button ecotrackerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        annualCarbonButton = findViewById(R.id.annualCarbonButton);
        ecotrackerButton = findViewById(R.id.ecoTrackerButton);
        ecoGuageButton = findViewById(R.id.ecoGuageButton);
        ecoHubButton = findViewById(R.id.ecoHubButton);
        ecoAgentButton = findViewById(R.id.ecoAgentButton);

        annualCarbonButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(HomeMenu.this, );
                //startActivity(intent);
            }
        });

        ecoTrackerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(HomeMenu.this, );
                //startActivity(intent);
            }
        });

        ecoGuageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(HomeMenu.this, );
                //startActivity(intent);
            }
        });
    }
}
