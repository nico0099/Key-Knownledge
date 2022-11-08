package com.example.keyknowledge.control;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.keyknowledge.*;
import com.example.keyknowledge.model.*;

public class EndMatchControl {

    private EndMatch endMatch;
    private EndMatchManager manager;
    public EndMatchControl(){}
    public EndMatchControl(EndMatch endMatch) {
        this.endMatch=endMatch;
        manager=new EndMatchManager(this);
    }

    public void updateMatch(Quiz q, int p) {
        manager.updateMatch(q,p);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void waitOpponent() {
        endMatch.waitOpponent();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void finish(Quiz q) {
        endMatch.end(q);
    }

    public void returnHome() {
        Intent i=new Intent(endMatch.getApplicationContext(),MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        endMatch.startActivity(i);
    }
}
