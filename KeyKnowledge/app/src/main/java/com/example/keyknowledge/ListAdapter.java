package com.example.keyknowledge;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.example.keyknowledge.model.Question;

import java.util.List;

public class ListAdapter extends ArrayAdapter<AdapterWrapper> {

    LayoutInflater inflater;
    Question question ;

    public ListAdapter(Context context, int resource, List<AdapterWrapper> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        question = new Question();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_answer, null);
        }

        AdapterWrapper adapterWrapper = getItem(position);
        question = adapterWrapper.getQuestion();
        TextView domandaNum, domandaText, letterRispData, textRispData, letterRispEsatta, textRispEsatta;
        ImageView smileFace, sadFace;
        domandaNum = convertView.findViewById(R.id.itemDomandaNum);
        domandaText = convertView.findViewById(R.id.itemDomandaText);
        letterRispData = convertView.findViewById(R.id.circleRispData);
        textRispData = convertView.findViewById(R.id.textRispData);
        letterRispEsatta = convertView.findViewById(R.id.circleRispEsatta);
        textRispEsatta = convertView.findViewById(R.id.textRispEsatta);
        smileFace = convertView.findViewById(R.id.smileFace);
        sadFace = convertView.findViewById(R.id.sadFace);
        if(adapterWrapper.getRisposta_utente() == adapterWrapper.getQuestion().getRisposta_esatta()){
            sadFace.setVisibility(View.INVISIBLE);
            smileFace.setVisibility(View.VISIBLE);
        }else{
            smileFace.setVisibility(View.INVISIBLE);
            sadFace.setVisibility(View.VISIBLE);
        }
        domandaNum.setText("Domanda " + adapterWrapper.getNumDomanda());
        domandaText.setText(adapterWrapper.getQuestion().getTesto());
        letterRispData.setText(getLetterRisp(adapterWrapper.getRisposta_utente()));
        textRispData.setText(getStringRisp(adapterWrapper.getRisposta_utente()));
        letterRispEsatta.setText(getLetterRisp(adapterWrapper.getQuestion().getRisposta_esatta()));
        textRispEsatta.setText(getStringRisp(adapterWrapper.getQuestion().getRisposta_esatta()));
        return convertView;
    }

    private String getStringRisp(int numRisp){
        String result = "";
        switch (numRisp){
            case 1:
                result = question.getRisposta1();
                break;
            case 2:
                result = question.getRisposta2();
                break;
            case 3:
                result = question.getRisposta3();
                break;
            case 4:
                result = question.getRisposta4();
                break;
            default:
                break;
        }

        return result;
    }

    private String getLetterRisp(int numRisp){
        String result = "";
        switch (numRisp){
            case 1:
                result = "A";
                break;
            case 2:
                result = "B";
                break;
            case 3:
                result = "C";
                break;
            case 4:
                result = "D";
                break;
            default:
                break;
        }

        return result;
    }


}
