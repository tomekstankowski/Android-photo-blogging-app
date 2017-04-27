package com.tomaszstankowski.trainingapplication.model;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Class accessing photos database.
 */

public class PhotoAccessor extends DataBaseAccessor {

    public Task<Void> savePhoto(Photo photo){
        return setValue("photos/all/" + photo.getKey(), photo);
    }

    public Task<Void> setLastPhoto(Photo photo){
        return setValue("photos/last_photo", photo.getKey());
    }

    public void getPhoto(String key, ValueEventListener listener){
        getValue("photos/all/" + key, listener);
    }

    public void getLastPhoto(ValueEventListener listener){
        ValueEventListener keyFetchListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getValue(String.class);
                if(key != null) {
                    getPhoto(key, listener);
                }
                else
                    listener.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        };
        getValue("photos/last_photo", keyFetchListener);
    }
}
