package com.tomaszstankowski.trainingapplication.photo_capture;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoCaptureInteractorImpl implements PhotoCaptureInteractor {
    @Inject
    DataBaseAccessor mDataAccessor;
    @Inject
    StorageAccessor mResourceAccessor;

    @Inject
    PhotoCaptureInteractorImpl() {
    }

    private DatabaseReference mRef;
    private ValueEventListener mListener;

    @Override
    public void addListenerForLastPhotoChanges(OnLastPhotoChangeListener listener, String userKey) {
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getValue(String.class);
                if (key != null) {
                    mDataAccessor.getPhotoOnce(key, new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Photo photo = dataSnapshot.getValue(Photo.class);
                            if (photo != null) {
                                photo.key = dataSnapshot.getKey();
                                mResourceAccessor.getImageUri(photo)
                                        .addOnSuccessListener(uri -> listener.onLastPhotoChanged(photo, uri))
                                        .addOnFailureListener(e -> listener.onLastPhotoFetchError());
                            } else
                                listener.onLastPhotoFetchError();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onLastPhotoFetchError();
                        }
                    });
                } else
                    listener.onLastPhotoNull();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLastPhotoFetchError();
            }
        };
        mRef = mDataAccessor.getUserLastPhoto(mListener, userKey);
    }

    @Override
    public void removeListenerForLastPhotoChanges() {
        if (mRef != null)
            mRef.removeEventListener(mListener);
    }

}
