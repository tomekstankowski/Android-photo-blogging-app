package com.tomaszstankowski.trainingapplication.discover;

import android.net.Uri;

public interface DiscoverView {

    void addPhoto(Uri image);

    void removePhoto(int pos);

    void startPhotoDetailsView();
}
