package com.tomaszstankowski.trainingapplication.main;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.tomaszstankowski.trainingapplication.login.LoginActivity;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class MainPresenterImpl implements MainPresenter{
    private MainView mView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Inject
    public MainPresenterImpl() {
    }

    @Override
    public void onCreateView(MainView view) {
        mView = view;
    }

    @Override
    public void onStartView() {
        //check if the user is signed in
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(mView.getContext(), LoginActivity.class);
            intent.putExtra(LoginActivity.REQUEST_CODE, LoginActivity.REQUEST_CODE_LOG_IN);
            mView.startActivityForResult(
                    intent,
                    LoginActivity.REQUEST_CODE_LOG_IN);
        } else {
            mView.showHomePage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LoginActivity.REQUEST_CODE_LOG_IN
                || requestCode == LoginActivity.REQUEST_CODE_LOGGED_OUT) {
            if (resultCode == RESULT_OK) {
                mView.showHomePage();
            }
        }
    }

    @Override
    public void onDestroy(){
        mView = null;
    }
}
