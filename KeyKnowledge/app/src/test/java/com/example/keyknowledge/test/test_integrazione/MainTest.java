package com.example.keyknowledge.test.test_integrazione;

import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.R;
import com.example.keyknowledge.control.MainControl;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class MainTest extends TestCase {

    public static junit.framework.Test suite(){
        return new TestSuite(MainTest.class);
    }
    private MainActivity main;
    private MainControl mainControl;
    private DatabaseReference mockedDatabaseReference;
    private User user;

    @Before
    public void setUp() throws Exception{
        toMockSetUp();
        main = new MainActivity();
        main.setGraphicFlag(false);
        mainControl = new MainControl(main);
        mainControl.setIntentFlag(false);
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
    public void testLogout(){
        user = new User("niconico", "password", "niconico@gmail.com", UserManager.ONLINE);
        System.out.println("INFO: chiamata con utente " + user.getNickname());
        toMockLogout();
        mainControl.logout(user);
        assertEquals(UserManager.OFFLINE, user.getStato());
        System.out.println("INFO: Stato utente " + user.getNickname() + " dopo il logout = " + user.getStato());
        System.out.println("TEST testLogout() PASSED\n");
    }
    //mock methods->
    private void toMockLogout(){
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.setValue(anyString())).thenReturn(null);
        user.setStato(UserManager.OFFLINE);
    }

    @Test
    public void testBackHome(){
        System.out.println("INFO: Utenti preseneti nel db-->");
        System.out.println("1. nickname: niconico\tpassword = password");
        System.out.println("2. nickname: crex99\t\tpassword = password");
        System.out.println("3. nickname: gioo\t\tpassword = password\n");
        user = new User("niconico", "password", "niconico@gmail.com", UserManager.OFFLINE);
        System.out.println("INFO: chiamata con utente " + user.getNickname() + ", stato: " + user.getStato());
        toMockBackHome();
        mainControl.backHome(user.getNickname());
        assertEquals(user.getNickname(), main.getUser().getNickname());
        assertEquals(UserManager.ONLINE, main.getUser().getStato());
        assertEquals(R.layout.home, main.getLayout());
        System.out.println("INFO: Stato utente " + user.getNickname() + " dopo la chiamata backHome(): " + user.getStato());
        System.out.println("INFO: Layout activity: " + getStringLayout(main.getLayout()));
        System.out.println("TEST backHome() PASSED\n");
    }

    private String getStringLayout(int layout){
        if(layout == R.layout.home){
            return "LAYOUT-HOME";
        }else return "LAYOUT-BENVENUTO";
    }
    //mock methods->
    private void toMockBackHome(){
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
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.setValue(anyString())).thenReturn(null);
        user.setStato(UserManager.ONLINE);
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
