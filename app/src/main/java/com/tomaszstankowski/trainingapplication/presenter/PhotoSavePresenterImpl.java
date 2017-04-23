package com.tomaszstankowski.trainingapplication.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.tomaszstankowski.trainingapplication.model.Model;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.view.PhotoSaveView;
import com.tomaszstankowski.trainingapplication.view.View;

/**
 * Presenter responding to PhotoSaveActivity calls
 */

public class PhotoSavePresenterImpl implements PhotoSavePresenter{
    private static final String PHOTO_URI = "PHOTO_URI";
    private static final String PHOTO_KEY = "PHOTO_KEY";

    private PhotoSaveView mView;
    private Model mModel;

    @Override
    public void setView(View view){
        mView = (PhotoSaveView)view;
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
    public Uri getImageUri(){
        return mView.getIntent().getParcelableExtra(PHOTO_URI);
    }

    @Override
    public void onSaveButtonClicked(String title, String desc){
        Uri uri = mView.getIntent().getParcelableExtra(PHOTO_URI);
        Activity activity = mView.getActivity();
        Photo photo = new Photo(title,"admin",desc,uri);
        Task<Void> saveTask = mModel.savePhoto(photo);
        saveTask.addOnSuccessListener(aVoid ->{
            //calling activity can read the data
            Intent data = new Intent();
            data.putExtra(PHOTO_KEY, photo);
            if (activity.getParent() == null) {
                activity.setResult(Activity.RESULT_OK, data);
            } else {
                activity.getParent().setResult(Activity.RESULT_OK, data);
            }
            activity.finish();
        } );
        saveTask.addOnFailureListener(r->{
            //calling activity can print error message
            if (activity.getParent() == null) {
                activity.setResult(Activity.RESULT_CANCELED);
            } else {
                activity.getParent().setResult(Activity.RESULT_CANCELED);
            }
            activity.finish();
        } );
        mView.showProgressBar();
    }

    @Override
    public void onBackButtonClicked(){
        mView.getActivity().finish();
    }
}
