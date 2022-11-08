package com.example.keyknowledge.control;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.keyknowledge.AdapterWrapper;
import com.example.keyknowledge.EndMatch;
import com.example.keyknowledge.Match;
import com.example.keyknowledge.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchControl {

    private Quiz quiz;
    private MatchManager manager;
    private Match match;
    private ArrayList<AdapterWrapper> wrapperArrayList;

    public MatchControl(Quiz q, Match m){
        match=m;
        quiz=q;
        manager=new MatchManager(quiz,this);
        wrapperArrayList = new ArrayList<AdapterWrapper>();
    }

    public MatchManager getManager() {
        return manager;
    }

    public void getQuestion(){
        manager.getMatchQuestion();
    }

    public void getQuestion(int current,Boolean resp){
        manager.getMatchQuestion(current,resp);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion(Question question){
        match.setQuestion(question);
    }

    public void setList(ArrayList<AdapterWrapper> wrapperArrayList){
        this.wrapperArrayList = wrapperArrayList;
    }
    public void endMatch(Quiz quiz, int player) {
        Intent i=new Intent(match.getApplicationContext(), EndMatch.class);
        i.putExtra("quiz",quiz);
        i.putExtra("player",player);
        if(wrapperArrayList != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", (Serializable) wrapperArrayList);
            i.putExtras(bundle);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1800);
                    match.startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void setQuitListener(Quiz quiz,int player) {
        manager.setQuitListener(quiz,player);
    }

    public void quit(Quiz quiz) {
        manager.quit(quiz);
    }
}
