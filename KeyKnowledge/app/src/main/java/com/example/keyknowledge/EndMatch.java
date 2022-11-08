package com.example.keyknowledge;

import android.app.Activity;
import android.app.admin.ConnectEvent;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.airbnb.lottie.LottieAnimationView;
import com.example.keyknowledge.control.EndMatchControl;
import com.example.keyknowledge.model.Quiz;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EndMatch extends Activity implements Animation.AnimationListener{

    private Quiz quiz;
    private int player;
    private EndMatchControl control;
    private TextView text;
    private boolean graphicFlag = true;
    LottieAnimationView lottieWait, lottieConfetti1, lottieConfetti2, lottieWin, lottieLose, lottiePareggio;
    LinearLayout returnHome;
    ImageView hai_vinto, pareggio, hai_perso;
    Animation fade_Out;
    ArrayList<AdapterWrapper> wrapperArrayList;
    ViewDialog viewDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        quiz=(Quiz)i.getSerializableExtra("quiz");
        System.out.println("1) Quiz in endMatch: " + quiz);
        player=i.getIntExtra("player",-1);
        wrapperArrayList = (ArrayList<AdapterWrapper>) i.getExtras().getSerializable("list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_match);
        fade_Out = new AlphaAnimation(0, 1);
        fade_Out.setStartOffset(500);
        fade_Out.setDuration(3000);
        text=findViewById(R.id.text);
        lottieWait = findViewById(R.id.lottieWait);
        lottieConfetti1 = findViewById(R.id.lottieConfetti1);
        lottieConfetti2 = findViewById(R.id.lottieConfetti2);
        lottieWin = findViewById(R.id.lottieWin);
        lottieLose = findViewById(R.id.lottieLose);
        returnHome = findViewById(R.id.returnHome);
        lottiePareggio = findViewById(R.id.lottiePareggio);
        hai_vinto = findViewById(R.id.hai_vinto);
        hai_perso = findViewById(R.id.hai_perso);
        pareggio = findViewById(R.id.pareggio);
        control=new EndMatchControl(this);
        control.updateMatch(quiz,player);
        fade_Out.setAnimationListener(this);
    }

    public void setGraphicFlag(boolean graphicFlag) {
        this.graphicFlag = graphicFlag;
    }

    public void waitOpponent() {
        text.setVisibility(View.VISIBLE);
        lottieWait.setVisibility(View.VISIBLE);
        if(player==1){
            setText("Aspettando il giocatore\n"+quiz.getUser2() + "...");
            System.out.println(quiz.getUser2());
        }else{
            setText("Aspettando il giocatore\n"+quiz.getUser1() + "...");
            System.out.println(quiz.getUser1());
        }
    }

    public void end(Quiz q) {
        if(wrapperArrayList != null){
            viewDialog = new ViewDialog(this, R.layout.result_layout, wrapperArrayList);
        }
        text.setVisibility(View.INVISIBLE);
        if(lottieWait.getVisibility() == View.VISIBLE){
            lottieWait.setVisibility(View.INVISIBLE);
        }
        returnHome.setVisibility(View.VISIBLE);
        quiz=q;
        System.out.println(quiz.getPunteggioG1());
        System.out.println(quiz.getPunteggioG2());
        if(player==1){
            if(quiz.getPunteggioG1()>quiz.getPunteggioG2()){
                //setText("HAI VINTO!!!");
                hai_vinto.startAnimation(fade_Out);
                hai_vinto.setVisibility(View.VISIBLE);
                lottieWin.setVisibility(View.VISIBLE);
                lottieConfetti1.setVisibility(View.VISIBLE);
                lottieConfetti2.setVisibility(View.VISIBLE);
            }else if(quiz.getPunteggioG1()<quiz.getPunteggioG2()){
                //setText("HAI PERSO!!!");
                hai_perso.startAnimation(fade_Out);
                hai_perso.setVisibility(View.VISIBLE);
                lottieLose.setVisibility(View.VISIBLE);
            }else{
                //setText("PAREGGIO!!!");
                pareggio.startAnimation(fade_Out);
                pareggio.setVisibility(View.VISIBLE);
                lottiePareggio.setVisibility(View.VISIBLE);
            }
        }else if(player==2){
            if(quiz.getPunteggioG2()>quiz.getPunteggioG1()){
                //setText("VITTORIA!!!");
                hai_vinto.startAnimation(fade_Out);
                hai_vinto.setVisibility(View.VISIBLE);
                lottieWin.setVisibility(View.VISIBLE);
                lottieConfetti1.setVisibility(View.VISIBLE);
                lottieConfetti2.setVisibility(View.VISIBLE);
            }else if(quiz.getPunteggioG2()<quiz.getPunteggioG1()){
                //setText("HAI PERSO!!!");
                hai_perso.startAnimation(fade_Out);
                hai_perso.setVisibility(View.VISIBLE);
                lottieLose.setVisibility(View.VISIBLE);
            }else{
                //setText("PAREGGIO!!!");
                pareggio.startAnimation(fade_Out);
                pareggio.setVisibility(View.VISIBLE);
                lottiePareggio.setVisibility(View.VISIBLE);
            }
        }else if(player==-2){
            text.setVisibility(View.VISIBLE);
            hai_vinto.startAnimation(fade_Out);
            hai_vinto.setVisibility(View.VISIBLE);
            setText("VITTORIA PER ABBANDONO!!!\nIl giocatore "+quiz.getUser1()+" ha abbandonato");
            lottieWin.setVisibility(View.VISIBLE);
            lottieConfetti1.setVisibility(View.VISIBLE);
            lottieConfetti2.setVisibility(View.VISIBLE);
        }else if(player==-1){
            text.setVisibility(View.VISIBLE);
            hai_vinto.startAnimation(fade_Out);
            hai_vinto.setVisibility(View.VISIBLE);
            setText("VITTORIA PER ABBANDONO!!!\nIl giocatore "+quiz.getUser2()+" ha abbandonato");
            lottieWin.setVisibility(View.VISIBLE);
            lottieConfetti1.setVisibility(View.VISIBLE);
            lottieConfetti2.setVisibility(View.VISIBLE);
        }
    }

    public void setText(String x){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(x);
            }
        });
    }

    public void returnHome(View view){
        control.returnHome();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        viewDialog.showDialog();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
