package com.example.myfirstapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.example.myfirstapp.LoginPresenter;
import com.example.myfirstapp.LoginView;
import com.google.firebase.database.DatabaseReference;

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
    public void checkEmptyUtorID() {
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.onLoginButtonClicked("", "");
        verify(view).showToast("Both UTORid and password are required");
    }

    @Test
    public void test() {
        assertEquals(2 + 2, 4);
    }
}
