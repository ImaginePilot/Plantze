package com.example.LoginPage;

import android.content.Intent;

import java.util.Objects;

public class MainActivityPresenter {

    public Login login;
    public Registration register;

    public Model model;
    public MainActivityPresenter(Login login, Registration register,Model model){
        this.login = login;
        this.register = register;
        this.model = model;
        model.PS(this);
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
    public void login() {
        String Email = login.getEmail();
        String Password = login.getPassword();
        if(Email.isEmpty() || Password.isEmpty() ){
            login.DisplayMessage("Feild or Feilds Cannot be empty");
        }

        model.SignIn(Email,Password);


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
    public void loginSucessful(String Uid){
        model.GlobalEmissionsData(Uid);
    }
    public void DataDosentExist(String Uid){
        login.NextActivity(Uid);
    }
    public void DataExists(String Uid){
        login.NextActivity1(Uid);
    }
    public void loginNotSucessful(){
        login.DisplayMessage("Sign in not sucessful");
    }
    public void EmailNotVerified(){
        login.DisplayMessage("Please verify email");
    }
}
