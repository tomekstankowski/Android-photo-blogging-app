package com.tomaszstankowski.trainingapplication.settings;


interface SettingsView {
    enum Message {
        SUCCESS,
        ERROR,
        UNCHANGED
    }

    void startLogOutUI();

    void startLoginView(int mode);

    void update(String username);

    void showMessage(Message message);

    void showProgressbar();

    void hideProgressbar();
}
