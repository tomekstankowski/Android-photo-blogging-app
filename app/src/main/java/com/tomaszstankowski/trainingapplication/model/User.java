package com.tomaszstankowski.trainingapplication.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class instances holding details of the users of the app.
 */
@IgnoreExtraProperties
public class User implements Serializable {
    @Exclude
    public String key;
    public String userName;
    public Map<String, Boolean> photos = new HashMap<>();

    public User(@NonNull String userName) {
        this.userName = userName;
    }

    @Exclude
    public void attachPhoto(Photo photo) {
        photos.put(photo.key, true);
    }
}
