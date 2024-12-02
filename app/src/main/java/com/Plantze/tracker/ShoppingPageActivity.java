package com.Plantze.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingPageActivity extends AppCompatActivity {
    private EditText editServings;
    private Spinner spinner;
    private Button saveButton;
    private String subType;
    private String date;
    private StoredData d;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_shopping);
        editServings=findViewById(R.id.shoppingPageEditText);
        saveButton=findViewById(R.id.shoppingPageSaveButton);
        spinner= findViewById(R.id.spinnerShoppingPage);
        d=EcoTrackerApplication.getInstance(this).getStoredData();
        date=getIntent().getStringExtra("date");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        subType="clothes";
                        break;
                    case 1:
                        subType="electronics";
                        break;
                    case 2:
                        subType="other";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveButton.setOnClickListener(v->{
            int servings = 0;
            try{
                servings=Integer.parseInt(editServings.getText().toString().trim());
            }catch (Exception e){
                e.printStackTrace();
            }
            ShoppingActivities a=new ShoppingActivities(subType,servings);
            BillPageActivity.writeDate(a,date);
            d.single_write_to_mapping(a);

            Intent intent =new Intent(this,EcoTrackerActivity.class);
            intent.putExtra("date",date);
            startActivity(intent);

        });
    }
}
