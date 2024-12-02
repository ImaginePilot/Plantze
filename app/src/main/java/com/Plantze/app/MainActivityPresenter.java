package com.plantze.app;

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


        }

    }
    public void ForgotPasswordEmailSent(){
        login.DisplayMessage("An email to reset your password has been sent");
    }
    public void login() {
        String Email = login.getEmail();
        String Password = login.getPassword();
        if(Email.isEmpty() || Password.isEmpty() ){
            login.DisplayMessage("Feild or Feilds Cannot be empty");
        }
        else {
            model.SignIn(Email, Password);
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
    public void RegistrationEmailSent(){
        register.MakeToast("An email containing verfication link has been sent to the email address");
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
