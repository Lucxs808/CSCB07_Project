package com.example.myfirstapp;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginPresenterTest {

    @Mock
    private LoginView mockView;

    @Mock
    private DatabaseReference mockReference;

    private LoginPresenter loginPresenter;

    @Before
    public void setInitial() {
        MockitoAnnotations.openMocks(this);
        loginPresenter = new LoginPresenter(mockView);
        loginPresenter.setDatabaseReference(mockReference);
    }

    @Test
    public void testOnLoginButtonWithEmptyUTORid() {
        loginPresenter.onLoginButtonClicked("", "");
        verify(mockView).showToast("Both UTORid and password are required");
        verifyNoMoreInteractions(mockReference, mockView);
    }

    @Test
    public void testCorrectCredentials() {
        // Mock the behavior of DatabaseReference for a successful login with "John" and "test"
        DatabaseReference studentReference = mock(DatabaseReference.class);
        when(mockReference.child(eq("students")).child(eq("John"))).thenReturn(studentReference);

        // Simulate the existence of the UTORid in the database with "test" as the stored password
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(dataSnapshot.exists()).thenReturn(true);
        when(dataSnapshot.child(eq("password")).getValue(String.class)).thenReturn("test");

        // Set up the behavior when addListenerForSingleValueEvent is called
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(dataSnapshot);
            return null;
        }).when(studentReference).addListenerForSingleValueEvent(ArgumentMatchers.any());

        // Call the method with the specified credentials
        loginPresenter.onLoginButtonClicked("John", "test");

        // Verify that the correct navigation method is called
        verify(mockView).navigateToMainPage("John");
        verifyNoMoreInteractions(mockView);
    }
}
