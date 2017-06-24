package com.tomaszstankowski.trainingapplication.model;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Class accessing Firebase Database.
 */
public class DataBaseAccessor {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Inject
    public DataBaseAccessor() {
    }

    public Task<Void> savePhoto(Photo photo) {
        String key = mDatabase.getReference("photos").push().getKey();
        photo.key = key;
        Map<String, Object> data = new HashMap<>();
        data.put("users/" + photo.userKey + "/photos/" + key, true);
        data.put("photos/" + key, photo);
        return mDatabase.getReference().updateChildren(data);
    }

    public Task<Void> removePhoto(Photo photo) {
        Map<String, Object> data = new HashMap<>();
        data.put("users/" + photo.userKey + "/photos/" + photo.key, null);
        data.put("photos/" + photo.key, null);
        return mDatabase.getReference().updateChildren(data);
    }

    public Task<Void> editPhoto(Photo photo) {
        return mDatabase.getReference("photos").child(photo.key).setValue(photo);
    }

    public DatabaseReference getPhoto(String key) {
        return mDatabase.getReference("photos").child(key);
    }

    public Query getUserLastPhoto(String userKey) {
        return mDatabase.getReference("users").child(userKey).child("photos").orderByKey().limitToLast(1);
    }

    public Query getUserPhotos(String userKey) {
        return mDatabase.getReference("users").child(userKey).child("photos").orderByKey();
    }
}
