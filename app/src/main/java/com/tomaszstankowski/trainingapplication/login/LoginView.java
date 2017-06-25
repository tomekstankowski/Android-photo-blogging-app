package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;


public interface LoginView {
    enum Message {
        NO_NETWORK,
        ERROR,
        SIGN_IN_REQUIRED
    }

    void startActivityForResult(Intent intent, int requestCode);

    void showLoggedOutView();

    void showRetryView(LoginView.Message message);

    int getThemeId();

    void finish(int resultCode);
}
