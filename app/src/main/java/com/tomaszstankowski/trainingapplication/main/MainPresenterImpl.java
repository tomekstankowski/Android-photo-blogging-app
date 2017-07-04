package com.tomaszstankowski.trainingapplication.main;



import com.google.firebase.auth.FirebaseAuth;
import com.tomaszstankowski.trainingapplication.Config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter{
    private MainView mView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Inject
    MainPresenterImpl() {
    }

    @Override
    public void onCreateView(MainView view) {
        mView = view;
        if (mAuth.getCurrentUser() == null)
            mView.startLoginView(Config.LOGIN_MODE_DEFAULT);
    }

    @Override
    public void onLoginViewResult(int resultCode) {
        if (resultCode == Config.LOGIN_RESULT_OK)
            mView.navigate(MainView.Navigable.HOME, null);
    }

    @Override
    public void onNavigateRequest(MainView.Navigable navigable) {
        switch (navigable) {
            case HOME:
                mView.navigate(MainView.Navigable.HOME, null);
                break;
            case DISCOVER:
                mView.navigate(MainView.Navigable.DISCOVER, null);
                break;
            case MY_PROFILE:
                Map<String, Serializable> args = new HashMap<>();
                //show logged user profile
                args.put(Config.USER_DETAILS_MODE, Config.USER_DETAILS_MODE_CURRENT);
                mView.navigate(MainView.Navigable.MY_PROFILE, args);
                break;
            case SETTINGS:
                mView.navigate(MainView.Navigable.SETTINGS, null);
                break;
        }
    }
    @Override
    public void onDestroy(){
        mView = null;
    }
}
