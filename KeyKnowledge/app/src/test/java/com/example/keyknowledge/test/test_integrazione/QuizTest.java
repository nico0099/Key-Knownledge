package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.Pairing;
import com.example.keyknowledge.control.PairingControl;
import com.example.keyknowledge.control.QuestionControl;
import com.example.keyknowledge.control.QuizControl;
import com.example.keyknowledge.model.Quiz;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class QuizTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(QuizTest.class);
    }
    private DatabaseReference mockedDatabaseReference;
    private QuizControl quizControl;
    private Quiz quiz;
    private Pairing pairing;

    @Before
    public void setUp() throws Exception{
        toMockSetUp();
        quizControl = new QuizControl();
        quiz = new Quiz(1, Quiz.RESTART_MODE, 10, "nick1", "nick2");
        pairing = new Pairing();
        pairing.setGraphicFlag(false);
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
    public void testSetQuiz(){
        quizControl.setQuiz(quiz, new PairingControl(pairing));
        assertEquals(quiz, pairing.getQuiz());
        System.out.println("INFO:Quiz = " + quiz);
        System.out.println("INFO:Quiz in activity = " + pairing.getQuiz());
    }
}
