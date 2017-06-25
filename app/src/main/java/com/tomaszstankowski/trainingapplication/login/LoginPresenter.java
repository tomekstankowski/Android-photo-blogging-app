package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;


public interface LoginPresenter {

    void onCreateView(LoginView view, int requestCode);

    void onDestroyView();

    void onLogInButtonClicked();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
