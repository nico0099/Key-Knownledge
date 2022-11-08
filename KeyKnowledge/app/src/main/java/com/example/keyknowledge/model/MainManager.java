package com.example.keyknowledge.model;


import com.example.keyknowledge.R;
import com.example.keyknowledge.control.*;

import static com.example.keyknowledge.model.UserManager.OFFLINE;
import static com.example.keyknowledge.model.UserManager.ONLINE;


public class MainManager {

    private UserManager manager;
    private MainControl control;
    public MainManager(MainControl c){
        manager=new UserManager();
        control=c;
    }

    public MainManager(){}

    public void accessUser(String nick) {
        manager.getUser(nick,this);
    }

    public void accessUser(User user){
        if(user==null){
            control.setMessage("L'utente "+user.getNickname()+" non esiste");
        }else{
            user.setStato(ONLINE);
            manager.setState(ONLINE,user.getNickname());
            control.setView(R.layout.home,user);
        }
    }

    public void logout(User user) {
        manager.setState(OFFLINE,user.getNickname());
    }
}
