package com.plantze.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.plantze.app.R;

public class AnnualEmissionsMessage extends AppCompatActivity {
    private Button Next;
    private TextView Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_annual_emissions_message);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Message = findViewById(R.id.Message);
            Next = findViewById(R.id.Next);
            Message.setText("Hi! Now we will ask you a few questions to calucate your annual Emissions");
            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent fp=new Intent(getApplicationContext(),GetLocation.class);

                    startActivity(fp);
                }
            });
            return insets;
        });
    }
}