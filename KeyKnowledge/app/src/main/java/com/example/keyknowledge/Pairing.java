package com.example.keyknowledge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;
import com.airbnb.lottie.LottieAnimationView;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;
import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class Pairing extends Activity {
    Context context=this;
    PairingControl control=new PairingControl(this);
    User user;
    String mode;
     Quiz quiz;
    TextView startMatch, user1Nick, user2Nick, userNick;
    LinearLayout linearSearch;
    LottieAnimationView lottieConn;
    Animation fade_In, fade_Out;
    LottieAnimationView lottieCount;
    Timer timer;
    private boolean graphicFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        user=(User)i.getSerializableExtra("user");
        Log.d("INFO", "USER: " + user);
        mode=i.getStringExtra("mode");
         timer = new Timer();
        TimerTask task = new MyTask();

// aspetta 20 secondi prima dell'esecuzione
        timer.schedule(task,20000);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_player);
        control.createMatch(user,mode);
        linearSearch = findViewById(R.id.linearForSearch);
        lottieConn = findViewById(R.id.lottieConnection);
        userNick = findViewById(R.id.userNick);
        userNick.setText(user.getNickname());
        user1Nick = findViewById(R.id.user1Nick);
        user2Nick = findViewById(R.id.user2Nick);
        startMatch = findViewById(R.id.startMatch);
        lottieCount = findViewById(R.id.lottieCount);
        fade_Out = new AlphaAnimation(0, 1);
        fade_Out.setStartOffset(500);
        fade_Out.setDuration(5000);
        fade_In = new AlphaAnimation(1, 0);
        fade_In.setInterpolator(new DecelerateInterpolator());
        fade_In.setStartOffset(1000);
        fade_In.setDuration(1000);
        checkSmallPhone();
    }

    private void checkSmallPhone(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        System.out.println("misura altezza: " + height);
        if(height < 1000){
            userNick.setY(userNick.getY() - 50);
            user1Nick.setY(user1Nick.getY() + 80);
            user2Nick.setY(user2Nick.getY() + 80);
        }
        if(height < 700){
            userNick.setY(userNick.getY() - 90);
            user1Nick.setY(user1Nick.getY() + 100);
            user2Nick.setY(user2Nick.getY() + 100);
        }
    }


    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void setGraphicFlag(boolean graphicFlag) {
        this.graphicFlag = graphicFlag;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz x){
        quiz=x;
        if(graphicFlag == true){
            if(!quiz.getUser2().equals("void")){
                if(quiz.getUser2().equals(user.getNickname())){
                    user1Nick.setText(quiz.getUser2());
                    user2Nick.setText(quiz.getUser1());
                }else{
                    user1Nick.setText(quiz.getUser1());
                    user2Nick.setText(quiz.getUser2());
                }
                startMatch.setVisibility(View.VISIBLE);
                linearSearch.setVisibility(View.INVISIBLE);
                lottieConn.setVisibility(View.VISIBLE);
                lottieConn.playAnimation();
                user1Nick.startAnimation(fade_Out);
                user2Nick.startAnimation(fade_Out);
                user1Nick.setVisibility(View.VISIBLE);
                user2Nick.setVisibility(View.VISIBLE);
                lottieCount.setVisibility(View.VISIBLE);
                lottieCount.playAnimation();
            }
        }
    }

    @Override
    public void onBackPressed() {
        control.resetMatch(quiz);
        super.onBackPressed();
    }
    //class timer
    class MyTask extends TimerTask {
        public void run() {
            control.resetMatch(quiz);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("ATTENZIONE");
                    builder.setMessage("Nessun avversario trovato,riprova più tardi");

                    builder.setPositiveButton("TORNA ALLA HOME", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            onBackPressed();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            System.out.println( "Running the task" );
        }
    }
    protected void onDestroy() {
        timer.cancel();
        timer.purge();
        super.onDestroy();
    }
}


