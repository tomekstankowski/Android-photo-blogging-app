package com.tomaszstankowski.trainingapplication.photo_capture;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.PhotoAccessor;


public class PhotoInteractorImpl implements PhotoInteractor {
    private PhotoAccessor mAccessor;

    public PhotoInteractorImpl(){
        mAccessor = new PhotoAccessor();
    }

    @Override
    public void getLastPhoto(OnLastPhotoFetchListener listener){
        mAccessor.getLastPhoto(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                if(photo != null)
                    listener.onSuccess(photo);
                else
                    listener.onError();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError();
            }
        });
    }

}
