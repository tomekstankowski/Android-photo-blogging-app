package com.tomaszstankowski.trainingapplication.discover;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.Map;

interface DiscoverView {

    void addPhoto(StorageReference image);

    void removePhoto(int pos);

    void startPhotoDetailsView(Map<String, Serializable> args);
}
