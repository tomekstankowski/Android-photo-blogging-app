package com.tomaszstankowski.trainingapplication.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

/**
 * Class accessing Firebase Storage.
 */

public class StorageAccessor {
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    public Task<Uri> getImageUri(Photo photo) {
        return mStorage.getReference("images").child(photo.key).getDownloadUrl();
    }

    public UploadTask saveImage(Photo photo, Uri uri) {
        return mStorage.getReference("images").child(photo.key).putFile(uri);
    }
}
