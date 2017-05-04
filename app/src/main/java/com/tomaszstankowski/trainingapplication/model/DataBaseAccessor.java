package com.tomaszstankowski.trainingapplication.model;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Class accessing Firebase Database.
 */

public class DataBaseAccessor {
    private FirebaseDatabase mDatabase;

    public DataBaseAccessor(){
        mDatabase = FirebaseDatabase.getInstance();
    }

    public Task<Void> savePhoto(Photo photo) {
        String key = mDatabase.getReference("photos").push().getKey();
        photo.key = key;
        Map<String, Object> changedData = new HashMap<>();
        changedData.put("users/" + photo.userKey + "/photos/" + key, true);
        changedData.put("users/" + photo.userKey + "/lastPhoto", key);
        changedData.put("photos/" + key, photo);
        return mDatabase.getReference().updateChildren(changedData);
    }

    public Task<Void> editPhoto(Photo photo) {
        return mDatabase.getReference("photos").child(photo.key).setValue(photo);
    }

    public void getPhotoOnce(String key, ValueEventListener listener) {
        mDatabase.getReference("photos").child(key).addListenerForSingleValueEvent(listener);
    }

    public DatabaseReference getPhoto(String key, ValueEventListener listener) {
        DatabaseReference ref = mDatabase.getReference("photos").child(key);
        ref.addValueEventListener(listener);
        return ref;
    }

    public DatabaseReference getUserLastPhoto(ValueEventListener listener, String userKey) {
        DatabaseReference ref = mDatabase.getReference("users").child(userKey).child("lastPhoto");
        ref.addValueEventListener(listener);
        return ref;
    }

    public DatabaseReference getUserPhotos(ChildEventListener listener, String userKey) {
        DatabaseReference ref = mDatabase.getReference("users").child(userKey).child("photos");
        ref.addChildEventListener(listener);
        return ref;
    }
}
