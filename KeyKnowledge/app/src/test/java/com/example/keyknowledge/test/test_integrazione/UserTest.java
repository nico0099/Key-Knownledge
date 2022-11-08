package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.Login;
import com.example.keyknowledge.control.LoginControl;
import com.example.keyknowledge.control.UserControl;
import com.example.keyknowledge.model.User;
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
public class UserTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(UserTest.class);
    }
    private DatabaseReference mockedDatabaseReference;
    private UserControl userControl;
    private User user;

    @Before
    public void setUp() throws Exception{
        toMockSetUp();
        userControl = new UserControl();

        user = new User("nick", "password", "email", "offline");
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
    public void testSaveUser(){
        Login login = new Login();
        login.setFlagEditor(false);
        LoginControl loginControl = new LoginControl(login);
        loginControl.setFlagIntent(false);
        userControl.saveUser(user, loginControl);
        assertEquals(user, login.getUser());
        System.out.println("INFO:User =\n" + user);
        System.out.println("INFO:User in activity =\n" + login.getUser());
    }
}
