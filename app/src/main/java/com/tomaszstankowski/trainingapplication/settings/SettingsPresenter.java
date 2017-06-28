package com.tomaszstankowski.trainingapplication.settings;


public interface SettingsPresenter {

    void onCreateView(SettingsView view);

    void onDestroyView();

    void onLogOutButtonClicked();

    void onLogOutCompleted();

    void onSaveButtonClicked(String username);
}
