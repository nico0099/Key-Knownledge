package com.example.keyknowledge.control;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import com.example.keyknowledge.Knowledge;
import com.example.keyknowledge.Login;
import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.Pairing;
import com.example.keyknowledge.R;
import com.example.keyknowledge.model.MainManager;
import com.example.keyknowledge.model.User;
import static com.example.keyknowledge.model.Quiz.CLASSIC_MODE;
import static com.example.keyknowledge.model.Quiz.MISC_MODE;
import static com.example.keyknowledge.model.Quiz.RESTART_MODE;


public class MainControl {
    private Intent i;
    private MainActivity main;
    private MainManager manager;
    private boolean intentFlag = true;
    public MainControl(MainActivity a){
        main=a;
        manager=new MainManager(this);
    }

    public void setMessage(String x){
        main.message(x);
    }

    public void backHome(String nick){
        manager.accessUser(nick);
    }

    public void controlAccess(String nick) {
        if(nick!=null){
            backHome(nick);
        }else{
            main.setContent(R.layout.activity_main,null);
        }
    }

    public void setView(int x, User user){
        main.setContent(x,user);
    }

    public void goKnowledge(User user) {
        i=new Intent(main.getApplicationContext(), Knowledge.class);
        i.putExtra("user",user);
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(main.getApplicationContext(), R.anim.traslate_from_right, R.anim.translate_from_left);
        main.startActivity(i, activityOptions.toBundle());
    }

    public void goLogin(){
        i=new Intent(main.getApplicationContext(), Login.class);
        main.startActivity(i);
    }

    public void setIntentFlag(boolean intentFlag) {
        this.intentFlag = intentFlag;
    }

    public void logout(User user){
        manager.logout(user);
        if(intentFlag == true){
            i = new Intent(main.getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            main.startActivity(i);
        }
    }


    public void searchOpponent(String mode,User user) {
        switch(mode){
            case RESTART_MODE:
                i=new Intent(main.getApplicationContext(), Pairing.class);
                i.putExtra("user",user);
                i.putExtra("mode",mode);
                main.startActivity(i);
                break;
            case MISC_MODE:
            case CLASSIC_MODE:
                main.message("funzione in fase di sviluppo");
                break;

        }
    }
}
