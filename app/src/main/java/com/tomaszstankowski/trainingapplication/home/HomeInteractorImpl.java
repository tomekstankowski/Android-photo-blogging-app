package com.tomaszstankowski.trainingapplication.home;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HomeInteractorImpl implements HomeInteractor, ChildEventListener {
    private DataBaseAccessor mDataAccessor;
    private StorageAccessor mStorageAccessor;
    private Query mQuery;
    private OnLastPhotoChangeListener mListener;

    @Inject
    HomeInteractorImpl(DataBaseAccessor dataBaseAccessor, StorageAccessor storageAccessor) {
        mDataAccessor = dataBaseAccessor;
        mStorageAccessor = storageAccessor;
    }

    @Override
    public void observeUserLastPhoto(String userKey, OnLastPhotoChangeListener listener) {
        mListener = listener;
        mQuery = mDataAccessor.getUserLastPhoto(userKey);
        mQuery.addChildEventListener(this);
    }

    @Override
    public void stopObservingUserLastPhoto() {
        if (mQuery != null)
            mQuery.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        if (key != null) {
            mDataAccessor.getPhoto(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Photo photo = dataSnapshot.getValue(Photo.class);
                    if (photo != null) {
                        photo.key = dataSnapshot.getKey();
                        mStorageAccessor.getImageUri(photo)
                                .addOnSuccessListener(uri -> mListener.onLastPhotoChanged(photo, uri))
                                .addOnFailureListener(e -> mListener.onLastPhotoFetchError());
                    } else {
                        mListener.onLastPhotoFetchError();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mListener.onLastPhotoFetchError();
                }
            });
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //child is just String to Boolean pair, so we don't need to worry about value change
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        mListener.onLastPhotoRemoved();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

}
