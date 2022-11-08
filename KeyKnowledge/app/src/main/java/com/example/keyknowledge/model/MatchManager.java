package com.example.keyknowledge.model;


import com.example.keyknowledge.control.MatchControl;
import java.util.Random;
import static com.example.keyknowledge.model.Quiz.CLASSIC_MODE;
import static com.example.keyknowledge.model.Quiz.MISC_MODE;
import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class MatchManager {

    private static final int CATEGORIES=5, LEVELS=4, QUESTIONS=4;
    private String[] categories={"arte","cultura generale","geografia","scienze","storia"};
    private String[] questions={"arte","generale","geo","scienze","storia"};
    private String[] levels={"livello1","livello2","livello3","livello4"};
    private MatchControl control;
    private Quiz quiz;
    private IaModule module;
    private QuizManager managerQuiz;
    private QuestionManager managerQuestion;
    public MatchManager(Quiz q,MatchControl c){
        quiz=q;
        control=c;
        managerQuiz=new QuizManager(q);
        module=new IaModule(q,c);
        managerQuestion=new QuestionManager(c);
    }

    public QuestionManager getManagerQuestion() {
        return managerQuestion;
    }

    public void getMatchQuestion(){
        Random r=new Random();
        int categoria=r.nextInt(CATEGORIES-1);
        int level=r.nextInt(LEVELS-1);
        int max=(level+1)*4;
        int min=(level+1)*4-(QUESTIONS-1);
        int domanda=r.nextInt((max-min)+1)+min;
        String question=questions[categoria]+domanda;
        managerQuestion.getQuestion(categories[categoria],levels[level],question);
    }

    public void getMatchQuestion(int current,Boolean resp){
        switch(quiz.getMode()){
            case RESTART_MODE:
                module.nextQuestion(current,resp);
                break;
            case MISC_MODE:
            case CLASSIC_MODE:
                //do nothing
                break;
        }

    }

    public void setQuitListener(Quiz quiz,int player) {
        managerQuiz.setQuitListener(quiz,player,control);
        }


    public void quit(Quiz quiz) {
        managerQuiz.quit(quiz);
    }
}
