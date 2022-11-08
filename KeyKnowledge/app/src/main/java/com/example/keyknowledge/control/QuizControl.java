package com.example.keyknowledge.control;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.keyknowledge.model.*;

public class QuizControl {

    public QuizControl(){

    }

    public void setQuitListener(Quiz quiz, int player, MatchControl control){
        control.endMatch(quiz,player);
    }

    public void startMatch(Quiz quiz,int player,PairingControl control){
        control.startMatch(quiz,player);
    }

    public void setQuiz(Quiz quiz,PairingControl control){
        control.setQuiz(quiz);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void wait(EndMatchControl control){
        control.waitOpponent();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void finish(Quiz quiz, EndMatchControl control){
        control.finish(quiz);
    }
}
