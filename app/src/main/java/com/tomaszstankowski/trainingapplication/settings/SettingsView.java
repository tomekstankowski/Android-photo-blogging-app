package com.tomaszstankowski.trainingapplication.settings;

import android.support.v4.app.FragmentActivity;

public interface SettingsView {
    enum Message {
        SUCCESS,
        ERROR,
        UNCHANGED
    }

    void update(String username);

    void showMessage(Message message);

    void showProgressbar();

    void hideProgressbar();

    FragmentActivity getActivity();
}
