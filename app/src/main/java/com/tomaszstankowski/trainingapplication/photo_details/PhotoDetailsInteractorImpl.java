package com.tomaszstankowski.trainingapplication.photo_details;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;

public class PhotoDetailsInteractorImpl implements PhotoDetailsInteractor {
    private DataBaseAccessor mDataAccessor;
    private StorageAccessor mResourceAccessor;
    private DatabaseReference mRef;
    private ValueEventListener mListener;

    public PhotoDetailsInteractorImpl() {
        mDataAccessor = new DataBaseAccessor();
        mResourceAccessor = new StorageAccessor();
    }

    @Override
    public void addListenerForPhotoChanges(String key, OnPhotoChangeListener listener) {
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                if (photo != null) {
                    photo.key = dataSnapshot.getKey();
                    mResourceAccessor.getImageUri(photo)
                            .addOnSuccessListener(uri -> listener.onPhotoChange(photo, uri))
                            .addOnFailureListener(e -> listener.onPhotoFetchError());
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
}
