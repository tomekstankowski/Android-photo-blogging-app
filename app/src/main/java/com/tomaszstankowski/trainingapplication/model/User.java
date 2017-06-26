package com.tomaszstankowski.trainingapplication.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    public String name;
    public String email;
    public Map<String, Boolean> photos = new HashMap<>();

    public User() {
    }

    public User(@NonNull String name, @NonNull String key, @Nullable String email) {
        this.name = name;
        this.key = key;
        this.email = email;
    }
}
