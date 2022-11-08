package com.example.keyknowledge.test.test_unita;

import com.example.keyknowledge.model.User;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Test;

public class UserTest extends TestCase {

    private User user;

    @Override
    protected void setUp() throws Exception {
        user = new User("nick", "password", "email", "offline");
        System.out.println("");
    }

    public static junit.framework.Test suite(){
        return new TestSuite(UserTest.class);
    }

    @Test
    public void testGetNickname() {
        assertEquals("nick", user.getNickname());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetEmail() {
        assertEquals("email", user.getEmail());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetStato(){
        assertEquals("offline", user.getStato());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetRuolo(){
        assertEquals("giocatore", user.getRuolo());
        System.out.println("Test " + getName() + " passed");;
    }

    @Test
    public void testGetNumPartiteGiocate(){
        assertEquals(0, user.getNumPartiteGiocate());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testGetNumPartiteVinte(){
        assertEquals(0, user.getNumPartiteVinte());
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetNickname(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setNickname("nickTest");
        System.out.println("invocazione user.setNickname(\"nickTest\")...");
        assertEquals("nickTest", user.getNickname());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetEmail(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setEmail("emailTest");
        System.out.println("invocazione user.setEmail(\"emailTest\")...");
        assertEquals("emailTest", user.getEmail());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetPassword(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setPassword("passwordTest");
        System.out.println("invocazione user.setPassword(\"passwordTest\")...");
        assertEquals("passwordTest", user.getPassword());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetStato(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setStato("online");
        System.out.println("invocazione user.setStato(\"online\")...");
        assertEquals("online", user.getStato());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void testSetRuolo(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setRuolo("ruoloTest");
        System.out.println("invocazione user.setRuolo(\"ruoloTest\")...");
        assertEquals("ruoloTest", user.getRuolo());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void setNumPartiteGiocate(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setNumPartiteGiocate(user.getNumPartiteGiocate()+1);
        System.out.println("invocazione user.setNumPartiteGiocate(user.getNumPartiteGiocate()+1)...");
        assertEquals(1, user.getNumPartiteGiocate());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }

    @Test
    public void setNumPartiteVinte(){
        System.out.println("User prima " + getName() + ":\n" + user);
        user.setNumPartiteVinte(user.getNumPartiteVinte()+1);
        System.out.println("invocazione user.setNumPartiteVinte(user.getNumPartiteVinte()+1)...");
        assertEquals(1, user.getNumPartiteVinte());
        System.out.println("User dopo " + getName() + ":\n" + user);
        System.out.println("Test " + getName() + " passed");
    }


}
