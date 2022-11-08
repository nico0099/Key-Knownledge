package com.example.keyknowledge.test.test_unita;

import com.example.keyknowledge.control.QuestionControl;
import com.example.keyknowledge.model.Question;
import com.example.keyknowledge.model.QuestionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import static org.mockito.Matchers.matches;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class QuestionManagerTest extends TestCase {

    private DatabaseReference mockedDatabaseReference;
    private QuestionManager questionManager;
    private QuestionControl control;

    @Before
    public void setUp() {
        toMockSetUp();
        questionManager = new QuestionManager();
        questionManager.setControl(control);
    }
    //mock methods->
    private void toMockSetUp(){
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
        control = mock(QuestionControl.class);
        doNothing().when(control).setQuestion(any(Question.class));
    }

    @Test
    public void testGetQuestion(){
        toMockGetQuestion();
        questionManager.getQuestion("storia", "livello1", "storia1");
        System.out.println("INVOCAZIONE METODO TESTING: questionManager.getQuestion(\"storia\", \"livello1\", \"storia1\")...");
        assertEquals(new Question("storia1",
                                "testo_domanda",
                                "r1",
                                "r2",
                                "r3",
                                "r4",
                                1,
                                "storia",
                                1),
                                questionManager.getQuestionInEvent());
        System.out.println("QUESTION ASPETTATA: " +
                new Question("storia1",
                "testo_domanda",
                "r1",
                "r2",
                "r3",
                "r4",
                1,
                "storia",
                1));
        System.out.println("QUESTION ATTUALE: " + questionManager.getQuestionInEvent());
        System.out.println("TEST testGetQuestion PASSED\n");
    }
    //mock methods->
    private void toMockGetQuestion(){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        doAnswer(invocation -> {
            ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.child(matches("[a-z]+"))).thenAnswer(invocation1 -> {
                String categoria = (String) invocation1.getArguments()[0];
                when(dataSnapshot.child(startsWith("l"))).thenAnswer(invocation2 -> {
                    String livArgument = (String) invocation2.getArguments()[0];
                    int livello = Integer.parseInt(String.valueOf(livArgument.charAt(livArgument.length()-1)));
                    when(dataSnapshot.child(matches("^" + categoria + "[1-4]{1}"))).thenAnswer(invocation3 -> {
                        String id = (String) invocation3.getArguments()[0];
                        when(dataSnapshot.getValue(Question.class)).thenReturn(
                                new Question(
                                        id,
                                        "testo_domanda",
                                        "r1",
                                        "r2",
                                        "r3",
                                        "r4",
                                        1,
                                        categoria,
                                        livello));
                        return dataSnapshot;
                    });
                    return dataSnapshot;
                });
                return dataSnapshot;
            });
            listener.onDataChange(dataSnapshot);
            return mockedDatabaseReference;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));
    }


    public static junit.framework.Test suite(){
        return new TestSuite(QuestionManagerTest.class);
    }
}
