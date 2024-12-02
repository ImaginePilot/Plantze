package com.Plantze.app;

import static java.lang.Double.parseDouble;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.plantze.app.R;

import java.io.IOException;
import java.io.InputStream;

public class Global_emission_result extends AppCompatActivity {

    TextView transport,publicT,privateT,flights;// Variables related to Transportation
    TextView food,beef,pork,chicken,fish,leftovers;// Variables related to food
    TextView housing,renewable; //Variables related to housing
    TextView consumption,clothes,secondhand,electronics,recycle; //V
    TextView TotalComparision;
    UserData User_Data = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_global_emission_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            User_Data = (UserData) getIntent().getSerializableExtra("Data"); //Obtaining data
            User_Data.Housing = parseDouble(ComputeHousing(User_Data.UserAnswers));
            User_Data.compute_results();
            transport = findViewById(R.id.Transportation);
            publicT = findViewById(R.id.PublicTransportation);
            privateT = findViewById(R.id.PersonalVehicle);
            flights = findViewById(R.id.Flights);

            food = findViewById(R.id.Food);
            beef = findViewById(R.id.Beef);
            pork = findViewById(R.id.Pork);
            chicken= findViewById(R.id.Chicken);
            fish = findViewById(R.id.Fish);
            leftovers = findViewById(R.id.LeftOvers);

            housing= findViewById(R.id.Housing);
            renewable = findViewById(R.id.RenewableEnergy);

            consumption = findViewById(R.id.Consumption);
            clothes = findViewById(R.id.Clothes);
            secondhand = findViewById(R.id.SecondHandGoods);
            electronics = findViewById(R.id.Electronics);
            recycle = findViewById(R.id.Recycle);

            TotalComparision = findViewById(R.id.TotalComparision);


            transport.setText("Emissions from transportation breakdown: "+User_Data.Transportation);
            publicT.setText("Emissions from car: "+User_Data.personal_vehicle);
            privateT.setText("Emissions from Public transport"+User_Data.public_Transportation);
            flights.setText("Emission by flights: "+User_Data.flights);

            food.setText("Emissions by food breakdown: "+User_Data.Food);
            beef.setText("Emission by beef: "+User_Data.Beef);
            pork.setText("Emissions by pork: "+User_Data.Pork);
            chicken.setText("Emissions by chicken: "+User_Data.Chicken);
            fish.setText("Emissions by fish: "+User_Data.Fish);
            leftovers.setText("Emissions from leftovers: "+User_Data.Leftovers);

            housing.setText("Emissions by housing breakdown: "+ComputeHousing(User_Data.UserAnswers));
            renewable.setText("Emissions decrease by renewable: "+User_Data.RenewableEnergy);

            consumption.setText("Emissions by consumption breakdown: "+User_Data.Consumption);
            clothes.setText("Emissions by clothes :"+User_Data.Clothes);
            secondhand.setText("Emissions by reduction eco friendly goods: "+User_Data.SecondHand);
            electronics.setText("Emissions by electronics: "+User_Data.Electronics);
            recycle.setText("Emissions reduction by recycle: "+User_Data.Consumption1);

            TotalComparision.setText(User_Data.getMessage());

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child(User_Data.Uid);
            FirebaseUserData Data = new FirebaseUserData(User_Data.Transportation,User_Data.Food,User_Data.Housing,User_Data.Consumption);
            ref.child("AnnualEmissionsData").setValue(Data);


            return insets;
        });
    }

    String ComputeHousing(int [] UserAnswers){
        //double housing = 0;
        int TypeofHome = 4;
        int SizeofHome = 3;
        int NofOccupants = 4;
        int HeatingType = 5;
        int ElectrictyBill = 5;
        String [][][][][] Housing_Computation_array = new String[TypeofHome][SizeofHome][NofOccupants][ElectrictyBill][HeatingType];
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            CellAddress cellAdress = new CellAddress("C13");
            inputStream = assetManager.open("Copy of Formulas.xls");
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(2);
            int rowStart = 0; // POI rows are 0 based
            int rowEnd = 48;
            int colStart = 0;
            int colEnd = 24;
            int rowcounter = 0;
            //No of choices for each question


            //Counter for eacho one of them
            int TH_ct = 0;
            int SH_ct = 0;
            int NofOcc_ct = 0;
            int Heat_ct = 0;
            int Elect_ct = 0;
            int rowNum = rowStart;
            int cn = 0;
            while(rowNum<rowEnd) {
                Row r = sheet.getRow(rowNum);
                Heat_ct=0;
                Elect_ct=0;
                //  NofOcc_ct=0;
                cn =0;
                while(cn < 25){
                    if(Heat_ct == 5) {
                        Heat_ct = 0;
                        Elect_ct++;
                    }
                    Housing_Computation_array[TH_ct][SH_ct][NofOcc_ct][Elect_ct][Heat_ct] = r.getCell(cn).toString();
                    Heat_ct++;

                    cn++;
                }
                rowcounter++;
                rowNum++;
                NofOcc_ct++;
                if(rowcounter == 4){
                    NofOcc_ct =0;
                    rowcounter=0;
                    SH_ct++;
                    if(SH_ct == 3) {
                        SH_ct = 0;
                        if(TH_ct < 4) {
                            TH_ct++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Housing_Computation_array[UserAnswers[13]-1][UserAnswers[15]-1][UserAnswers[14]-1][UserAnswers[17]-1][UserAnswers[16]-1];

    }

}
