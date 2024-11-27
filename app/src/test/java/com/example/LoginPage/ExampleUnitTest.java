package com.example.LoginPage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    Model model;
    @Mock
    Login login;
    @Mock
    Registration registration;

    @Test
    public void testSignUp() {

        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(registration.getEmail1()).thenReturn("rohitmutyala920@gmail.com");
        when(registration.getPassword1()).thenReturn("ABCD@1");
        when(registration.getConfirmPassword()).thenReturn("A");
        presenter.signup();
        verify(registration).MakeToast("Passwords dont Match");

    }

    @Test
    public void testSignup1(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(registration.getEmail1()).thenReturn("rohitmutyala920@gmail.com");
        when(registration.getPassword1()).thenReturn("ABCD@1");
        when(registration.getConfirmPassword()).thenReturn("ABCD@1");
        presenter.signup();
        verify(model).signup("rohitmutyala920@gmail.com","ABCD@1");
    }
    @Test
    public void testForgotPassword1(){
        MainActivityPresenter presenter= new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("rohitmutyala920@gmail.com");
        presenter.ForgotPassword();
        verify(model).SendForgotPasswordEmail("rohitmutyala920@gmail.com");
        verify(login).DisplayMessage("If your are a registered user an email has been sent to you to rest your password");

    }
    @Test
    public void testForgotPassword(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("");
        presenter.ForgotPassword();
        verify(login).DisplayMessage("Email cannot be empty");
    }
    @Test
    public void testlogin(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("");
        when(login.getPassword()).thenReturn("");
        presenter.login();
        verify(login).DisplayMessage("Feild or Feilds Cannot be empty");
    }
    @Test
    public void testlogin1(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("rohitmutyala920@gmail.com");
        when(login.getPassword()).thenReturn("");
        presenter.login();
        verify(login).DisplayMessage("Feild or Feilds Cannot be empty");
    }
    @Test
    public void testlogin2(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("");
        when(login.getPassword()).thenReturn("ABCD@1");
        presenter.login();
        verify(login).DisplayMessage("Feild or Feilds Cannot be empty");
    }
    @Test
    public void testlogin3(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("rohitmutyala920@gmail.com");
        when(model.SignIn("rohitmutyala920@gmail.com","ABCD@1")).thenReturn(-1);
        when(login.getPassword()).thenReturn("ABCD@1");
        presenter.login();
        verify(login).DisplayMessage("Sign in not sucessful");
    }
    @Test
    public void testlogin4(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("rohitmutyala920@gmail.com");
        when(model.SignIn("rohitmutyala920@gmail.com","ABCD@1")).thenReturn(1);
        when(login.getPassword()).thenReturn("ABCD@1");
        presenter.login();
        verify(login).NextActivity("rohitmutyala920@gmail.com");
    }
    @Test
    public void testlogin5(){
        MainActivityPresenter presenter = new MainActivityPresenter(login,registration,model);
        when(login.getEmail()).thenReturn("rohitmutyala920@gmail.com");
        when(model.SignIn("rohitmutyala920@gmail.com","ABCD@1")).thenReturn(2);
        when(login.getPassword()).thenReturn("ABCD@1");
        presenter.login();
        verify(model).SignIn("rohitmutyala920@gmail.com","ABCD@1");
        verify(login).DisplayMessage("Please verify email");
    }

}