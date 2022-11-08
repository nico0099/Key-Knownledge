package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.Pairing;
import com.example.keyknowledge.control.PairingControl;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.matches;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class PairingTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(PairingTest.class);
    }

    private DatabaseReference mockedDatabaseReference;
    private Pairing pairing;
    private PairingControl pairingControl;
    private User user1, user2;
    private static final String RESTART = "restart", CLASSIC = "classic", MISC = "misc";

    @Before
    @Override
    public void setUp() throws Exception{
        toMockSetUp();
        pairing = new Pairing();
        pairing.setGraphicFlag(false);
        pairingControl = new PairingControl(pairing);
        pairingControl.setIntentFlag(false);
        user1 = new User("niconico", "password", "niconico@gmail.com", "online");
        user2 = new User("crex99", "password", "crex99@gmail.com", "online");
    }
    //mock methods->
    private void toMockSetUp(){
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
    }

    @Test
    public void testCreateMatch(){
        System.out.println("INFO: Utente in abbinamento =\n" + user1);
        System.out.println("INFO: Altro utente in abbinamento =\n" + user2);
        toMockCreateQuiz();
        pairingControl.createMatch(user1, RESTART);
        Quiz quizInMenager = pairingControl.getManager().getManagerQuiz().getQuizInEvent();
        Quiz quiz = pairing.getQuiz();
        assertEquals(quizInMenager, quiz);
        assertEquals(user1.getNickname(), quiz.getUser1());
        assertEquals(user2.getNickname(), quiz.getUser2());
        System.out.println("INFO: Quiz generato dopo invocazione =\n" + quiz);
        System.out.println("TEST testCreateMatch() PASSED\n");
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
                            Quiz quiz = new Quiz(id, mode, 10, nickname, user2.getNickname());
                            quiz.setStatus("full");
                            return quiz;
                        });
                        return dataSnapshot;
                    }).when(dataSnapshot).child(matches("[0-9]+"));
                    valueEventListener.onDataChange(dataSnapshot);
                    return valueEventListener;
                }).when(mockedDatabaseReference).addValueEventListener(any(ValueEventListener.class));
                return mockTask;
            }).when(mockedDatabaseReference).setValue(matches("[a-z0-9]+"));
        }
        if(statusValue.equals("wait")){
            //
        }
        handler.doTransaction(mutableData);
    }


    @Test
    public void testResetMatch(){
        Quiz quiz = new Quiz(0, "restart", 10, "nick1", "nick2");
        System.out.println("INFO: Quiz prima di resetMatch() =\n" + quiz);
        toMockResetQuiz(quiz);
        pairingControl.resetMatch(quiz);
        assertEquals("void", quiz.getStatus());
        assertEquals("void", quiz.getUser1());
        assertEquals("void", quiz.getUser2());
        System.out.println("INFO: Quiz dopo resetMatch() =\n" + quiz);
        System.out.println("TEST testResetMatch() PASSED\n");
    }
    //methods to mock->
    private void toMockResetQuiz(Quiz quiz){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.setValue(anyString())).thenReturn(null);
        quiz.setStatus("void");
        quiz.setUser1("void");
        quiz.setUser2("void");
    }

}
