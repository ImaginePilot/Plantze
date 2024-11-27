package com.example.LoginPage;

import android.content.Intent;

import java.util.Objects;

public class MainActivityPresenter {

    public Login login;
    public Registration register;

    public Model model;
    public MainActivityPresenter(Login login, Registration register, Model model){
        this.login = login;
        this.model = model;
        this.register = register;
    }
    public void ForgotPassword(){
        String Email = login.getEmail();
        if(Email.isEmpty())
            login.DisplayMessage("Email cannot be empty");
        else
        {
            model.SendForgotPasswordEmail(Email);

            login.DisplayMessage("If your are a registered user an email has been sent to you to rest your password");
        }

    }
    public void login(){
        int signIn ;
        String Email = login.getEmail();
        String Password = login.getPassword();
        if(Email.isEmpty() || Password.isEmpty() ){
            login.DisplayMessage("Feild or Feilds Cannot be empty");
        }

        signIn = model.SignIn(Email,Password);
        if (signIn == 1) {
            login.NextActivity();
        }
        if (signIn == -1) {
            login.DisplayMessage("Sign in not sucessful");
        }
        if (signIn == 2){
            login.DisplayMessage("Please verify email");
        }

    }
    public void signup(){
        String Email = register.getEmail1();
        String Password = register.getPassword1();
        String ConfirmPassword = register.getConfirmPassword();
        if(Objects.equals(Password, ConfirmPassword))
            model.signup(Email, Password);
        else
            register.MakeToast("Passwords dont Match");
    }
}
