package com.tomaszstankowski.trainingapplication.main;

public class MainPresenterImpl implements MainPresenter{
    private MainView mView;

    public MainPresenterImpl(MainView view){
        mView = view;
    }

    @Override
    public void onDestroy(){
        mView = null;
    }
}
