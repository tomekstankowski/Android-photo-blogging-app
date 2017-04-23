package com.tomaszstankowski.trainingapplication.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tomek on 4/18/2017.
 */

public interface Model {
    Task<Void> savePhoto(Photo photo);

    Task<Void> setLastPhoto(Photo photo);

    void getPhoto(String key, ValueEventListener listener);

    void getLastPhoto(ValueEventListener listener);
}
