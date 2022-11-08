package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String nickname;
    private String email;
    private String password;
    private String stato;
    private String ruolo;
    private int numPartiteVinte;
    private int numPartiteGiocate;

    public User(){}

    public User(User user, String password, String email, String offline){ }

    public User(String a,String b,String c,String d){
        nickname=a;
        email=c;
        password=b;
        stato=d;
        ruolo="giocatore";
        numPartiteVinte=0;
        numPartiteGiocate=0;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getStato(){
        return stato;
    }

    public String getRuolo(){
        return ruolo;
    }

    public int getNumPartiteGiocate(){
        return numPartiteGiocate;
    }

    public int getNumPartiteVinte(){
        return numPartiteVinte;
    }

    public void setNickname(String x){
        nickname=x;
    }

    public void setEmail(String x){
        email=x;
    }

    public void setPassword(String x){
        password=x;
    }

    public void setStato(String x){
        stato=x;
    }

    public void setRuolo(String x){
        ruolo=x;
    }

    public void setNumPartiteGiocate(int x){
        numPartiteGiocate=x;
    }

    public void setNumPartiteVinte(int x){
        numPartiteVinte=x;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", stato='" + stato + '\'' +
                ", ruolo='" + ruolo + '\'' +
                ", partite vinte='" + numPartiteVinte + '\'' +
                ", partite giocate='" + numPartiteGiocate + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return numPartiteVinte == user.numPartiteVinte &&
                numPartiteGiocate == user.numPartiteGiocate &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(stato, user.stato) &&
                Objects.equals(ruolo, user.ruolo);
    }

}