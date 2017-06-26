package com.tomaszstankowski.trainingapplication.login;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.User;

import javax.inject.Inject;

public class LoginInteractorImpl implements LoginInteractor {
    private DataBaseAccessor mDataAccessor;

    @Inject
    public LoginInteractorImpl(DataBaseAccessor dataBaseAccessor) {
        mDataAccessor = dataBaseAccessor;
    }

    @Override
    public void getUser(String key, OnUserFetchListener listener) {
        mDataAccessor.getUser(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //user might be null, so we don't set it's key
                listener.onUserFetchSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onUserFetchFailure();
            }
        });
    }

    @Override
    public void saveUser(User user, OnUserSaveListener listener) {
        mDataAccessor.saveUser(user)
                .addOnSuccessListener(aVoid -> listener.onUserSaveSuccess())
                .addOnFailureListener(e -> listener.onUserSaveFailure());
    }
}
