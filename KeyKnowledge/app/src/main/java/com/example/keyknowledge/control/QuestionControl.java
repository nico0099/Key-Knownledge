package com.example.keyknowledge.control;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.keyknowledge.model.Question;

public class QuestionControl {

    public QuestionControl(MatchControl c){
        control=c;
    }

    public QuestionControl(){

    }

    private MatchControl control;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion(Question question) {
        control.setQuestion(question);
    }

}
