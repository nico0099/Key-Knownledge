package com.example.keyknowledge.model;

import androidx.annotation.NonNull;

import com.example.keyknowledge.R;
import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {

    public static String TABLE="users";
    public static String OFFLINE="offline",ONLINE="online";
    private DatabaseReference mDatabase;
    private UserControl controller;
    private User user = null;
    public UserManager(){
        controller=new UserControl();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void setState(String state,String nick){
        mDatabase.child(TABLE).child(nick).child("stato").setValue(state);
    }

    public void getUser(String nick,String pass,LoginManager l){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.child(nick).getValue(User.class);
                l.login(user,pass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public User getUserInEvent(){
        return user;
    }

    public void getUser(String nick,MainManager main){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user=snapshot.child(nick).getValue(User.class);
                main.accessUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
    public void login(String nick, String pass, LoginControl control){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    controller.setMessage("L'utente "+nick+" non esiste",control);
                }else{
                    if(user.getPassword().equals(pass)){
                        if(user.getStato().equals(OFFLINE)){
                            mDatabase.child(TABLE).child(nick).child("stato").setValue(ONLINE);
                            controller.saveUser(user,control);
                        }else{
                            controller.setMessage("utente connesso da un altro dispositivo",control);
                        }

                    }else{
                        controller.setMessage("password errata",control);
                    }
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    public void access(String nick,MainControl control){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    controller.setMessage("L'utente "+nick+" non esiste",control);
                }else{
                    mDatabase.child(TABLE).child(user.getNickname()).child("stato").setValue(ONLINE);
                    controller.setView(R.layout.home,user,control);

                }
            }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
 */
}
