//package com.example.myfirstapp;
//
//import org.junit.Test;
//import org.junit.Before;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.Assert.*;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
//
//    @Mock
//    private LoginView mockView;
//    @Mock
//    private DatabaseReference mockReference;
//
//    private LoginPresenter loginPresenter;
//
//    @Before
//    public void setInitial(){
//        MockitoAnnotations.openMocks(this);
//        loginPresenter = new LoginPresenter(mockView);
//        loginPresenter.setDatabaseReference(mockReference);
//    }
//    @Test
//    public void testOnLoginButtonWithEmptyUTORid() {
//        loginPresenter.onLoginButtonClicked("", "");
//        Mockito.verify(mockView).showToast("Both UTORid and password are required");
//        Mockito.verifyNoMoreInteractions(mockReference, mockView);
//    }
//    @Test
//    public void testCorrectCredentials(){
//        // Mock the behavior of DatabaseReference for a successful login with "John" and "test"
//        DatabaseReference studentReference = Mockito.mock(DatabaseReference.class);
//        Mockito.when(mockReference.child("students").child(ArgumentMatchers.eq("John"))).thenReturn(studentReference);
//
//        // Simulate the existence of the UTORid in the database with "test" as the stored password
//        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
//        Mockito.when(dataSnapshot.exists()).thenReturn(true);
//        Mockito.when(dataSnapshot.child("password").getValue(String.class)).thenReturn("test");
//
//        // Set up the behavior when addListenerForSingleValueEvent is called
//        Mockito.doAnswer(invocation -> {
//            ValueEventListener listener = invocation.getArgument(0);
//            listener.onDataChange(dataSnapshot);
//            return null;
//        }).when(studentReference).addListenerForSingleValueEvent(Mockito.any());
//
//        // Call the method with the specified credentials
//        loginPresenter.onLoginButtonClicked("John", "test");
//
//        // Verify that the correct navigation method is called
//        Mockito.verify(mockView).navigateToMainPage("John");
//        Mockito.verifyNoMoreInteractions(mockView);
//    }
//}
// Did not understand JUnit Tests, rought examples not working pls fix.