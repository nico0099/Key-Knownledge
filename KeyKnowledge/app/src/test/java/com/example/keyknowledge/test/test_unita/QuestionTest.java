package com.example.keyknowledge.test.test_unita;

import com.example.keyknowledge.model.Question;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Test;

public class QuestionTest extends TestCase {

    private Question question;

    @Override
    protected void setUp() throws Exception {
        question = new Question("id",
                "testo_domanda",
                "r1",
                "r2",
                "r3",
                "r4",
                1,
                "categoria",
                1);
        System.out.println("");
    }

    public static junit.framework.Test suite(){
        return new TestSuite(QuestionTest.class);
    }

    @Test
    public void testGetCategoria() {
        assertEquals("categoria", question.getCategoria());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetId() {
        assertEquals("id", question.getId());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetRisposte() {
        assertEquals("r1", question.getRisposta1());
        assertEquals("r2", question.getRisposta2());
        assertEquals("r3", question.getRisposta3());
        assertEquals("r4", question.getRisposta4());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetRisposta_esatta() {
        assertEquals(1, question.getRisposta_esatta());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetLivello(){
        assertEquals(1, question.getLivello());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetTesto() {
        assertEquals("testo_domanda", question.getTesto());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetCategoria() {
        System.out.println("Question prima " + getName() + ": " + question);
        question.setCategoria("arte");
        System.out.println("invocazione question.setCategoria(\"arte\")...");
        assertEquals("arte", question.getCategoria());
        System.out.println("Question dopo " + getName() + ": " + question);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetId() {
        System.out.println("Question prima " + getName() + ": " + question);
        question.setId("idTest");
        System.out.println("invocazione question.setId(\"idTest\")...");
        assertEquals("idTest", question.getId());
        System.out.println("Question dopo " + getName() + ": " + question);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetRisposte() {
        System.out.println("Question prima " + getName() + ": " + question);
        question.setRisposta1("r1Test");
        question.setRisposta2("r2Test");
        question.setRisposta3("r3Test");
        question.setRisposta4("r4Test");
        System.out.println("invocazione question.setRisposta1(\"r1Test\"), " +
                            "question.setRisposta2(\"r2Test\")," +
                            "question.setRisposta3(\"r3Test\")," +
                            "question.setRisposta4(\"r4Test\")...");
        assertEquals("r1Test", question.getRisposta1());
        assertEquals("r2Test", question.getRisposta2());
        assertEquals("r3Test", question.getRisposta3());
        assertEquals("r4Test", question.getRisposta4());
        System.out.println("Question dopo " + getName() + ": " + question);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetRisposta_esatta() {
        System.out.println("Question prima " + getName() + ": " + question);
        question.setRisposta_esatta(2);
        System.out.println("invocazione question.setRisposta_esatta(2)...");
        assertEquals(2, question.getRisposta_esatta());
        System.out.println("Question dopo " + getName() + ": " + question);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetTesto() {
        System.out.println("Question prima " + getName() + ": " + question);
        question.setTesto("testoTest");
        System.out.println("invocazione question.setTesto(\"testoTest\")...");
        assertEquals("testoTest", question.getTesto());
        System.out.println("Question dopo " + getName() + ": " + question);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetLivello(){
        System.out.println("Question prima " + getName() + ": " + question);
        question.setLivello(2);
        System.out.println("invocazione question.setLivello(2)...");
        assertEquals(2, question.getLivello());
        System.out.println("Question dopo " + getName() + ": " + question);
        System.out.println("Test " + getName() + " passed");
    }
}
