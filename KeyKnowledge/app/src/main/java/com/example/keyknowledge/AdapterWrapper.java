package com.example.keyknowledge;

import com.example.keyknowledge.model.Question;
import com.example.keyknowledge.model.Quiz;

import java.io.Serializable;

public class AdapterWrapper implements Serializable {
//ciao
    private int numDomanda;
    private int risposta_utente;
    private Question question;

    public AdapterWrapper(int numDomanda, int risposta_utente, Question question){
        this.numDomanda = numDomanda;
        this.risposta_utente = risposta_utente;
        this.question = question;
    }

    public int getNumDomanda() {
        return numDomanda;
    }

    public void setNumDomanda(int numDomanda) {
        this.numDomanda = numDomanda;
    }

    public int getRisposta_utente() {
        return risposta_utente;
    }

    public void setRisposta_utente(int risposta_utente) {
        this.risposta_utente = risposta_utente;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "AdapterWrapper{" +
                "numDomanda=" + numDomanda +
                ", risposta_utente=" + risposta_utente +
                ", question=" + question +
                '}';
    }
}
