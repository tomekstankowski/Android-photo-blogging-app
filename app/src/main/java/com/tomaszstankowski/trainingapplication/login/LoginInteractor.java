package com.tomaszstankowski.trainingapplication.login;


import android.support.annotation.Nullable;

import com.tomaszstankowski.trainingapplication.model.User;

public interface LoginInteractor {
    interface OnUserFetchListener {
        void onUserFetchSuccess(@Nullable User user);

        void onUserFetchFailure();
    }

    interface OnUserSaveListener {
        void onUserSaveSuccess();

        void onUserSaveFailure();
    }

    void getUser(String key, OnUserFetchListener listener);

    void saveUser(User user, OnUserSaveListener listener);
}
