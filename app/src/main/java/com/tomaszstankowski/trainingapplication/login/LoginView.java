package com.tomaszstankowski.trainingapplication.login;

import com.firebase.ui.auth.AuthUI;


interface LoginView {
    enum Message {
        NO_NETWORK,
        ERROR,
        SIGN_IN_REQUIRED
    }

    void startAuthUI(AuthUI.SignInIntentBuilder builder);

    void showLoggedOutView();

    void showRetryView(LoginView.Message message);

    void showProgressbar();

    void hideProgressbar();

    void finish(int resultCode);
}
