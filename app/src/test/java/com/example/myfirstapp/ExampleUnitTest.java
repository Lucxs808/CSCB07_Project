package com.example.myfirstapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import android.text.TextUtils;

import com.example.myfirstapp.LoginPresenter;
import com.example.myfirstapp.LoginView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ExampleUnitTest {

    @Mock
    LoginView view;

    @Mock
    DatabaseReference model;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCheckFields() {
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.onLoginButtonClicked("", "");
        verify(view).showToast("Both UTORid and password are required");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testSuccessfulStudentLogin() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);

        // Stubbing the behavior for database interactions
        when(model.child("students")).thenReturn(studentsRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(true);

            DataSnapshot passwordSnapshot = mock(DataSnapshot.class);
            when(passwordSnapshot.exists()).thenReturn(true);
            when(passwordSnapshot.getValue(String.class)).thenReturn("correctPassword");

            when(snapshot.child("password")).thenReturn(passwordSnapshot);

            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Create a presenter with the mocked view and model
        LoginPresenter presenter = new LoginPresenter(view, model);

        // Call the method to be tested
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");

        // Verify that the expected interactions occurred
        verify(view, never()).showToast("Incorrect Password");
        verify(view, never()).showToast("UTORid Not Found");
        verify(view, never()).showToast("Database error:");
        verify(view).navigateToMainPage("testUtorID");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testStudentIncorrectPassword() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(model.child("students")).thenReturn(studentsRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(true);
            DataSnapshot passwordSnapshot = mock(DataSnapshot.class);
            when(passwordSnapshot.exists()).thenReturn(true);
            when(passwordSnapshot.getValue(String.class)).thenReturn("incorrectPassword");
            when(snapshot.child("password")).thenReturn(passwordSnapshot);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");
        verify(view).showToast("Incorrect Password");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testSuccessfulAdminLogin() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference adminRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);
        DatabaseReference utoridRef = mock(DatabaseReference.class);

        // Stubbing the behavior for database interactions
        when(model.child("students")).thenReturn(studentsRef);
        when(model.child("admin")).thenReturn(adminRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);

        // Simulate UTORid not found in students
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Simulate adminRef.child(utorid) for the case when UTORid is not found in students
        when(adminRef.child(any(String.class))).thenReturn(utoridRef);

        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(true);

            DataSnapshot passwordSnapshot = mock(DataSnapshot.class);
            when(passwordSnapshot.exists()).thenReturn(true);
            when(passwordSnapshot.getValue(String.class)).thenReturn("correctPassword");

            when(snapshot.child("password")).thenReturn(passwordSnapshot);

            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(utoridRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Create a presenter with the mocked view and model
        LoginPresenter presenter = new LoginPresenter(view, model);

        // Call the method to be tested
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");

        // Verify that the expected interactions occurred
        verify(view, never()).showToast("Incorrect Password");
        verify(view, never()).showToast("UTORid Not Found");
        verify(view, never()).showToast("Database error:");
        verify(view).navigateToAdminPage("testUtorID");  // This should be invoked now
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testIncorrectAdminPassword() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference adminRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);
        DatabaseReference utoridRef = mock(DatabaseReference.class);

        // Stubbing the behavior for database interactions
        when(model.child("students")).thenReturn(studentsRef);
        when(model.child("admin")).thenReturn(adminRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);

        // Simulate UTORid not found in students
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Simulate adminRef.child(utorid) for the case when UTORid is not found in students
        when(adminRef.child(any(String.class))).thenReturn(utoridRef);

        // Simulate incorrect password in admin node
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(true);

            DataSnapshot passwordSnapshot = mock(DataSnapshot.class);
            when(passwordSnapshot.exists()).thenReturn(true);
            when(passwordSnapshot.getValue(String.class)).thenReturn("incorrectPassword"); // Incorrect password

            when(snapshot.child("password")).thenReturn(passwordSnapshot);

            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(utoridRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Create a presenter with the mocked view and model
        LoginPresenter presenter = new LoginPresenter(view, model);

        // Call the method to be tested
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");

        verify(view).showToast("Incorrect Password");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testAdminUtoridNotFound() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference adminRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);
        DatabaseReference utoridRef = mock(DatabaseReference.class);

        // Stubbing the behavior for database interactions
        when(model.child("students")).thenReturn(studentsRef);
        when(model.child("admin")).thenReturn(adminRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);

        // Simulate UTORid not found in students
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Simulate adminRef.child(utorid) for the case when UTORid is not found in students
        when(adminRef.child(any(String.class))).thenReturn(utoridRef);

        // Simulate non-existent UTORid in admin
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false); // UTORid not found in admin

            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(utoridRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Create a presenter with the mocked view and model
        LoginPresenter presenter = new LoginPresenter(view, model);

        // Call the method to be tested
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");

        verify(view).showToast("UTORid Not Found");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testDatabaseError() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);

        when(model.child("students")).thenReturn(studentsRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);

        // Simulate UTORid not found in students
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Simulate a database error in onCancelled
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onCancelled(mock(DatabaseError.class));
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Create a presenter with the mocked view and model
        LoginPresenter presenter = new LoginPresenter(view, model);

        // Call the method to be tested
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");

        // Verify that the expected interactions occurred
        verify(view).showToast("Database error: null");  // You can adjust the expected error message if needed
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testDatabaseErrorInAdminLogin() {
        DatabaseReference studentsRef = mock(DatabaseReference.class);
        DatabaseReference adminRef = mock(DatabaseReference.class);
        DatabaseReference studentRef = mock(DatabaseReference.class);
        DatabaseReference utoridRef = mock(DatabaseReference.class);

        when(model.child("students")).thenReturn(studentsRef);
        when(model.child("admin")).thenReturn(adminRef);
        when(studentsRef.child(any(String.class))).thenReturn(studentRef);

        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(studentRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        when(adminRef.child(any(String.class))).thenReturn(utoridRef);

        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.exists()).thenReturn(false); // UTORid not found in admin

            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(utoridRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onCancelled(mock(DatabaseError.class));
            return null;
        }).when(utoridRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        LoginPresenter presenter = new LoginPresenter(view, model);

        // Call the method to be tested
        presenter.onLoginButtonClicked("testUtorID", "correctPassword");

        verify(view).showToast("Database Error: null");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testRegisterButtonClicked() {
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.onRegisterNowClicked();
        verify(view).navigateToRegisterPage();
        verifyNoMoreInteractions(view);
    }
}

