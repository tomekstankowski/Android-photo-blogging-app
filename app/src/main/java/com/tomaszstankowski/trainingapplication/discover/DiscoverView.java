package com.tomaszstankowski.trainingapplication.discover;

import com.google.firebase.storage.StorageReference;

interface DiscoverView {

    void addPhoto(StorageReference image);

    void removePhoto(int pos);

    void startPhotoDetailsView();
}
