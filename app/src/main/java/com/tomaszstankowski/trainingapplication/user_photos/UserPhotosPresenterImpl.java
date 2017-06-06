package com.tomaszstankowski.trainingapplication.user_photos;


import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserPhotosPresenterImpl implements UserPhotosPresenter, UserPhotosInteractor.OnUserPhotosChangesListener {
    @Inject
    UserPhotosInteractor mInteractor;

    @Inject
    UserPhotosPresenterImpl() {
    }

    private static final String PHOTO = "PHOTO";
    private static final String IMAGE_URI = "IMAGE_URI";

    private UserPhotosView mView;
    private List<Photo> mPhotos = new ArrayList<>();
    private Map<String, Uri> mImages = new HashMap<>();

    @Override
    public void onCreateView(UserPhotosView view) {
        mView = view;
        mInteractor.addListenerForUserPhotosChanges(this);
        for (Photo p : mPhotos) {
            mView.addPhoto(mImages.get(p.key));
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mInteractor.removeListenerForUserPhotosChanges();
    }

    @Override
    public void onPhotoClicked(int position) {
        Photo clicked = mPhotos.get(position);
        Uri image = mImages.get(clicked.key);
        Intent intent = new Intent(mView.getContext(), PhotoDetailsActivity.class);
        intent.putExtra(PHOTO, (Parcelable) clicked);
        intent.putExtra(IMAGE_URI, image);
        mView.startActivity(intent);
    }

    @Override
    public void onPhotoAdded(Photo photo, Uri image) {
        //when activity was destroyed and now is recreated, there might be this photo saved
        if (!mImages.containsKey(photo.key)) {
            mPhotos.add(photo);
            mImages.put(photo.key, image);
            if (mView != null)
                mView.addPhoto(image);
        }
    }

    @Override
    public void onPhotoRemoved(String key) {
        int position;
        Iterator<Photo> iterator = mPhotos.iterator();
        while (iterator.hasNext()) {
            Photo photo = iterator.next();
            if (photo.key.equals(key)) {
                position = mPhotos.indexOf(photo);
                if (mView != null)
                    mView.removePhoto(position);
                iterator.remove();
                break;
            }
        }
    }
}
