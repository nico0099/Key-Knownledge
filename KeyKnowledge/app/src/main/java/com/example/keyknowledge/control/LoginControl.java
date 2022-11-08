package com.example.keyknowledge.control;

import android.content.Intent;
import com.example.keyknowledge.Login;
import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.model.LoginManager;
import com.example.keyknowledge.model.User;

public class LoginControl {
    Intent i;
    private Login login;
    private LoginManager manager;
    private boolean flagIntent = true;
    public LoginControl(Login x){
        login=x;
        manager=new LoginManager(this);
    }

    public LoginManager getManager() {
        return manager;
    }

    public void setMessage(String x){
        login.message(x);
    }

    public void access(String nick,String pass){
        manager.accessUser(nick,pass);
    }

    public void saveUser(User user){
        login.saveUser(user);
        goHome();
    }

    private void setIntent(){
        if(flagIntent == true){
            i=new Intent(login.getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            login.startActivity(i);
        }else return;
    }

    public void setFlagIntent(boolean flagIntent) {
        this.flagIntent = flagIntent;
    }

    public void goHome() {
        setIntent();
    }

}
