package com.tomaszstankowski.trainingapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.presenter.PhotoSavePresenter;
import com.tomaszstankowski.trainingapplication.presenter.PhotoSavePresenterImpl;
import com.tomaszstankowski.trainingapplication.presenter.Presenter;

/**
 * Activity starts after user captures photo.
 * He can then set title and desc and accept or reject photo.
 */

public class PhotoSaveActivity extends AppCompatActivity implements PhotoSaveView {
    private PhotoSavePresenter presenter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_save);
        if (presenter == null)
            presenter = new PhotoSavePresenterImpl();
        presenter.setView(this);
        setImage();
        setBackButton();
        setSaveButton();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }

    private void setImage() {
        SimpleDraweeView photoView = (SimpleDraweeView) findViewById(R.id.activity_photo_save_imageview_photo);
        Uri uri = presenter.getImageUri();
        photoView.setImageURI(uri);
    }

    private void setBackButton() {
        Button button = (Button) findViewById(R.id.activity_photo_save_button_back);
        button.setOnClickListener(view -> presenter.onBackButtonClicked());
    }

    private void setSaveButton() {
        Button button = (Button) findViewById(R.id.activity_photo_save_button_save);
        button.setOnClickListener(view -> {
            EditText titleLabel = (EditText) findViewById(R.id.activity_photo_save_edittext_title);
            String title = titleLabel.getText().toString();
            EditText descLabel = (EditText) findViewById(R.id.activity_photo_save_edittext_desc);
            String desc = descLabel.getText().toString();
            presenter.onSaveButtonClicked(title, desc);
        });
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public Activity getContext(){
        return this;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        if (mProgressBar == null)
            mProgressBar = (ProgressBar) findViewById(R.id.activity_photo_save_progressbar);
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public Activity getActivity(){
        return this;
    }
}
