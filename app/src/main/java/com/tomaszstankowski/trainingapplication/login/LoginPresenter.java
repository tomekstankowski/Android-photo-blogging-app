package com.tomaszstankowski.trainingapplication.login;


import com.firebase.ui.auth.IdpResponse;


public interface LoginPresenter {

    /**
     * @param mode is view behaviour requested by parent
     */
    void onCreateView(LoginView view, int mode);

    void onDestroyView();

    void onLogInButtonClicked();

    void onAuthUIResult(int resultCode, IdpResponse response);
}
