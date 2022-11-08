package com.example.keyknowledge;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.keyknowledge.model.*;

public class Knowledge extends Activity {
    private User user;
    AlertDialog.Builder builder;
    Animation translate_from_right;
    ImageView einstain;
    LinearLayout linearStoria, linearGeo, linearScienze, linearArte, linearCG;
    LinearLayout[] linears;
    RelativeLayout relativeLayout;
    TextView lavagna;
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
         user=(User)i.getSerializableExtra("user");
        Log.d("INFO", "USER: " + user);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knowledge);
        relativeLayout = findViewById(R.id.parentRelative);
        einstain = findViewById(R.id.imageEinstain);
        linearStoria = findViewById(R.id.linearStoria);
        linearArte = findViewById(R.id.linearArte);
        linearGeo = findViewById(R.id.linearGeo);
        linearScienze = findViewById(R.id.linearScienze);
        linearCG = findViewById(R.id.linearCG);
        lavagna = findViewById(R.id.lavagna);
        setCustomFont("myFont.otf");
        linears = new LinearLayout[]{linearStoria, linearArte, linearGeo, linearScienze, linearCG};
        translate_from_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.traslate_from_right);
        translate_from_right.setDuration(1500);
        einstain.startAnimation(translate_from_right);
        ViewDialog viewDialog = new ViewDialog(this, R.layout.vignetta_layout, null);
        translate_from_right.setAnimationListener(new Animation.AnimationListener() {
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
        });
        einstain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog.showDialog();
            }
        });

        checkSmallPhone();
    }

    private void setCustomFont(String nameFont){
        Typeface tf = Typeface.createFromAsset(getAssets(), nameFont);
        lavagna.setTypeface(tf);
    }

    private void checkSmallPhone(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        System.out.println("misura altezza: " + height);
        if(height < 1000){
            for(LinearLayout layout: linears){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                params.height = 100;
                layout.setLayoutParams(params);
            }
        }
        if(height < 700){
            for(LinearLayout layout: linears){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                params.height = 90;
                layout.setLayoutParams(params);
            }
        }
    }

}
