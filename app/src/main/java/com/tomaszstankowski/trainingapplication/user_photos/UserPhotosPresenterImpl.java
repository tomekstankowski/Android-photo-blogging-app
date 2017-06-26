package com.tomaszstankowski.trainingapplication.user_photos;


import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserPhotosPresenterImpl implements UserPhotosPresenter, UserPhotosInteractor.OnUserPhotosChangesListener {
    private static final String PHOTO = "PHOTO";
    private static final String IMAGE_URI = "IMAGE_URI";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private UserPhotosView mView;
    private UserPhotosInteractor mInteractor;
    private List<Photo> mPhotos = new ArrayList<>();
    private Map<String, Uri> mImages = new HashMap<>();

    @Inject
    UserPhotosPresenterImpl(UserPhotosInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onCreateView(UserPhotosView view) {
        mView = view;
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            mInteractor.observeUserPhotos(firebaseUser.getUid(), this);
            mView.updateUserView(firebaseUser.getDisplayName());
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mInteractor.stopObservingUserPhotos();
        mImages.clear();
        mPhotos.clear();
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
        if (mImages.containsKey(photo.key))
            return;
        mPhotos.add(photo);
        mImages.put(photo.key, image);
        Collections.sort(mPhotos, (p1, p2) -> p1.date.compareTo(p2.date));
        if (mView != null) {
            mView.removeAllPhotos();
            for (Photo p : mPhotos)
                mView.addPhoto(mImages.get(p.key));
        }
    }

    @Override
    public void onPhotoRemoved(String photoKey) {
        int position;
        Iterator<Photo> iterator = mPhotos.iterator();
        while (iterator.hasNext()) {
            Photo photo = iterator.next();
            if (photo.key.equals(photoKey)) {
                mImages.remove(photoKey);
                position = mPhotos.indexOf(photo);
                if (mView != null)
                    mView.removePhoto(position);
                iterator.remove();
                break;
            }
        }
    }
}
