package com.tomaszstankowski.trainingapplication.settings;


import com.tomaszstankowski.trainingapplication.model.User;

public interface SettingsInteractor {
    interface OnUserProfileUpdateListener {
        void onUserProfileUpdateSuccess();

        void onUserProfileUpdateFailure();
    }

    void updateUserProfile(User user, OnUserProfileUpdateListener listener);
}
