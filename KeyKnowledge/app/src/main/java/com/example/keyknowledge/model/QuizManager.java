package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class QuizManager {

    private static final int MAX_ROOMS=100;
    public static String TABLE="matches";
    private DatabaseReference mDatabase;
    private ValueEventListener listener;
    private Quiz quiz, quizInEvent;
    private QuizControl controller;

    public QuizManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        controller=new QuizControl();
    }

    public QuizManager(Quiz q){
        controller=new QuizControl();
        quiz=q;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void setQuitListener(Quiz quiz, int player, MatchControl control){
        listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Quiz q=snapshot.child(""+quiz.getId()+"").getValue(Quiz.class);
                if(q!=null&&!q.getStatus().equals("void")) {
                    String status = snapshot.child("" + quiz.getId() + "").child("status").getValue(String.class);
                    if (status.equals("quit")) {
                        controller.setQuitListener(quiz, player * -1,control);
                    }
                }else{
                    mDatabase.child(TABLE).child(quiz.getMode()).child("" + quiz.getId() + "").removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child(TABLE).child(quiz.getMode()).addValueEventListener(listener);
    }

    public void quit(Quiz quiz){
        mDatabase.child(TABLE).child(quiz.getMode()).removeEventListener(listener);
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(Quiz.class)!=null){
                    String control=snapshot.child("status").getValue(String.class);
                    if(!control.equals("void")){
                        mDatabase.child(TABLE).child(quiz.getMode()).child("" + quiz.getId() + "").child("status").setValue("quit");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Quiz getQuizInEvent(){
        return quizInEvent;
    }

    public void setController(QuizControl controller){
        this.controller = controller;
    }

    public void createQuiz(User user,String mode,PairingControl control){
        mDatabase.child(TABLE).child(mode).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue()!=null) {
                    for (int i = 0; i < MAX_ROOMS; i++) {
                        String id = "" + i + "";
                        if (currentData.hasChild(id)) {
                            String status = currentData.child(id).child("status").getValue(String.class);
                            if (status.equals("void")) {
                                mDatabase.child(TABLE).child(mode).child(id).child("status").setValue("wait");
                                mDatabase.child(TABLE).child(mode).child(id).child("user1").setValue(user.getNickname());
                                mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        controller.setQuiz(snapshot.child(id).getValue(Quiz.class),control);
                                        String status = snapshot.child(id).child("status").getValue(String.class);
                                        String opponent=snapshot.child(id).child("user2").getValue(String.class);
                                        if(status.equals("full")&&(!(opponent).equals("void"))) {
                                            quizInEvent =snapshot.child(id).getValue(Quiz.class);
                                            controller.startMatch(quiz,1,control);
                                            mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                                break;
                            } else if (status.equals("wait")) {
                                System.out.println("status è wait");
                                mDatabase.child(TABLE).child(mode).child(id).child("status").setValue("full");
                                mDatabase.child(TABLE).child(mode).child(id).child("user2").setValue(user.getNickname());
                                mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        controller.setQuiz(snapshot.child(id).getValue(Quiz.class),control);
                                        String status=snapshot.child(id).child("status").getValue(String.class);
                                        if(status.equals("full")){
                                            Quiz quiz=snapshot.child(id).getValue(Quiz.class);
                                            controller.startMatch(quiz,2,control);
                                            mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                break;
                            }
                        }else{
                            System.out.println("sono nell'else");
                            String us=user.getNickname();
                            Quiz quiz=new Quiz();
                            quiz.setId(i);
                            quiz.setStatus("wait");
                            quiz.setUser2("void");
                            quiz.setUser1(us);
                            quiz.setMode(mode);
                            quiz.setNumQuesiti(10);
                            mDatabase.child(TABLE).child(mode).child(""+quiz.getId()+"").setValue(quiz);
                            mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    controller.setQuiz(snapshot.child(id).getValue(Quiz.class),control);
                                    String status = snapshot.child(id).child("status").getValue(String.class);
                                    String opponent=snapshot.child(id).child("user2").getValue(String.class);
                                    if(status.equals("full")&&(!(opponent).equals("void"))) {
                                        Quiz quiz=snapshot.child(id).getValue(Quiz.class);
                                        controller.startMatch(quiz,1,control);
                                        mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                            break;
                        }
                    }
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {

            }

        });
    }

    public void resetQuiz(Quiz quiz){
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("void");
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("user1").setValue("void");
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("user2").setValue("void");
    }

    public void updateQuiz(Quiz quiz,int player,EndMatchControl control){
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").runTransaction(new Transaction.Handler() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue()!=null){
                    Quiz current=currentData.getValue(Quiz.class);
                    if(player==1){
                        //System.out.println("sono a player == 1");
                        currentData.child("punteggioG1").setValue(quiz.getPunteggioG1());
                    }else if(player==2){
                        currentData.child("punteggioG2").setValue(quiz.getPunteggioG2());
                    }
                    if(current.getStatus().equals("full")){
                        //System.out.println("sto nell'if equals full");
                        currentData.child("status").setValue("finishing");
                        controller.wait(control);
                        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                quizInEvent = snapshot.getValue(Quiz.class);
                                Quiz q=snapshot.getValue(Quiz.class);
                                //System.out.println("MAMMA MIA");
                                if (q.getStatus().equals("finished")) {
                                    controller.finish(q,control);
                                    mDatabase.child(TABLE).child(quiz.getMode()).child("" + quiz.getId() + "").removeEventListener(this);
                                    if(quiz.getId()==0){
                                        Quiz quiz=new Quiz();
                                        quiz.setId(0);
                                        quiz.setStatus("void");
                                        quiz.setUser2("void");
                                        quiz.setUser1("void");
                                        quiz.setMode(RESTART_MODE);
                                        quiz.setNumQuesiti(10);
                                        mDatabase.child(TABLE).child(RESTART_MODE).child(""+quiz.getId()+"").setValue(quiz);
                                    }else {
                                        mDatabase.child(TABLE).child(quiz.getMode()).child("" + quiz.getId() + "").removeValue();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else if(current.getStatus().equals("finishing")){
                        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addListenerForSingleValueEvent(new ValueEventListener(){

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Quiz q=snapshot.getValue(Quiz.class);
                                controller.finish(q,control);
                                mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("finished");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }else if(current.getStatus().equals("quit")){
                        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addListenerForSingleValueEvent(new ValueEventListener(){

                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Quiz q=snapshot.getValue(Quiz.class);
                                controller.finish(q,control);
                                Quiz current=new Quiz();
                                if(q.getId()==0){
                                    current.setId(0);
                                    current.setStatus("void");
                                    current.setUser2("void");
                                    current.setUser1("void");
                                    current.setMode(RESTART_MODE);
                                    current.setNumQuesiti(10);
                                    mDatabase.child(TABLE).child(RESTART_MODE).child(""+current.getId()+"").setValue(current);
                                }else {
                                    mDatabase.child(TABLE).child(RESTART_MODE).child("" +q.getId()+ "").removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
}
