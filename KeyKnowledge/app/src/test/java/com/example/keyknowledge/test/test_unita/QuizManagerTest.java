package com.example.keyknowledge.test.test_unita;

import com.example.keyknowledge.control.EndMatchControl;
import com.example.keyknowledge.control.PairingControl;
import com.example.keyknowledge.control.QuizControl;
import com.example.keyknowledge.model.Quiz;
import com.example.keyknowledge.model.QuizManager;
import com.example.keyknowledge.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.matches;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class QuizManagerTest extends TestCase {

    private DatabaseReference mockedDatabaseReference;
    private QuizManager quizManager;
    private QuizControl quizControl;
    @Before
    public void setUp() {
        toMockSetup();
        quizManager = new QuizManager();
        quizManager.setController(quizControl);
        System.out.println("");
    }
    //methods to mock->
    private void toMockSetup(){
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
        quizControl = mock(QuizControl.class);
        doNothing().when(quizControl).setQuiz(any(Quiz.class), any(PairingControl.class));
        doNothing().when(quizControl).startMatch(any(Quiz.class), anyInt(), any(PairingControl.class));
        doNothing().when(quizControl).wait(any(EndMatchControl.class));
        doNothing().when(quizControl).finish(any(Quiz.class), any(EndMatchControl.class));
    }

    @Test
    public void testCreateQuiz(){
        toMockCreateQuiz();
        quizManager.createQuiz(new User("nick1", "password", "email", "stato"), "restart", new PairingControl());
        Quiz quiz = new Quiz(0, "restart", 10, "nick1", "nick2");
        quiz.setStatus("full");
        System.out.println("INVOCAZIONE METODO TESTING: quizManager.createQuiz(new User(\"nick1\", \"password\", \"email\", \"stato\"), \"restart\", new PairingControl())...");
        assertEquals(quiz, quizManager.getQuizInEvent());
        System.out.println("QUIZ ATTESO:\t" + quiz);
        System.out.println("QUIZ OTTENUTO:\t" + quizManager.getQuizInEvent());
        System.out.println("TEST createQuiz() PASSED");
    }
    //methods to mock->
    private void toMockCreateQuiz(){
        when(mockedDatabaseReference.child(eq(QuizManager.TABLE))).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.child(matches("restart|misc|classic"))).thenAnswer(invocation -> {
            String mode = (String) invocation.getArguments()[0];
            doAnswer(invocation1 -> {
                Transaction.Handler handler = (Transaction.Handler) invocation1.getArguments()[0];
                MutableData mutableData = mock(MutableData.class);
                when(mutableData.getValue()).thenReturn(mutableData);
                when(mutableData.hasChild(anyString())).thenReturn(true);
                when(mutableData.child(anyString())).thenReturn(mutableData);
                setStatusQuiz(mutableData, "void", handler, mode);
                //setStatusQuiz(mutableData, "wait", handler);
                return  mockedDatabaseReference;
            }).when(mockedDatabaseReference).runTransaction(any(Transaction.Handler.class));
            return mockedDatabaseReference;
        });
    }
    private void setStatusQuiz(MutableData mutableData, String statusValue, Transaction.Handler handler, String mode){
        when(mutableData.getValue(String.class)).thenReturn(statusValue);
        if(statusValue.equals("void")){
            when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
            Task<Void> mockTask = mock(Task.class);
            when(mockedDatabaseReference.setValue(eq("wait"))).thenReturn(mockTask);
            doAnswer(invocation -> {
                String nickname = (String) invocation.getArguments()[0];
                doAnswer(invocation1 -> {
                    ValueEventListener valueEventListener = (ValueEventListener) invocation1.getArguments()[0];
                    DataSnapshot dataSnapshot = mock(DataSnapshot.class);
                    doAnswer(invocation2 -> {
                        int id = Integer.parseInt((String)invocation2.getArguments()[0]);;
                        when(dataSnapshot.child(anyString())).thenReturn(dataSnapshot);
                        when(dataSnapshot.getValue(String.class)).thenReturn("full");
                        when(dataSnapshot.getValue(Quiz.class)).thenAnswer(invocation3 -> {
                           Quiz quiz = new Quiz(id, mode, 10, nickname, "nick2");
                           quiz.setStatus("full");
                           return quiz;
                        });
                        return dataSnapshot;
                    }).when(dataSnapshot).child(matches("[0-9]+"));
                    valueEventListener.onDataChange(dataSnapshot);
                    return valueEventListener;
                }).when(mockedDatabaseReference).addValueEventListener(any(ValueEventListener.class));
                return mockTask;
            }).when(mockedDatabaseReference).setValue(matches("[a-z]+[0-9]+"));
        }
        if(statusValue.equals("wait")){
            //
        }
        handler.doTransaction(mutableData);
    }

    @Test
    public void testUpdateQuiz(){
        toMockUpdateQuiz();
        Quiz quiz = new Quiz(2, "classic", 10, "nick1", "nick2");
        System.out.println("INFO: quiz = new Quiz(1, \"restart\", 10, \"nick1\", \"nick2\")");
        quiz.setPunteggioG1(2);
        System.out.println("INFO: quiz.setPunteggioG1(2)");
        quizManager.updateQuiz(quiz, 1, new EndMatchControl());
        System.out.println("INVOCAZIONE METODO TESTING: quizManager.updateQuiz(quiz, 1(player), new EndMatchControl())...");
        assertEquals(quiz.getPunteggioG1(), quizManager.getQuizInEvent().getPunteggioG1());
        assertEquals("finishing", quizManager.getQuizInEvent().getStatus());
        System.out.println("QUIZ DAL DB MODIFICATO:\n" + quizManager.getQuizInEvent());
        System.out.println("TEST testUpdateQuiz() PASSED");
    }
    //methods to mock->
    private void toMockUpdateQuiz(){
        when(mockedDatabaseReference.child(eq(QuizManager.TABLE))).thenReturn(mockedDatabaseReference);
        doAnswer(invocation -> {
            String mode = (String) invocation.getArguments()[0];
            doAnswer(invocation1 -> {
                int id = Integer.parseInt((String) invocation1.getArguments()[0]);
                doAnswer(invocation2 -> {
                    Transaction.Handler handler = (Transaction.Handler) invocation2.getArguments()[0];
                    MutableData mutableData = mock(MutableData.class);
                    Quiz current = new Quiz(id, mode, 10, "nick1", "nick2");
                    System.out.println("QUIZ DAL DB NON ANCORA MODIFICATO:\n" + current);
                    when(mutableData.getValue()).thenReturn(current);
                    when(mutableData.getValue(Quiz.class)).thenReturn(current);
                    when(mutableData.child("punteggioG1")).thenReturn(mutableData);
                    doAnswer(invocation3 -> {
                        int punteggioG1 = (int) invocation3.getArguments()[0];
                        current.setStatus("full");
                        current.setPunteggioG1(punteggioG1);
                        toMockNext(current, mutableData);
                        return mutableData;
                    }).when(mutableData).setValue(anyInt());
                    handler.doTransaction(mutableData);
                    return null;
                }).when(mockedDatabaseReference).runTransaction(any(Transaction.Handler.class));
                return  mockedDatabaseReference;
            }).when(mockedDatabaseReference).child(matches("[0-9]+"));
            return mockedDatabaseReference;
        }).when(mockedDatabaseReference).child(matches("restart|misc|classic"));
    }
    private void toMockNext(Quiz current, MutableData mutableData){
        when(mutableData.child("status")).thenReturn(mutableData);
        doNothing().when(mutableData).setValue(eq("finishing"));
        doAnswer(invocation -> {
            current.setStatus("finishing");
            ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];
            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.getValue(Quiz.class)).thenReturn(current);
            valueEventListener.onDataChange(dataSnapshot);
            return valueEventListener;
        }).when(mockedDatabaseReference).addValueEventListener(any(ValueEventListener.class));
    }

    public static junit.framework.Test suite(){
        return new TestSuite(QuizManagerTest.class);
    }

}
