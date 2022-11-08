package com.example.keyknowledge;


import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.airbnb.lottie.LottieAnimationView;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.databinding.ActivityMainBinding;
import com.example.keyknowledge.model.*;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseOptions;

import java.util.EventListener;

import static com.example.keyknowledge.model.Quiz.CLASSIC_MODE;
import static com.example.keyknowledge.model.Quiz.MISC_MODE;
import static com.example.keyknowledge.model.Quiz.RESTART_MODE;



public class MainActivity extends Activity implements DrawerTopFragment.UserListener {

    SharedPreferences pref;
    User user;
    MainControl control=new MainControl(this);
    ImageView logo;
    private GestureDetector detector;
    int layout=0;
    Animation translate_from_right, translate_from_left, zoom_in, zoom_out;
    String nickname;
    ImageView profile;
    NavigationView navigationView;
    TextView nick, classicTv, restartTv, miscTv;
    LinearLayout layoutModes, readyForQuiz;
    LottieAnimationView lottieClassic, lottieRestart, lottieMisc, lottieStart;
    ActivityMainBinding binding;
    ViewDialog dialog;
    public static final String BroadcastStringForAction = "checkinternet";
    private IntentFilter mIntentFilter;
    private boolean graphicFlag = true;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        pref=getSharedPreferences("profile",MODE_PRIVATE);
        control.controlAccess(pref.getString("id",null));
        translate_from_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.traslate_from_right);
        translate_from_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_from_left);
        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        detector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velx, float vely) {
                        if (e1.getX()>e2.getX()) {
                            //swipe verso sinistra
                            if(navigationView != null && !navigationView.isShown()){
                                showNavigation(navigationView);
                            }
                        }else {
                            //swipe verso destra
                            if(layout==R.layout.home) {
                                if(!navigationView.isShown()){
                                    control.goKnowledge(user);
                                }

                            }
                        }
                        return true;
                    }
                });
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
    }



    @Override
    protected void onStart(){
        super.onStart();
        /*if(!InternetConnection.haveInternetConnection(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("ATTENZIONE!");
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_24);
            builder.setMessage("Nessuna connessione rilevata.\nRiconnettere il dispositivo");
            builder.setPositiveButton("RIPROVA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!InternetConnection.haveInternetConnection(getApplicationContext())){
                        builder.show();
                    }
                }
            });
            builder.setNegativeButton("CHIUDI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.show();
        }*/
        //InternetConnection.checkConnection(this);
    }


    public int getLayout() {
        return layout;
    }

    private void showDialog(){
        new AlertDialog.Builder(this)
                .setMessage("Sei sicuro di voler uscire dall'app?")
                .setNegativeButton("ANNULLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nada de nada
                    }
                })
                .setPositiveButton("ESCI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .show();
    }

    public void onClickMode(View v){
        TextView mode = (TextView)v;
        if(mode.isSelected()){
            return;
        }


        if(textSelected() != null){
            textSelected().setTextSize(15);
            if(textSelected().getId() == R.id.classicMode){
                if(lottieClassic.getVisibility() == View.VISIBLE){
                    lottieClassic.setVisibility(View.INVISIBLE);
                }
            }
            if(textSelected().getId() == R.id.restartMode){
                if(lottieRestart.getVisibility() == View.VISIBLE){
                    lottieRestart.setVisibility(View.INVISIBLE);
                }
            }
            if(textSelected().getId() == R.id.miscMode){
                if(lottieMisc.getVisibility() == View.VISIBLE){
                    lottieMisc.setVisibility(View.INVISIBLE);
                }
            }
            textSelected().setSelected(false);
            mode.setSelected(true);
            mode.setTextSize(40);
            Log.d("INFO", "restart: " + restartTv.isSelected() + ", classic: " + classicTv.isSelected() + ", misc: " + miscTv.isSelected());
            if(mode.getId() == R.id.classicMode){
                if(lottieClassic.getVisibility() == View.INVISIBLE){
                    lottieClassic.setVisibility(View.VISIBLE);
                    lottieClassic.playAnimation();
                }
            }
            if(mode.getId() == R.id.restartMode){
                if(lottieRestart.getVisibility() == View.INVISIBLE){
                    lottieRestart.setVisibility(View.VISIBLE);
                    lottieRestart.playAnimation();
                }
            }
            if(mode.getId() == R.id.miscMode){
                if(lottieMisc.getVisibility() == View.INVISIBLE){
                    lottieMisc.setVisibility(View.VISIBLE);
                    lottieMisc.playAnimation();
                }
            }

        }


    }

    private TextView textSelected(){
        if(restartTv.isSelected()){
            return restartTv;
        }else if(miscTv.isSelected()){
            return miscTv;
        }else if(classicTv.isSelected()){
            return classicTv;
        }else{
            return null;
        }
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

    private void setNavigationActions(){
        if(navigationView != null){
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {

                    int id = item.getItemId();
                    System.out.println("id item menu = " + id);
                    switch (id){
                        case R.id.profiloHome:
                        case R.id.statisticheHome:
                            Toast toast = Toast.makeText(getApplicationContext(), "Sezione non ancora disponibile", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 200);
                            toast.show();
                            break;
                        case R.id.logoutHome:
                            showDialog();
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }

    }

    public void showSettings(View view){
        Toast toast = Toast.makeText(getApplicationContext(),
                "sezione impostazioni non ancora disponibile", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();
    }


    public void showNavigation(View v){
        navigationView.setVisibility(View.VISIBLE);
        navigationView.startAnimation(translate_from_right);
    }

    public void closeNavigation(View v){
        navigationView.setVisibility(View.INVISIBLE);
        navigationView.startAnimation(translate_from_left);
    }

    public void setGraphicFlag(boolean graphicFlag) {
        this.graphicFlag = graphicFlag;
    }

    public void setContent(int x, User y) {
        if(graphicFlag == true) {
            setContentView(x);
        }
        layout=x;
        if(layout == R.layout.activity_main){
            readyForQuiz = findViewById(R.id.readyForQuiz);
        }
        if(layout == R.layout.home){
            if(graphicFlag == true){
                navigationView = findViewById(R.id.menulaterale);
                miscTv = findViewById(R.id.miscMode);
                classicTv = findViewById(R.id.classicMode);
                restartTv = findViewById(R.id.restartMode);
                lottieClassic = findViewById(R.id.lottieClassic);
                lottieMisc = findViewById(R.id.lottieMisc);
                lottieRestart = findViewById(R.id.lottieRestart);
                lottieStart = findViewById(R.id.lottieStart);
                layoutModes = findViewById(R.id.layoutModes);
            }
            user=y;
            if(user!=null) {
                nickname = user.getNickname();
                if(graphicFlag == true){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DrawerTopFragment fragment = new DrawerTopFragment();
                    ft.add(R.id.drawerTop, fragment);
                    ft.commit();
                    setNavigationActions();
                    restartTv.setSelected(true);
                    restartTv.setTextSize(40);
                    lottieRestart.setVisibility(View.VISIBLE);
                }
            }
        }
        if(graphicFlag == true){
            checkSmallPhone();
        }
    }

    private void checkSmallPhone(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if(height < 1000){
            if(layout == R.layout.home){
                layoutModes.setY(layoutModes.getY() + 70);
                lottieStart.setY(lottieStart.getY() + 70);
            }
            if(layout == R.layout.activity_main){
                readyForQuiz.setY(readyForQuiz.getY() + 70);
            }
        }
        if(height < 700){
            if(layout == R.layout.home){
                layoutModes.setY(layoutModes.getY() + 90);
                lottieStart.setY(lottieStart.getY() + 90);
            }
            if(layout == R.layout.activity_main){
                readyForQuiz.setY(readyForQuiz.getY() + 90);
            }
        }
    }

    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        control.goLogin();
    }

    public void startMatch(View v) {
        if(restartTv.isSelected()){
            lottieStart.playAnimation();
            startMatch2();
        }else if(classicTv.isSelected() || miscTv.isSelected()){
            Toast toast = Toast.makeText(getApplicationContext(), "Modalità di gioco non ancora disponibile", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 200);
            toast.show();
        }
    }

    private void logout(){
        Editor editor=pref.edit();
        editor.remove("id");
        editor.commit();
        control.logout(user);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        detector.onTouchEvent(event);
        return true;
    }
    private void startMatch1(){
        //CLASSIC MODE
        control.searchOpponent(CLASSIC_MODE,user);

    }
    private void startMatch2(){

        //RESTART MODE
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    control.searchOpponent(RESTART_MODE,user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    private void startMatch3(){
        //MISC MODE
        control.searchOpponent(MISC_MODE,user);

    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(MyReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(MyReceiver, mIntentFilter);
    }

    /*@Override
    public void onNetworkInfoAvailable(NetworkInfo ni) {
        System.out.println("Status conn: " + ni.isConnected());
        if (ni.getTypeName().equalsIgnoreCase("WIFI") || ni.getTypeName().equalsIgnoreCase("MOBILE")) {
            if (!ni.isConnected()) {
                Toast.makeText(getApplicationContext(),
                        "connessione assente", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }*/
}