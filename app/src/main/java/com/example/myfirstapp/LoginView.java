package com.example.myfirstapp;

public interface LoginView {
    void showToast(String message);
    void navigateToMainPage(String utorid);
    void navigateToAdminPage(String utorid);
    void navigateToRegisterPage();
}

