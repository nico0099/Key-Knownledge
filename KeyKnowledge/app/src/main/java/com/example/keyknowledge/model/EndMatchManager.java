package com.example.keyknowledge.model;


import com.example.keyknowledge.control.EndMatchControl;
public class EndMatchManager {

    private EndMatchControl control;
    private QuizManager managerQuiz;
    public EndMatchManager(EndMatchControl endMatchControl) {
        managerQuiz=new QuizManager();
        this.control=endMatchControl;
    }

    public void updateMatch(Quiz quiz,int player) {
        managerQuiz.updateQuiz(quiz,player,control);
    }
}
