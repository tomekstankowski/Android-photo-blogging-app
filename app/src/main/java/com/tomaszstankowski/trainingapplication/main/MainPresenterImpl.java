package com.tomaszstankowski.trainingapplication.main;



import com.google.firebase.auth.FirebaseAuth;
import com.tomaszstankowski.trainingapplication.Config;

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
        if (mAuth.getCurrentUser() != null)
            mView.showHomePage();
    }

    @Override
    public void onStartView() {
        //check if the user is signed in
        if (mAuth.getCurrentUser() == null)
            mView.startLoginView(Config.LOGIN_VIEW_MODE_DEFAULT);
    }

    @Override
    public void onLoginViewResult(int resultCode) {
        if (resultCode == Config.LOGIN_RESULT_OK)
            mView.showHomePage();
    }

    @Override
    public void onDestroy(){
        mView = null;
    }
}
