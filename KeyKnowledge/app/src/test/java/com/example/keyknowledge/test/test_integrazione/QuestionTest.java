package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.Match;
import com.example.keyknowledge.control.MatchControl;
import com.example.keyknowledge.control.QuestionControl;
import com.example.keyknowledge.model.Question;
import com.example.keyknowledge.model.Quiz;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class QuestionTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(QuestionTest.class);
    }
    private DatabaseReference mockedDatabaseReference;
    private QuestionControl questionControl;
    private Question question;
    private Match match;

    @Before
    public void setUp() throws Exception{
        toMockSetUp();
        match = new Match();
        match.setFlagGraphic(false);
        questionControl = new QuestionControl(new MatchControl(new Quiz(), match));
        question = new Question("id",
                "testo_domanda",
                "r1",
                "r2",
                "r3",
                "r4",
                1,
                "categoria",
                1);
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
    public void testSetQuestion(){
        questionControl.setQuestion(question);
        assertEquals(question, match.getQuestion());
        System.out.println("INFO:Question =\n" + question);
        System.out.println("INFO:Question in activity =\n" + match.getQuestion());
    }

}
