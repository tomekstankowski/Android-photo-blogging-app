package com.tomaszstankowski.trainingapplication.view;

import android.content.Context;

import com.tomaszstankowski.trainingapplication.presenter.Presenter;

/**
 * Basic MVP View interface.
 */

public interface View {
    Presenter getPresenter();

    void showMessage(String message);

    Context getContext();
}
