package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mView;
    private final int RC_SIGN_IN = 222;

    @Inject
    public LoginPresenterImpl() {
    }

    @Override
    public void onCreateView(LoginView view, int requestCode) {
        mView = view;
        switch (requestCode) {
            case LoginActivity.REQUEST_CODE_LOG_IN:
                startAuth();
                break;
            case LoginActivity.REQUEST_CODE_LOGGED_OUT:
                mView.showLoggedOutView();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }

    @Override
    public void onLogInButtonClicked() {
        startAuth();
    }

    private void startAuth() {
        mView.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()
                                )
                        )
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setTheme(mView.getThemeId())
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                mView.finish(RESULT_OK);
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    mView.showRetryView(LoginView.Message.SIGN_IN_REQUIRED);
                } else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    mView.showRetryView(LoginView.Message.NO_NETWORK);
                } else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    mView.showRetryView(LoginView.Message.ERROR);
                }
            }
        }
    }
}
