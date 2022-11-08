package com.google.firebase.database;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.keyknowledge.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.IndexedNode;


public class DataSnapshotMock extends DataSnapshot {

    private String nick;

    public DataSnapshotMock(){
        super(null, null);
    }

    @Override
    public DataSnapshot child(String nick){

        System.out.println("INFO: invocazione metodo child() in DataSnapshotMock");
        //System.out.println("Sono in child()");
        this.nick = nick;
        return this;
    }

    public String getNickOfUser(){
        return nick;
    }

    @Override
    public <T> T getValue(@NonNull Class<T> valueType) {
        System.out.println("INFO: invocazione metodo getValue() in DataSnapshotMock");
        //System.out.println("Sono in getValue()");
        return (T) new User(nick, "password", "email", "stato");
    }

}
