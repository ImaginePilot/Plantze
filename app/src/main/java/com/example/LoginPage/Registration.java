package com.example.LoginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07demosummer2024.R;

public class Registration extends AppCompatActivity implements MainActivityContract.Register {

    MainActivityPresenter presenter;
    private Button Register, Login;
    private EditText Name;
    private EditText Email_RP;
    private EditText Password_RP, ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Name = (android.widget.EditText) findViewById(R.id.Name);

            Email_RP = (android.widget.EditText) findViewById(R.id.Email);

            Model model = new Model();
            presenter = new MainActivityPresenter(null,this,model);
            Password_RP = (android.widget.EditText) findViewById(R.id.Password);
            ConfirmPassword = (android.widget.EditText) findViewById(R.id.ConfirmPassword);
            Register = (android.widget.Button) findViewById(R.id.Register);
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.signup();
                }
            });
            Login = findViewById(R.id.Login);
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fp=new Intent(getApplicationContext(),Login.class);
                    startActivity(fp);
                }
            });

            return insets;
        });
    }
    public String getEmail1(){
        return Email_RP.getText().toString();
    }
    public  String getPassword1(){
        return Password_RP.getText().toString();
    }
    public String getConfirmPassword(){
        return ConfirmPassword.getText().toString();
    }
    public void MakeToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}