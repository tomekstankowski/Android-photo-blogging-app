package com.tomaszstankowski.trainingapplication.photo_save;

import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.PhotoAccessor;

public class PhotoInteractorImpl implements PhotoInteractor {
    private PhotoAccessor mAccessor;

    public PhotoInteractorImpl(){
        mAccessor = new PhotoAccessor();
    }

    @Override
    public void savePhoto(Photo photo, OnPhotoSaveListener listener){
        mAccessor.savePhoto(photo)
                .addOnSuccessListener(aVoid -> mAccessor.setLastPhoto(photo)
                        .addOnSuccessListener(aVoid1 -> listener.onSuccess())
                        .addOnFailureListener(r -> listener.onError())
                )
                .addOnFailureListener(r -> listener.onError());
    }
}
