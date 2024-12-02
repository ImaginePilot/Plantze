package com.plantze.app;

import static java.lang.Double.parseDouble;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.plantze.app.R;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class GetLocation extends AppCompatActivity {

    public UserData Data = new UserData();
    Button Next;
    Spinner CountryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            CountryList = (android.widget.Spinner)findViewById(R.id.spinner);
            Next = (android.widget.Button)findViewById(R.id.gc);

            String Uid = getIntent().getStringExtra("Uid");
            Data.Uid = Uid;

            // Reading the countries and emissions for the excel file we were given
            ArrayList<String> countries_list = new ArrayList<>();
            ArrayList<String> emissions_list = new ArrayList<>();
            AssetManager assetManager = getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open("Global_averages - Copy.xls");
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                rowIterator.next();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    countries_list.add(row.getCell(0).toString());

                    emissions_list.add(row.getCell(1).toString());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // An adapter Containing all the countries user can select select from
            ArrayAdapter<String> adpater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,countries_list);
            CountryList.setAdapter(adpater);
            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int SelectedCountry = CountryList.getSelectedItemPosition();
                    Data.Location = countries_list.get(SelectedCountry);
                    Data.Country_Emissions = parseDouble(emissions_list.get(SelectedCountry));
                    Intent intent = new Intent(GetLocation.this, annualConsumption.class);
                    intent.putExtra("Data", Data);
                    startActivity(intent);
                }
            });

            return insets;
        });
    }

}