package com.tomaszstankowski.trainingapplication.photo_save;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.model.Photo;


/**
 * Presenter responding to PhotoSaveActivity calls
 */

public class PhotoSavePresenterImpl implements PhotoSavePresenter{
    private static final String PHOTO_URI = "PHOTO_URI";
    private static final String PHOTO_KEY = "PHOTO_KEY";

    private PhotoSaveView mView;
    private PhotoInteractor mInteractor;

    public PhotoSavePresenterImpl(PhotoSaveView view){
        mView = view;
        mInteractor = new PhotoInteractorImpl();
    }

    @Override
    public void onDestroyView(){
        mView = null;
    }

    @Override
    public Uri getImageUri(Activity activity){
        return activity.getIntent().getParcelableExtra(PHOTO_URI);
    }

    @Override
    public void onSaveButtonClicked(Activity activity, String title, String desc){
        Uri uri = activity.getIntent().getParcelableExtra(PHOTO_URI);
        Photo photo = new Photo(title,"admin",desc,uri);
        mInteractor.savePhoto(photo, new PhotoInteractor.OnPhotoSaveListener() {
            @Override
            public void onSuccess() {
                if (activity.getParent() == null) {
                    activity.setResult(Activity.RESULT_OK);
                } else {
                    activity.getParent().setResult(Activity.RESULT_OK);
                }
                activity.finish();
            }

            @Override
            public void onError() {
                mView.showMessage(activity.getString(R.string.save_error));
                activity.finish();
            }
        });
        mView.showProgressBar();
    }

    @Override
    public void onBackButtonClicked(Activity activity){
        activity.finish();
    }
}
