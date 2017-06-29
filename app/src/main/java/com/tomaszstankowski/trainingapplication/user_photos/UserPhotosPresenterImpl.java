package com.tomaszstankowski.trainingapplication.user_photos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.event.PhotoTransferEvent;
import com.tomaszstankowski.trainingapplication.model.Photo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserPhotosPresenterImpl implements UserPhotosPresenter, UserPhotosInteractor.OnUserPhotosChangesListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private UserPhotosView mView;
    private UserPhotosInteractor mInteractor;
    private List<Photo> mPhotos = new ArrayList<>();
    private Map<String, StorageReference> mImages = new HashMap<>();

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
            mView.updateUsername(firebaseUser.getDisplayName());
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
        Photo photo = mPhotos.get(position);
        mView.startPhotoDetailsView();
        EventBus.getDefault().postSticky(
                new PhotoTransferEvent(photo, Config.RC_PHOTO_DETAILS)
        );
    }

    @Override
    public void onPhotoAdded(Photo photo, StorageReference image) {
        if (mImages.containsKey(photo.key))
            return;
        mPhotos.add(photo);
        mImages.put(photo.key, image);
        mView.addPhoto(image);
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
