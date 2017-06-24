package com.tomaszstankowski.trainingapplication.main;

/**
 * Presenter responding to MainActivity calls
 */

public interface MainPresenter {

    void onCreateView(MainView view);

    void onStartView();

    void onDestroy();
}
