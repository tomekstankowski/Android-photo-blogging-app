package com.tomaszstankowski.trainingapplication.main;

import android.content.Intent;

/**
 * Presenter responding to MainActivity calls
 */

public interface MainPresenter {

    void onCreateView(MainView view);

    void onStartView();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onDestroy();
}
