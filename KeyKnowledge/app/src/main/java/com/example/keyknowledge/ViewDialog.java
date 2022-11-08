package com.example.keyknowledge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

public class ViewDialog {
    private Activity activity;
    private int layout;
    private ArrayList<AdapterWrapper> wrapperArrayList;
    private Dialog dialog;
    public ViewDialog(Activity activity, int layout, ArrayList<AdapterWrapper> wrapperArrayList){
        this.activity = activity;
        this.layout = layout;
        if(wrapperArrayList != null){
            this.wrapperArrayList = wrapperArrayList;
        }else{
            this.wrapperArrayList = null;
        }

        dialog = new Dialog(activity);
    }

    public void showAlertDialog(){
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        Button dialogButton = (Button) dialog.findViewById(R.id.exitButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowen(){
        return dialog.isShowing();
    }



    public void showDialog(){
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        if(layout == R.layout.vignetta_layout){
            Button dialogButton = (Button) dialog.findViewById(R.id.buttonHoCapito);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        if(layout == R.layout.result_layout && wrapperArrayList != null){
            System.out.println("Ciao, ci sono arrivato");
            int i=0;
            for(AdapterWrapper adapterWrapper: wrapperArrayList){
                System.out.println(i + ") Risposta utente: " + adapterWrapper.getRisposta_utente() +
                        " numDomanda: " + adapterWrapper.getNumDomanda());
                i++;
            }
            ListView listView = dialog.findViewById(R.id.listViewAnswers);
            ListAdapter listAdapter = new ListAdapter(dialog.getContext(), R.layout.item_answer, wrapperArrayList);
            listView.setAdapter(listAdapter);
            Button dialogButton = (Button) dialog.findViewById(R.id.closeButtonDialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }


    }
}
