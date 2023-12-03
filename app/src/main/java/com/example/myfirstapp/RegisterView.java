package com.example.myfirstapp;

public interface RegisterView {
    void showToast(String message);

    void navigateToLogin();

    void navigateToMainPage(String utorid, String password);
}
