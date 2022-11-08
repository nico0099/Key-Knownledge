package com.example.keyknowledge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.ConnectEvent;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.net.ConnectException;
import java.net.Socket;

import javax.sql.ConnectionEvent;

public class InternetConnection {

    public static void checkConnection(Activity activity){
        if(haveInternetConnection(activity)){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
            builder.setTitle("ATTENZIONE!");
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_24);
            builder.setMessage("Nessuna connessione rilevata.\nRiconnettere il dispositivo");
            builder.setPositiveButton("RIPROVA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!InternetConnection.haveInternetConnection(activity.getApplicationContext())){
                        builder.show();
                    }
                }
            });
            builder.setNegativeButton("CHIUDI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });

            builder.show();
        }
    }

    private static boolean haveInternetConnection(Context c) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
