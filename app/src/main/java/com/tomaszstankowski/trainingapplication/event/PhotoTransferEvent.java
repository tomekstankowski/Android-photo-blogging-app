package com.tomaszstankowski.trainingapplication.event;

import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;

public class PhotoTransferEvent {
    public final Photo photo;
    public final Uri image;
    public final int requestCode;

    public PhotoTransferEvent(Photo photo, Uri image, int requestCode) {
        this.photo = photo;
        this.image = image;
        this.requestCode = requestCode;
    }
}
