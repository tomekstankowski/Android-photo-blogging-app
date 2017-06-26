package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tomaszstankowski.trainingapplication.model.User;

import java.util.Arrays;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnUserFetchListener,
        LoginInteractor.OnUserSaveListener {

    private final int RC_SIGN_IN = 222;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LoginView mView;
    private LoginInteractor mInteractor;

    @Inject
    public LoginPresenterImpl(LoginInteractor interactor) {
        mInteractor = interactor;
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
                //check if the user exists in database
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    mInteractor.getUser(firebaseUser.getUid(), this);
                    mView.showProgressbar();
                } else {
                    mView.showRetryView(LoginView.Message.ERROR);
                }
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

    @Override
    public void onUserFetchSuccess(@Nullable User user) {
        //save user
        if (user == null) {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                String name = firebaseUser.getDisplayName();
                String key = firebaseUser.getUid();
                String email = mAuth.getCurrentUser().getEmail();
                if (name != null && key != null) {
                    user = new User(name, key, email);
                    mInteractor.saveUser(user, this);
                    return;
                }
            }
            mView.showRetryView(LoginView.Message.ERROR);
            mView.hideProgressbar();
        }
        //user exists in database
        else {
            mView.finish(RESULT_OK);
        }
    }

    @Override
    public void onUserFetchFailure() {
        mView.showRetryView(LoginView.Message.ERROR);
        mView.hideProgressbar();
    }

    @Override
    public void onUserSaveSuccess() {
        mView.finish(RESULT_OK);
    }

    @Override
    public void onUserSaveFailure() {
        mView.showRetryView(LoginView.Message.ERROR);
        mView.hideProgressbar();
    }
}