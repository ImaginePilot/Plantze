package com.example.LoginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07demosummer2024.R;

public class Login extends AppCompatActivity implements MainActivityContract.Login {

    MainActivityPresenter presenter;
    private Button login;
    private Button forgotPassword;

    private EditText Email;
    private EditText Password;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            presenter = new MainActivityPresenter(this,null,new Model());

            Email = findViewById(R.id.Email);
            Password = findViewById(R.id.Password);
            message = findViewById(R.id.Message);
            forgotPassword = findViewById(R.id.ForgotPassword);
            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.ForgotPassword();
                }
            });


            login = findViewById(R.id.Login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.login();
                }
            });

            return insets;
        });
    }

    public String getEmail()
    {
        String email = Email.getText().toString();
        return email;
    }
    public String getPassword()
    {
        String password = Password.getText().toString();
        return password;
    }

    public void DisplayMessage(String Message)
    {
        message.setText(Message);
    }

    public void NextActivity(String Email){

        Intent fp=new Intent(getApplicationContext(),GetLocation.class);
        fp.putExtra("Email",Email);
        startActivity(fp);
    }
}