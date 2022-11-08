package com.example.keyknowledge.test.test_unita;

import com.example.keyknowledge.model.MainManager;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class UserManagerTest extends TestCase {

    private DatabaseReference mockedDatabaseReference;
    private UserManager userManager;
    private MainManager mainManager;

    @Before
    public void setUp() {
        toMockSetUp();
        userManager = new UserManager();
    }
    //mock methods->
    private void toMockSetUp(){
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        //snapshot = Mockito.mock(DataSnapshot.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
        mainManager = mock(MainManager.class);
        doNothing().when(mainManager).accessUser(any(User.class));
    }

    @Test
    public void testGetUser() {
        toMockGetUser();
        userManager.getUser("nick", mainManager);
        System.out.println("INVOCAZIONE METODO TESTING: userManager.getUser(\"nick\", mainManager)...");
        assertEquals(new User("nick", "password", "email", "offline"), userManager.getUserInEvent());
        System.out.printf("UTENTE ASPETTATO:\n%s%n", new User("nick", "password", "email", "stato"));
        System.out.printf("UTENTE ATTUALE:\n%s%n", userManager.getUserInEvent());
        System.out.println("TEST getUser() PASSED\n");
    }
    //mock methods->
    private void toMockGetUser(){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                DataSnapshot dataSnapshot = mock(DataSnapshot.class);
                when(dataSnapshot.child(anyString())).thenAnswer(invocation1 -> {
                    String nick = ((String) invocation1.getArguments()[0]);
                    when(dataSnapshot.getValue(User.class)).thenReturn(new User(nick, "password", "email", "offline"));
                    return dataSnapshot;
                });
                //DataSnapshot dataSnapshot = new DataSnapshotMock();
                listener.onDataChange(dataSnapshot);
                return null;
            }
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));
    }



    @Test
    public void testSetState(){
        toMockGetUser();
        toMockSetState();
        userManager.getUser("nick", mainManager);
        assertEquals("offline", userManager.getUserInEvent().getStato());
        System.out.println("UTENTE PRIMA setState():\n" + userManager.getUserInEvent());
        userManager.setState("online", "nick");
        System.out.println("INVOCAZIONE METODO TESTING: userManager.setState(\"online\", \"nick\")...");
        userManager.getUser("nick", mainManager);
        assertEquals("online", userManager.getUserInEvent().getStato());
        System.out.println("UTENTE DOPO setState():\n" + userManager.getUserInEvent());
        System.out.println("TEST setState() PASSED\n");
    }
    //mock methods->
    private void toMockSetState(){
        when(mockedDatabaseReference.child(anyString())).thenAnswer(invocation -> {
            if(!invocation.getArguments()[0].equals(UserManager.TABLE) && !invocation.getArguments()[0].equals("stato")){
                String nick = (String) invocation.getArguments()[0];
                when(mockedDatabaseReference.setValue(anyString())).thenAnswer(invocation1 -> {
                    String state = (String) invocation1.getArguments()[0];
                    doAnswer(new Answer<Void>() {
                        @Override
                        public Void answer(InvocationOnMock invocation) throws Throwable {
                            System.out.println("INFO: invocazione 2 metodo answer() in testSetState...");
                            ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
                            when(dataSnapshot.child(anyString())).thenAnswer(invocation1 -> {
                                String argument = ((String) invocation1.getArguments()[0]);
                                assertEquals(nick, argument);
                                when(dataSnapshot.getValue(User.class)).thenReturn(new User(argument, "password", "email", state));
                                return dataSnapshot;
                            });
                            //DataSnapshot dataSnapshot = new DataSnapshotMock();
                            listener.onDataChange(dataSnapshot);
                            return null;
                        }
                    }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));
                    return null;
                });
            }
            return mockedDatabaseReference;
        });
    }

    public static junit.framework.Test suite(){
        return new TestSuite(UserManagerTest.class);
    }
}
