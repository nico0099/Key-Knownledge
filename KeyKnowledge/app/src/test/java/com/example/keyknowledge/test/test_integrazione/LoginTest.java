package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.Login;
import com.example.keyknowledge.control.LoginControl;
import com.example.keyknowledge.model.LoginManager;
import com.example.keyknowledge.model.User;
import com.example.keyknowledge.model.UserManager;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.Scanner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class LoginTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(LoginTest.class);
    }
    private Login login;
    private LoginControl loginControl;
    private LoginManager loginManager;
    private DatabaseReference mockedDatabaseReference;

    @Before
    public void setUp() throws Exception {
        toMockSetUp();
        login = new Login();
        login.setFlagEditor(false);
        login.setFlagGraphic(false);
        loginControl = new LoginControl(login);
        loginControl.setFlagIntent(false);
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
    public void testLogin(){
        String nick = "niconico", pass = "password";
        System.out.println("INFO: Utenti preseneti nel db-->");
        System.out.println("1. nickname: niconico\tpassword = password");
        System.out.println("2. nickname: crex99\t\tpassword = password");
        System.out.println("3. nickname: gioo\t\tpassword = password\n");
        System.out.println("INFO: Login utente con nickname = " + nick + ", password = " + pass + "...");
        toMockGetUser();
        loginControl.access(nick, pass);
        User userInActivity = login.getUser();
        if(userInActivity == null){
            assertEquals(null, userInActivity);
            System.out.println(login.getMessage());
        }else{
            assertEquals(nick, userInActivity.getNickname());
            assertEquals(pass, userInActivity.getPassword());
            assertEquals(UserManager.ONLINE, userInActivity.getStato());
            System.out.println("INFO: Utente = \n" + userInActivity);
        }

        System.out.println("TEST testAccess() passed");
    }
    //mock methods->
    private void toMockGetUser( ){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                DataSnapshot dataSnapshot = mock(DataSnapshot.class);
                when(dataSnapshot.child(anyString())).thenAnswer(invocation1 -> {
                    String nick = ((String) invocation1.getArguments()[0]);
                    when(dataSnapshot.getValue(User.class)).thenReturn(getUser(nick));
                    return dataSnapshot;
                });
                //DataSnapshot dataSnapshot = new DataSnapshotMock();
                listener.onDataChange(dataSnapshot);
                return null;
            }
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));
        when(mockedDatabaseReference.setValue(anyString())).thenReturn(null);
    }
    private User getUser(String nick){
        if(nick.equals("niconico")) {
            return new User(nick, "password", "nico@gmail.com", "offline");
        }else if(nick.equals("gioo")) {
            return new User(nick, "password", "gio@gmail.com", "offline");
        }else if(nick.equals("crex99")) {
            return new User(nick, "password", "crex99@gmail.com", "offline");
        }else return null;
    }


}
