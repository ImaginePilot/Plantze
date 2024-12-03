package com.plantze.app;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        ecoTrackerButton = findViewById(R.id.ecoTrackerButton);
        ecoGuageButton = findViewById(R.id.ecoGuageButton);
        //ecoHubButton = findViewById(R.id.ecoHubButton);
        //ecoAgentButton = findViewById(R.id.ecoAgentButton);
        Log.d(TAG, "onCreate: ecotrackerbutton"+ecotrackerButton);
        annualCarbonButton.setOnClickListener(v->{

                Intent intent = new Intent(this,AnnualEmissionsMessage.class );
                startActivity(intent);

        });

        ecoTrackerButton.setOnClickListener(v->{
                Intent intent = new Intent(this,EcoTrackerActivity.class);
                startActivity(intent);

        });

        /*ecoGuageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(HomeMenu.this, );
                //startActivity(intent);
            }
        });*/
    }
}
