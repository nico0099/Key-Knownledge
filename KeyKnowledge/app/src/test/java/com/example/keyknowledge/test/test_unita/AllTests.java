package com.example.keyknowledge.test.test_unita;


import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AllTests {

    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTest(QuestionManagerTest.suite());
        //suite.addTest(QuestionTest.suite());
        suite.addTest(QuizManagerTest.suite());
        //suite.addTest(QuizTest.suite());
        suite.addTest(UserManagerTest.suite());
        //suite.addTest(UserTest.suite());
        return suite;
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }
}
