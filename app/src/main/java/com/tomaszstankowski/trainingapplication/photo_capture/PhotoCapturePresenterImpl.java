package com.tomaszstankowski.trainingapplication.photo_capture;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.service.PhotoService;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class PhotoCapturePresenterImpl implements PhotoCapturePresenter {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PHOTO_SAVE = 2;
    private static final String PHOTO_URI = "PHOTO_URI";
    private static final String PHOTO_KEY = "PHOTO_KEY";
    private File mPhotoFile;
    private PhotoCaptureView mView;
    private PhotoInteractor mInteractor;

    public PhotoCapturePresenterImpl(PhotoCaptureView view){
        mView = view;
        mInteractor = new PhotoInteractorImpl();
    }


    @Override
    public void onDestroyView(){
        mView = null;
    }

    @Override
    public void onViewUpdateRequest(){
            mView.showProgressBar();
            mInteractor.getLastPhoto(new PhotoInteractor.OnLastPhotoFetchListener() {
                @Override
                public void onSuccess(Photo photo) {
                    if(mView != null) {
                        mView.updateView(photo.getTitle(), Uri.parse(photo.getUri()));
                        mView.hideProgressBar();
                    }
                }

                @Override
                public void onError() {
                    if(mView != null)
                        mView.hideProgressBar();
                }
            });
    }

    /**
     * Called when user wants to capture photo.
     */
    @Override
    public void onCaptureButtonClicked(Context context){
        mPhotoFile = null;
        try {
            mPhotoFile = PhotoService.createPhotoFile(context);
        }catch (IOException e){
            e.printStackTrace();
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (mPhotoFile != null
                && takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            Uri photoUri = PhotoService.getUriFromFile(context, mPhotoFile);
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
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            onPhotoCapture(context);
        }
        if(requestCode == REQUEST_PHOTO_SAVE){
            if(resultCode == RESULT_OK) {
                onPhotoSave();
            }
        }
    }

    private void onPhotoCapture(Context context){
        PhotoService.addPhotoToGallery(context, mPhotoFile);
        Intent photoSaveIntent = new Intent(context, PhotoSaveActivity.class);
        Uri photoUri = PhotoService.getUriFromFile(context, mPhotoFile);
        photoSaveIntent.putExtra(PHOTO_URI, photoUri);
        mView.startActivityForResult(photoSaveIntent,REQUEST_PHOTO_SAVE);
    }

    private void onPhotoSave(){
        onViewUpdateRequest();
    }
}
