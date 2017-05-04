package com.tomaszstankowski.trainingapplication.user_photos;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;


public class UserPhotosInteractorImpl implements UserPhotosInteractor {
    private DataBaseAccessor mDataAccessor;
    private StorageAccessor mResourceAccessor;
    private DatabaseReference mRef;
    private ChildEventListener mListener;

    public UserPhotosInteractorImpl() {
        mDataAccessor = new DataBaseAccessor();
        mResourceAccessor = new StorageAccessor();
    }

    @Override
    public void addListenerForUserPhotosChanges(OnUserPhotosChangesListener listener) {
        mListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                mDataAccessor.getPhotoOnce(key, new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Photo photo = dataSnapshot.getValue(Photo.class);
                        if (photo != null) {
                            photo.key = key;
                            mResourceAccessor.getImageUri(photo)
                                    .addOnSuccessListener(uri -> listener.onPhotoAdded(photo, uri));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                listener.onPhotoRemoved(key);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mRef = mDataAccessor.getUserPhotos(mListener, "admin");
    }

    @Override
    public void removeListenerForUserPhotosChanges() {
        if (mRef != null)
            mRef.removeEventListener(mListener);
    }
}
