package com.tomaszstankowski.trainingapplication.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

import javax.inject.Inject;

/**
 * Class accessing Firebase Storage.
 */

public class StorageAccessor {
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    @Inject
    public StorageAccessor() {
    }

    public StorageReference getImage(String name) {
        return mStorage.getReference("images").child(name);
    }

    public UploadTask saveImage(String name, InputStream inputStream) {
        return mStorage.getReference("images").child(name).putStream(inputStream);
    }

    public Task<Void> removeImage(String name) {
        return mStorage.getReference("images").child(name).delete();
    }
}
