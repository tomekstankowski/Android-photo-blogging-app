package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;


public interface LoginPresenter {

    void onCreateView(LoginView view);

    void onDestroyView();

    void onRetryButtonClicked();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
