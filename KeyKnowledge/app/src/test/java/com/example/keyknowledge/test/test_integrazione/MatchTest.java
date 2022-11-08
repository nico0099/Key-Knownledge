package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.Match;
import com.example.keyknowledge.control.MatchControl;
import com.example.keyknowledge.model.Question;
import com.example.keyknowledge.model.Quiz;
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
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class MatchTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(MatchTest.class);
    }
    private MatchControl matchControl;
    private Match match;
    private Quiz quiz;
    private DatabaseReference mockedDatabaseReference;

    @Before
    public void setUp() throws Exception {
        toMockSetUp();
        match = new Match();
        match.setFlagGraphic(false);
        quiz = new Quiz(1, "restart", 10, "user1", "user2");
        matchControl = new MatchControl(quiz, match);
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
    public void testGetMatchQuestion(){
        toMockGetQuestion();
        matchControl.getQuestion();
        Question questionInManager = matchControl.getManager().getManagerQuestion().getQuestionInEvent();
        System.out.println("INFO: Question in QuestionManager =\n" + printQuestion(questionInManager));
        Question question = match.getQuestion();
        assertEquals(questionInManager, question);
        System.out.println("INFO: Question in activity =\n" + printQuestion(question));
        System.out.println("TEST testGetQuestion() PASSED\n");
    }
    //mock methods->
    private void toMockGetQuestion(){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        doAnswer(invocation -> {
            ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.child(matches("^[a-im-z]+"))).thenAnswer(invocation1 -> {
                String categoria = (String) invocation1.getArguments()[0];
                when(dataSnapshot.child(startsWith("l"))).thenAnswer(invocation2 -> {
                    String livArgument = (String) invocation2.getArguments()[0];
                    int livello = Integer.parseInt(String.valueOf(livArgument.charAt(livArgument.length()-1)));
                    when(dataSnapshot.child(matches("^[a-im-z]+[a-z]+[0-9]+"))).thenAnswer(invocation3 -> {
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

    @Test
    public void testQuit(){
        quiz.setStatus("full");
        System.out.println("INFO: Quiz prima dell'invocazione quit() =\n" + quiz);
        toMockQuit();
        matchControl.quit(quiz);
        assertEquals("quit", quiz.getStatus());
        System.out.println("INFO: Quiz dopo l'invocazione quit() =\n" + quiz);
        System.out.println("TEST testQuit() PASSED\n");
    }
    //mock methods->
    private void toMockQuit(){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        doAnswer(invocation -> {
            ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.getValue(Quiz.class)).thenReturn(quiz);
            when(dataSnapshot.child(anyString())).thenReturn(dataSnapshot);
            when(dataSnapshot.getValue(String.class)).thenReturn(quiz.getStatus());
            when(mockedDatabaseReference.setValue(anyString())).thenReturn(null);
            quiz.setStatus("quit");
            listener.onDataChange(dataSnapshot);
            return mockedDatabaseReference;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));
    }

    private String printQuestion(Question question){
        return "Question{ id = " + question.getId() + ", categoria = " + question.getCategoria() + ", livello = " + question.getLivello() + "}";
    }
}
