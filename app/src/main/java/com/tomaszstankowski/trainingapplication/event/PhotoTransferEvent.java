package com.tomaszstankowski.trainingapplication.event;

import com.tomaszstankowski.trainingapplication.model.Photo;

public class PhotoTransferEvent {
    public final Photo photo;
    public final int requestCode;

    public PhotoTransferEvent(Photo photo, int requestCode) {
        this.photo = photo;
        this.requestCode = requestCode;
    }
}
