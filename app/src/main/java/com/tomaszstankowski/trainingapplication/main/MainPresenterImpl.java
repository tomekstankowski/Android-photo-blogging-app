package com.tomaszstankowski.trainingapplication.main;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.tomaszstankowski.trainingapplication.login.LoginActivity;

import javax.inject.Inject;

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
            mView.startActivity(new Intent(mView.getContext(), LoginActivity.class));
        }
    }

    @Override
    public void onDestroy(){
        mView = null;
    }
}
