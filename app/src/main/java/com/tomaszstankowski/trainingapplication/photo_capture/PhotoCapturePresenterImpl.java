package com.tomaszstankowski.trainingapplication.photo_capture;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;
import com.tomaszstankowski.trainingapplication.util.ImageManager;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.app.Activity.RESULT_OK;

@Singleton
public class PhotoCapturePresenterImpl implements PhotoCapturePresenter, PhotoCaptureInteractor.OnLastPhotoChangeListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PHOTO_SAVE = 2;
    private static final String TEMP_IMAGE_PATH = "TEMP_IMAGE_PATH";
    private static final String PHOTO = "PHOTO";
    private static final String IMAGE_URI = "IMAGE_URI";

    private ImageManager mManager;
    private PhotoCaptureInteractor mInteractor;
    private Photo mPhoto;
    private Uri mImage;
    private File mTempImageFile;
    private PhotoCaptureView mView;

    @Inject
    PhotoCapturePresenterImpl(PhotoCaptureInteractor interactor, ImageManager manager) {
        mInteractor = interactor;
        mManager = manager;
    }

    @Override
    public void onDestroyView(){
        mView = null;
        mInteractor.stopObservingUserLastPhoto();
    }

    @Override
    public void onCreateView(PhotoCaptureView view) {
        mView = view;
        mInteractor.observeUserLastPhoto(this);
    }

    @Override
    public void onLastPhotoChanged(Photo photo, Uri image) {
        mPhoto = photo;
        mImage = image;
        if (mView != null) {
            mView.updateView(image);
        }
    }

    @Override
    public void onLastPhotoRemoved() {
        mPhoto = null;
        mImage = null;
        if (mView != null) {
            mView.clearView();
        }
    }
    @Override
    public void onLastPhotoFetchError() {
        if (mView != null) {
            mView.showMessage(mView.getContext().getString(R.string.load_error));
        }
    }

    @Override
    public void onCaptureButtonClicked() {
        mTempImageFile = null;
        Context context = mView.getContext();
        try {
            mTempImageFile = mManager.createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (mTempImageFile != null
                && takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            Uri photoUri = mManager.getImageUriFromFile(mTempImageFile);
            if(photoUri != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                mView.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                return;
            }
        }
        if(mView != null)
            mView.showMessage(context.getString(R.string.capture_error));
    }

    @Override
    public void onImageClicked() {
        if (mPhoto != null) {
            Intent intent = new Intent(mView.getContext(), PhotoDetailsActivity.class);
            intent.putExtra(PHOTO, (Parcelable) mPhoto);
            intent.putExtra(IMAGE_URI, mImage);
            mView.startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            onPhotoCaptured(mView.getContext());
        }
        if (requestCode == REQUEST_PHOTO_SAVE)
            //was temporary removed just before starting PhotoSaveActivity
            mInteractor.observeUserLastPhoto(this);
    }

    private void onPhotoCaptured(Context context) {
        mManager.addImageToSystemGallery(mTempImageFile);
        Intent photoSaveIntent = new Intent(context, PhotoSaveActivity.class);
        photoSaveIntent.putExtra(TEMP_IMAGE_PATH, mTempImageFile.getAbsolutePath());
        mView.startActivityForResult(photoSaveIntent, REQUEST_PHOTO_SAVE);
        //otherwise listener is trying to access image in storage before it's fully uploaded
        //we can't atomically save the photo in database and save image in storage
        mInteractor.stopObservingUserLastPhoto();
    }
}
