package com.tomaszstankowski.trainingapplication.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.model.Model;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.service.PhotoService;
import com.tomaszstankowski.trainingapplication.view.PhotoCaptureView;
import com.tomaszstankowski.trainingapplication.view.PhotoSaveActivity;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Presenter responding to PhotoCaptureFragment calls
 */

public class PhotoCapturePresenterImpl implements PhotoCapturePresenter {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PHOTO_SAVE = 2;
    private static final String PHOTO_URI = "PHOTO_URI";
    private static final String PHOTO_KEY = "PHOTO_KEY";
    private File mPhotoFile;
    private Photo mLastPhoto;
    private PhotoCaptureView mView;
    private Model mModel;

    @Override
    public void setView(com.tomaszstankowski.trainingapplication.view.View view){
        mView = (PhotoCaptureView) view;
    }

    @Override
    public void setModel(Model model){
        mModel = model;
    }

    @Override
    public void onDestroyView(){
        mView = null;
    }

    @Override
    public void updateContent(TextView label, TextView title, SimpleDraweeView image){
        if(mLastPhoto == null) {
            mView.showProgressBar();
            mModel.getLastPhoto(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Object val = dataSnapshot.getValue();
                    if(val!= null) {
                        mLastPhoto = (Photo) val;
                        setContent(label, title, image);
                    }
                    if (mView != null)
                        mView.hideProgressBar();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mView.hideProgressBar();
                }
            });
        }
        else {
            setContent(label, title, image);
        }
    }

    private void setContent(TextView label, TextView title, SimpleDraweeView image){
        if(mView != null) {
            label.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            title.setText(mLastPhoto.getTitle());
            image.setVisibility(View.VISIBLE);
            image.setImageURI(mLastPhoto.uri);
        }
    }

    /**
     * Called when user wants to capture photo.
     */
    @Override
    public void onCaptureButtonClicked(){
        Context context = mView.getContext();
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
            mView.showMessage(context.getString(R.string.error));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //the photo has just been captured
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            onPhotoCapture();
        }
        if(requestCode == REQUEST_PHOTO_SAVE){
            if(resultCode == RESULT_OK) {
                Photo photo = (Photo) data.getSerializableExtra(PHOTO_KEY);
                onPhotoSave(photo);
            }
            else
                mView.showMessage(mView.getContext().getString(R.string.save_error));
        }
    }

    /**
     * Called in onActivityResult() if the result code was ok
     */
    private void onPhotoCapture(){
        Context context = mView.getContext();
        PhotoService.addPhotoToGallery(context, mPhotoFile);
        Intent photoSaveIntent = new Intent(context, PhotoSaveActivity.class);
        Uri photoUri = PhotoService.getUriFromFile(context, mPhotoFile);
        photoSaveIntent.putExtra(PHOTO_URI, photoUri);
        mView.startActivityForResult(photoSaveIntent,REQUEST_PHOTO_SAVE);
    }

    private void onPhotoSave(Photo photo){
        Task<Void> task = mModel.setLastPhoto(photo);
        task.addOnSuccessListener(aVoid -> {
            mLastPhoto = photo;
            if(mView != null)
                mView.notifyDataChanged();
        });
    }
}
