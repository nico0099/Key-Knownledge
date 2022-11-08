package com.example.keyknowledge.control;

import com.example.keyknowledge.model.*;

public class UserControl {

    public UserControl(){

    }

    public void setMessage(String s,LoginControl control){
        control.setMessage(s);
    }

    public void setMessage(String s,MainControl control){
        control.setMessage(s);
    }

    public void setView(int view,User user,MainControl control){
        control.setView(view,user);
    }

    public void saveUser(User user, LoginControl control){
        control.saveUser(user);
    }


}
