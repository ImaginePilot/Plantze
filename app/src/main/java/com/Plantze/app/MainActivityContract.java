package com.plantze.app;

public interface MainActivityContract {
    interface Login{
        String getEmail();
        String getPassword();
        void DisplayMessage(String message);
        void NextActivity(String Email);
    }
    interface Register{
        String getEmail1();
        String getPassword1();
        String getConfirmPassword();
    }
    interface Presenter{
        void login();
        void ForgotPassword();
    }
    interface Model{
        int SignIn(String Email, String Password);
        void SendForgotPasswordEmail(String Email);



    }

}
