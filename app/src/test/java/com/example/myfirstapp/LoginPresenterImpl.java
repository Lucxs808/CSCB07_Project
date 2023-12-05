////package com.example.myfirstapp;
////
////import org.junit.Test;
////
////import static org.junit.Assert.*;
////
/////**
//// * Example local unit test, which will execute on the development machine (host).
//// *
//// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
//// */
////public class LoginPresenterImpl {
////    @Test
////    public void addition_isCorrect() {
////        assertEquals(4, 2 + 2);
////    }
////}
//
//import static org.mockito.Mockito.verify;
//
//import com.example.myfirstapp.LoginView;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class LoginPresenterImplTest {
//
//    @Mock
//    private LoginView mockLoginView;
//
//    @Mock
//    private LoginModel mockLoginModel;
//
//    private LoginPresenterImpl loginPresenter;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        loginPresenter = new LoginPresenterImpl(mockLoginView, mockLoginModel);
//    }
//
//    @Test
//    public void testValidateCredentialsSuccess() {
//        // Arrange
//        String validUsername = "user123";
//        String validPassword = "pass123";
//
//        // Act
//        loginPresenter.validateCredentials(validUsername, validPassword);
//
//        // Assert
//        verify(mockLoginModel).login(validUsername, validPassword, loginPresenter);
//    }
//
//    @Test
//    public void testValidateCredentialsEmptyUsername() {
//        // Arrange
//        String emptyUsername = "";
//        String validPassword = "pass123";
//
//        // Act
//        loginPresenter.validateCredentials(emptyUsername, validPassword);
//
//        // Assert
//        verify(mockLoginView).showUsernameError();
//        verifyZeroInteractions(mockLoginModel);
//    }
//
//    @Test
//    public void testValidateCredentialsEmptyPassword() {
//        // Arrange
//        String validUsername = "user123";
//        String emptyPassword = "";
//
//        // Act
//        loginPresenter.validateCredentials(validUsername, emptyPassword);
//
//        // Assert
//        verify(mockLoginView).showPasswordError();
//        verifyZeroInteractions(mockLoginModel);
//    }
//
//    @Test
//    public void testOnUsernameError() {
//        // Act
//        loginPresenter.onUsernameError();
//
//        // Assert
//        verify(mockLoginView).showUsernameError();
//    }
//
//    @Test
//    public void testOnPasswordError() {
//        // Act
//        loginPresenter.onPasswordError();
//
//        // Assert
//        verify(mockLoginView).showPasswordError();
//    }
//
//    @Test
//    public void testOnSuccess() {
//        // Act
//        loginPresenter.onSuccess();
//
//        // Assert
//        verify(mockLoginView).showSuccessMessage();
//    }
//}