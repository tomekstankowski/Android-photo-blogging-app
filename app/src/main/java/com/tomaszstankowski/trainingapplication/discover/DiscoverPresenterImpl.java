package com.tomaszstankowski.trainingapplication.discover;


import android.net.Uri;

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

public class DiscoverPresenterImpl implements DiscoverPresenter,
        DiscoverInteractor.OnRecentPhotosChangeListener {

    private DiscoverView mView;
    private DiscoverInteractor mInteractor;
    private List<Photo> mPhotos = new ArrayList<>();
    private Map<String, Uri> mImages = new HashMap<>();

    @Inject
    DiscoverPresenterImpl(DiscoverInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onCreateView(DiscoverView view) {
        mView = view;
        mInteractor.observeRecentPhotos(this);
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mInteractor.stopObservingRecentPhotos();
        mPhotos.clear();
        mImages.clear();
    }

    @Override
    public void onPhotoClicked(int pos) {
        Photo photo = mPhotos.get(pos);
        Uri image = mImages.get(photo.key);
        mView.startPhotoDetailsView();
        EventBus.getDefault().postSticky(
                new PhotoTransferEvent(photo, image, Config.RC_PHOTO_DETAILS)
        );
    }

    @Override
    public void onPhotoAdded(Photo photo, Uri image) {
        if (!mImages.containsKey(photo.key)) {
            mPhotos.add(photo);
            mImages.put(photo.key, image);
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
                mImages.remove(key);
                position = mPhotos.indexOf(photo);
                if (mView != null)
                    mView.removePhoto(position);
                iterator.remove();
                break;
            }
        }
    }
}
