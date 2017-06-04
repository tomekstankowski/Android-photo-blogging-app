package com.tomaszstankowski.trainingapplication.photo_details;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;

public class PhotoDetailsInteractorImpl implements PhotoDetailsInteractor {
    private DataBaseAccessor mDataAccessor = new DataBaseAccessor();
    private DatabaseReference mRef;
    private ValueEventListener mListener;

    @Override
    public void addListenerForPhotoChanges(String key, OnPhotoChangeListener listener) {
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                if (photo != null) {
                    photo.key = key;
                    listener.onPhotoChange(photo);
                } else
                    listener.onPhotoFetchError();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onPhotoFetchError();
            }
        };
        mRef = mDataAccessor.getPhoto(key, mListener);
    }

    @Override
    public void removeListenerForPhotoChanges() {
        if (mRef != null)
            mRef.removeEventListener(mListener);
    }

    @Override
    public void removePhoto(Photo photo, OnPhotoRemoveListener listener) {
        mDataAccessor.removePhoto(photo).addOnSuccessListener(aVoid -> listener.onPhotoRemoveSuccess())
                .addOnFailureListener(e -> listener.onPhotoRemoveFailure());
    }
}
