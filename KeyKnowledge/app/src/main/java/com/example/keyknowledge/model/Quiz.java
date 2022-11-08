package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Quiz implements Serializable {

    public static final String RESTART_MODE="RESTART_MODE",CLASSIC_MODE="CLASSIC_MODE",MISC_MODE="MISC_MODE";

    private int id;
    private String mode;
    private int numQuesiti;
    private String user1;
    private String user2;
    private int punteggioG1;
    private int punteggioG2;
    private String status;

    public Quiz(){
        punteggioG1=-1;
        punteggioG2=-1;
    }

    public Quiz(int a,String b,int c,String d,String e){
        id=a;
        mode=b;
        numQuesiti=c;
        user1=d;
        user2=e;
        punteggioG1=-1;
        punteggioG2=-1;
        status = null;
    }

    public int getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public int getNumQuesiti() {
        return numQuesiti;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public int getPunteggioG1() {
        return punteggioG1;
    }

    public int getPunteggioG2() {
        return punteggioG2;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setNumQuesiti(int numQuesiti) {
        this.numQuesiti = numQuesiti;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public void setPunteggioG1(int punteggioG1) {
        this.punteggioG1 = punteggioG1;
    }

    public void setPunteggioG2(int punteggioG2) {
        this.punteggioG2 = punteggioG2;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", mode='" + mode + '\'' +
                ", numQuesiti=" + numQuesiti +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", punteggioG1=" + punteggioG1 +
                ", punteggioG2=" + punteggioG2 +
                ", status='" + status + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id == quiz.id &&
                numQuesiti == quiz.numQuesiti &&
                punteggioG1 == quiz.punteggioG1 &&
                punteggioG2 == quiz.punteggioG2 &&
                Objects.equals(mode, quiz.mode) &&
                Objects.equals(user1, quiz.user1) &&
                Objects.equals(user2, quiz.user2) &&
                Objects.equals(status, quiz.status);
    }

}
