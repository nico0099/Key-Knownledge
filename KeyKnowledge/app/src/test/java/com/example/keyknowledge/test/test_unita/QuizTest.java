package com.example.keyknowledge.test.test_unita;

import com.example.keyknowledge.model.Quiz;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;

public class QuizTest extends TestCase {

    private Quiz quiz;

    @Override
    protected void setUp() throws Exception {
        quiz = new Quiz(1, Quiz.RESTART_MODE, 10, "nick1", "nick2");
        System.out.println("");
    }

    @Test
    public void testGetId() {
        assertEquals(1, quiz.getId());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetMode() {
        assertEquals(Quiz.RESTART_MODE, quiz.getMode());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetNumQuesiti() {
        assertEquals(10, quiz.getNumQuesiti());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetUsers() {
        assertEquals("nick1", quiz.getUser1());
        assertEquals("nick2", quiz.getUser2());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetPunteggi() {
        assertEquals(-1, quiz.getPunteggioG1());
        assertEquals(-1, quiz.getPunteggioG2());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetStatus() {
        assertEquals(null, quiz.getStatus());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetId() {
        System.out.println("Quiz prima " + getName() + ":\n" + quiz);
        quiz.setId(2);
        System.out.println("invocazione quiz.setId(2)...");
        assertEquals(2, quiz.getId());
        System.out.println("Quiz dopo " + getName() + ":\n" + quiz);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetMode() {
        System.out.println("Quiz prima " + getName() + ":\n" + quiz);
        quiz.setMode(Quiz.CLASSIC_MODE);
        System.out.println("invocazione quiz.setMode(Quiz.CLASSIC_MODE)...");
        assertEquals(Quiz.CLASSIC_MODE, quiz.getMode());
        System.out.println("Quiz dopo " + getName() + ":\n" + quiz);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetNumQuesiti() {
        System.out.println("Quiz prima " + getName() + ":\n" + quiz);
        quiz.setNumQuesiti(15);
        System.out.println("invocazione quiz.setMode(Quiz.CLASSIC_MODE)...");
        assertEquals(15, quiz.getNumQuesiti());
        System.out.println("Quiz dopo " + getName() + ":\n" + quiz);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetUsers() {
        System.out.println("Quiz prima " + getName() + ":\n" + quiz);
        quiz.setUser1("nick1Test");
        quiz.setUser2("nick2Test");
        System.out.println("invocazione quiz.setUser1(\"nick1Test\")...");
        System.out.println("invocazione quiz.setUser2(\"nick2Test\")...");
        assertEquals("nick1Test", quiz.getUser1());
        assertEquals("nick2Test", quiz.getUser2());
        System.out.println("Quiz dopo " + getName() + ":\n" + quiz);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetPunteggi() {
        System.out.println("Quiz prima " + getName() + ":\n" + quiz);
        quiz.setPunteggioG1(1);
        quiz.setPunteggioG2(1);
        System.out.println("invocazione quiz.setPunteggioG1(quiz.getPunteggioG1()+1)...");
        System.out.println("invocazione quiz.setPunteggioG2(quiz.getPunteggioG2()+1)...");
        assertEquals(1, quiz.getPunteggioG1());
        assertEquals(1, quiz.getPunteggioG2());
        System.out.println("Quiz dopo " + getName() + ":\n" + quiz);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetStatus() {
        System.out.println("Quiz prima " + getName() + ":\n" + quiz);
        quiz.setStatus("full");
        System.out.println("invocazione quiz.setStatus(\"full\")...");
        assertEquals("full", quiz.getStatus());
        System.out.println("Quiz dopo " + getName() + ":\n" + quiz);
        System.out.println("Test " + getName() + " passed");
    }

    public static junit.framework.Test suite(){
        return new TestSuite(QuizTest.class);
    }

}
