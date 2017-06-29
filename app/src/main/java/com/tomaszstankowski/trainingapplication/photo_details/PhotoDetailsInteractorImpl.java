package com.tomaszstankowski.trainingapplication.photo_details;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;
import com.tomaszstankowski.trainingapplication.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoDetailsInteractorImpl implements PhotoDetailsInteractor, ValueEventListener {
    private DataBaseAccessor mDataAccessor;
    private StorageAccessor mStorageAccessor;

    @Inject
    PhotoDetailsInteractorImpl(DataBaseAccessor dataBaseAccessor, StorageAccessor storageAccessor) {
        mDataAccessor = dataBaseAccessor;
        mStorageAccessor = storageAccessor;
    }

    private DatabaseReference mPhotoRef;
    private OnPhotoChangeListener mListener;

    @Override
    public void observePhoto(String key, OnPhotoChangeListener listener) {
        mListener = listener;
        mPhotoRef = mDataAccessor.getPhoto(key);
        mPhotoRef.addValueEventListener(this);
    }

    @Override
    public void stopObservingPhoto() {
        if (mPhotoRef != null)
            mPhotoRef.removeEventListener(this);
    }

    @Override
    public void removePhoto(Photo photo, OnPhotoRemoveListener listener) {
        mDataAccessor.removePhoto(photo)
                .addOnSuccessListener(aVoid -> mStorageAccessor.removeImage(photo.key)
                        .addOnSuccessListener(aVoid1 -> listener.onPhotoRemoveSuccess())
                        .addOnFailureListener(e -> listener.onPhotoRemoveFailure())
                )
                .addOnFailureListener(e -> listener.onPhotoRemoveFailure());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Photo photo = dataSnapshot.getValue(Photo.class);
        if (photo != null) {
            photo.key = dataSnapshot.getKey();
            mListener.onPhotoChange(photo);
        } else
            mListener.onPhotoFetchError();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        mListener.onPhotoFetchError();
    }

    @Override
    public void getUser(String key, OnUserFetchListener listener) {
        mDataAccessor.getUser(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    user.key = dataSnapshot.getKey();
                    listener.onUserFetchSuccess(user);
                } else {
                    listener.onUserFetchFailure();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onUserFetchFailure();
            }
        });
    }

    @Override
    public StorageReference getImage(Photo photo) {
        return mStorageAccessor.getImage(photo.key);
    }
}
