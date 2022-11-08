package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Question implements Serializable {

    private String id;
    private String testo;
    private String risposta1;
    private String risposta2;
    private String risposta3;
    private String risposta4;
    private int risposta_esatta;
    private String categoria;
    private int livello;

    public Question() {
    }

    public Question(String id,String testo,String risposta1,String risposta2,String risposta3,String risposta4,int risposta_corretta,String categoria,int livello) {
        this.testo = testo;
        this.id=id;
        this.risposta1=risposta1;
        this.risposta2=risposta2;
        this.risposta3=risposta3;
        this.risposta4=risposta4;
        this.risposta_esatta=risposta_corretta;
        this.categoria=categoria;
        this.livello=livello;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getId() {
        return id;
    }

    public String getRisposta1() {
        return risposta1;
    }

    public String getRisposta2() {
        return risposta2;
    }

    public String getRisposta3() {
        return risposta3;
    }

    public String getRisposta4() {
        return risposta4;
    }

    public int getRisposta_esatta() {
        return risposta_esatta;
    }

    public int getLivello(){
        return livello;
    }

    public String getTesto() {
        return testo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRisposta1(String risposta1) {
        this.risposta1 = risposta1;
    }

    public void setRisposta2(String risposta2) {
        this.risposta2 = risposta2;
    }

    public void setRisposta3(String risposta3) {
        this.risposta3 = risposta3;
    }

    public void setRisposta4(String risposta4) {
        this.risposta4 = risposta4;
    }

    public void setRisposta_esatta(int risposta_corretta) {
        this.risposta_esatta = risposta_corretta;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setLivello(int livello){
        this.livello=livello;
    }

    @Override
    public String toString() {
        return "Question{\n" +
                "id='" + id + '\n' +
                "testo='" + testo + '\n' +
                "risposta1='" + risposta1 + '\n' +
                "risposta2='" + risposta2 + '\n' +
                "risposta3='" + risposta3 + '\n' +
                "risposta4='" + risposta4 + '\n' +
                "risposta_esatta=" + risposta_esatta + '\n' +
                "categoria='" + categoria + '\n' +
                "livello='" + livello + '\n' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return risposta_esatta == question.risposta_esatta &&
                livello == question.livello &&
                Objects.equals(id, question.id) &&
                Objects.equals(testo, question.testo) &&
                Objects.equals(risposta1, question.risposta1) &&
                Objects.equals(risposta2, question.risposta2) &&
                Objects.equals(risposta3, question.risposta3) &&
                Objects.equals(risposta4, question.risposta4) &&
                Objects.equals(categoria, question.categoria);
    }

}
