package com.example.keyknowledge;

import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.airbnb.lottie.LottieAnimationView;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

import java.util.ArrayList;

import static com.example.keyknowledge.R.color.answer_check;

public class Match extends Activity {

    private LinearLayout grid;
    private TextView text,numero,categoria,livello, circleb1, circleb2, circleb3, circleb4, textb1, textb2, textb3, textb4, player1, player2;
    private RelativeLayout b1,b2,b3,b4,confirm;
    private int currentQuestion=0,risposta_corrente=0,player;
    private RelativeLayout[] risposte;
    private Quiz quiz;
    private Question question;
    private MatchControl control;
    private ArrayList<View> list;
    private RelativeLayout answer_prec;
    private LottieAnimationView lottieRight, lottieWrong;
    private ProgressBar progressBar1;
    private ImageView iconCat;
    private ArrayList<AdapterWrapper> wrapperArrayList;
    ViewDialog dialog;
    public static final String BroadcastStringForAction = "checkinternet";
    private IntentFilter mIntentFilter;
    private boolean flagGraphic = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        quiz=(Quiz)i.getSerializableExtra("quiz");
        player=i.getIntExtra("player",0);
        control=new MatchControl(quiz,this);
        control.setQuitListener(quiz,player);
        control.getQuestion(currentQuestion,false);
        //control.getQuestion();
        currentQuestion++;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match);
        wrapperArrayList = new ArrayList<AdapterWrapper>();
        iconCat = findViewById(R.id.iconCat);
        progressBar1 = findViewById(R.id.progressBar1);
        lottieRight = findViewById(R.id.lottieRight);
        lottieWrong = findViewById(R.id.lottieWrong);
        grid=findViewById(R.id.answers);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        if(player == 1){
            player1.setText(quiz.getUser1());
            player2.setText(quiz.getUser2());
        }else{
            player1.setText(quiz.getUser2());
            player2.setText(quiz.getUser1());
        }
        text=findViewById(R.id.domanda);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        textb1=findViewById(R.id.textb1);
        textb2=findViewById(R.id.textb2);
        textb3=findViewById(R.id.textb3);
        textb4=findViewById(R.id.textb4);
        circleb1=findViewById(R.id.circleb1);
        circleb2=findViewById(R.id.circleb2);
        circleb3=findViewById(R.id.circleb3);
        circleb4=findViewById(R.id.circleb4);
        numero=findViewById(R.id.numDomanda);
        categoria=findViewById(R.id.catDomanda);
        livello=findViewById(R.id.livDomanda);
        risposte = new RelativeLayout[]{b1, b2, b3, b4};
        answer_prec = null;
        dialog = new ViewDialog(this, R.layout.alert_connection_layout, null);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        if(!isOnline(getApplicationContext())){
            if(dialog != null && !dialog.isShowen()){
                dialog.showAlertDialog();
            }
        }else{
            if(dialog.isShowen()){
                dialog.dismiss();
            }
        }
        checkSmallPhone();
        /*list=grid.getTouchables();
        for(View v:list){
            Button b=(Button)v;
            int id=Integer.parseInt(b.getTag().toString());
            b.setOnClickListener(new View.OnClickListener(){

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    risposta_corrente=id;
                    for(View c:list){
                        Button b2=(Button)c;
                        if(risposta_corrente==Integer.parseInt(b2.getTag().toString())){
                            b2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                        }else{
                            b2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                        }
                    }
                }
            });
        }*/
    }

    public BroadcastReceiver MyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(BroadcastStringForAction)){
                if(!intent.getStringExtra("online_status").equals("true")){
                    if(dialog != null && !dialog.isShowen()){
                        dialog.showAlertDialog();
                    }
                }else{
                    if(dialog.isShowen()){
                        dialog.dismiss();
                    }
                }
            }
        }
    };

    public boolean isOnline(Context c){

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    private void checkSmallPhone() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if(height < 1000){
            b1.setY(30);
            b2.setY(30);
            b3.setY(30);
            b4.setY(30);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickAnswer(View v){
        if(answer_prec != null){
            answer_prec.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }
        RelativeLayout answer = (RelativeLayout)v;
        answer.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(answer_check)));
        answer_prec = answer;
        risposta_corrente = Integer.parseInt(answer.getTag().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setQuestionGraphic(){
        if(flagGraphic == true){
            if(answer_prec != null){
                answer_prec.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
            textb1.setText(question.getRisposta1());
            textb2.setText(question.getRisposta2());
            textb3.setText(question.getRisposta3());
            textb4.setText(question.getRisposta4());
            text.setText(question.getTesto());
            categoria.setText(question.getCategoria());
            setIcon(question.getCategoria());
            livello.setText("Livello "+question.getLivello());
            numero.setText("Domanda n."+currentQuestion);
        }else return;
    }

    public void setFlagGraphic(boolean flagGraphic) {
        this.flagGraphic = flagGraphic;
    }

    public Question getQuestion() {
        return question;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion(Question q){
        question=q;
        setQuestionGraphic();
        risposta_corrente = 0;
        /*for(View c:list){
            Button b2=(Button)c;
            b2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }*/
    }

    public void message(String x){
        Toast.makeText(this,x,Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void next(View view) {
        wrapperArrayList.add(new AdapterWrapper(currentQuestion, risposta_corrente, question));
        if (quiz.getPunteggioG1() < 0) {
                        quiz.setPunteggioG1(0);
                    }
        if (quiz.getPunteggioG2() < 0) {
                        quiz.setPunteggioG2(0);
                    }
        if (risposta_corrente == 0) {
            message("SELEZIONARE UNA RISPOSTA PRIMA DI ANDARE AVANTI");
        } else {

            if (risposta_corrente == question.getRisposta_esatta()) {
                if (player == 1) {
                    quiz.setPunteggioG1(quiz.getPunteggioG1() + 1);
                } else if (player == 2) {
                    quiz.setPunteggioG2(quiz.getPunteggioG2() + 1);
                }
                int color=getResources().getColor(R.color.green);
                switch (risposta_corrente) {
                    case 1:
                        b1.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 2:
                        b2.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 3:
                        b3.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 4:
                        b4.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                }
                control.getQuestion(currentQuestion,true);
                progressBar1.setProgress(progressBar1.getProgress() + 10);
                lottieRight.setVisibility(View.VISIBLE);
                lottieRight.playAnimation();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            lottieRight.setVisibility(View.INVISIBLE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            } else {
                int color = getResources().getColor(R.color.red);
                switch (risposta_corrente) {
                    case 1:
                        b1.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 2:
                        b2.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 3:
                        b3.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 4:
                        b4.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                }
                control.getQuestion(currentQuestion,false);
                lottieWrong.setVisibility(View.VISIBLE);
                lottieWrong.playAnimation();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            lottieWrong.setVisibility(View.INVISIBLE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
            if (currentQuestion == quiz.getNumQuesiti()) {
                System.out.println("1) Quiz prima di control.endMatch: " + quiz);
                if(quiz.getPunteggioG1() == 10){
                    progressBar1.setProgress(progressBar1.getWidth());
                }
                for(AdapterWrapper a: wrapperArrayList){
                    System.out.println(a);
                }
                control.setList(wrapperArrayList);
                control.endMatch(quiz, player);

            }else {
                currentQuestion++;
            }
        }

    }

    private void setIcon(String categoria){
        if(categoria.equals("storia")){
            iconCat.setImageDrawable(getResources().getDrawable(R.drawable.storia));
        }
        if(categoria.equals("scienze")){
            iconCat.setImageDrawable(getResources().getDrawable(R.drawable.scienze));
        }
        if(categoria.equals("arte")){
            iconCat.setImageDrawable(getResources().getDrawable(R.drawable.arte));
        }
        if(categoria.equals("geografia")){
            iconCat.setImageDrawable(getResources().getDrawable(R.drawable.geografia));
        }
        if(categoria.equals("cultura generale")){
            iconCat.setImageDrawable(getResources().getDrawable(R.drawable.cultura_generale));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(MyReceiver, mIntentFilter);
        Log.d("LIFECYCLE","onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(MyReceiver, mIntentFilter);
        Log.d("LIFECYCLE","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
        Log.d("LIFECYCLE","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LIFECYCLE","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE","onDestroy()");
        if (currentQuestion != quiz.getNumQuesiti()) {
            control.quit(quiz);
        }


    }

}
