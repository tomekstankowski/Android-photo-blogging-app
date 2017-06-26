package com.tomaszstankowski.trainingapplication.settings;


import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.User;

import javax.inject.Inject;

public class SettingsInteractorImpl implements SettingsInteractor {
    private DataBaseAccessor mDataAccessor;

    @Inject
    public SettingsInteractorImpl(DataBaseAccessor dataBaseAccessor) {
        mDataAccessor = dataBaseAccessor;
    }

    @Override
    public void updateUserProfile(User user, OnUserProfileUpdateListener listener) {
        mDataAccessor.editUserProfile(user)
                .addOnSuccessListener(aVoid -> listener.onUserProfileUpdateSuccess())
                .addOnFailureListener(e -> listener.onUserProfileUpdateFailure());
    }
}
