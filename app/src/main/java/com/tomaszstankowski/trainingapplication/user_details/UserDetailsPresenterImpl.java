package com.tomaszstankowski.trainingapplication.user_details;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDetailsPresenterImpl implements UserDetailsPresenter, UserDetailsInteractor.OnUserPhotosChangesListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private UserDetailsView mView;
    private UserDetailsInteractor mInteractor;
    private List<Photo> mPhotos = new ArrayList<>();
    private Map<String, StorageReference> mImages = new HashMap<>();

    @Inject
    UserDetailsPresenterImpl(UserDetailsInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onCreateView(UserDetailsView view) {
        mView = view;
        Serializable mode = mView.getArg(Config.USER_DETAILS_MODE);
        if (mode != null && mode instanceof Integer) {
            if (mode.equals(Config.USER_DETAILS_MODE_CURRENT)) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    mInteractor.observeUserPhotos(firebaseUser.getUid(), this);
                    mView.updateUsername(firebaseUser.getDisplayName());
                }
            } else if (mode.equals(Config.USER_DETAILS_MODE_DEFAULT)) {
                Serializable arg = mView.getArg(Config.ARG_USER);
                if (arg != null && arg instanceof User) {
                    User user = (User) arg;
                    mInteractor.observeUserPhotos(user.key, this);
                    mView.updateUsername(user.name);
                }
            }
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
        Map<String, Serializable> args = new HashMap<>();
        args.put(Config.ARG_PHOTO, photo);
        mView.startPhotoDetailsView(args);
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
