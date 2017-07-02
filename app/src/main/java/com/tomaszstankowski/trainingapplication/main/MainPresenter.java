package com.tomaszstankowski.trainingapplication.main;

/**
 * Presenter responding to MainActivity calls
 */

public interface MainPresenter {

    void onCreateView(MainView view);

    void onLoginViewResult(int resultCode);

    void onDestroy();
}
