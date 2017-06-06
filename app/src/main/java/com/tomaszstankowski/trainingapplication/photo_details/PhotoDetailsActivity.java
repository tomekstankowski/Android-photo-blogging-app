package com.tomaszstankowski.trainingapplication.photo_details;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;

import javax.inject.Inject;

/**
 * Activity displaying photo.
 */

public class PhotoDetailsActivity extends AppCompatActivity implements PhotoDetailsView {
    @Inject
    PhotoDetailsPresenter mPresenter;

    private TextView mAuthorTv;
    private TextView mTitleTv;
    private TextView mDescTv;
    private TextView mDateTv;
    private Button mEditBttn;
    private Button mRemoveBttn;
    private SimpleDraweeView mImage;
    private ProgressBar mProgressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        ((App) getApplication()).getMainComponent().inject(this);
        mAuthorTv = (TextView) findViewById(R.id.activity_photo_details_textview_author);
        mTitleTv = (TextView) findViewById(R.id.activity_photo_details_textview_title);
        mDescTv = (TextView) findViewById(R.id.activity_photo_details_textview_desc);
        mDateTv = (TextView) findViewById(R.id.activity_photo_details_textview_date);
        mEditBttn = (Button) findViewById(R.id.activity_photo_details_button_edit);
        mRemoveBttn = (Button) findViewById(R.id.activity_photo_details_button_remove);
        mImage = (SimpleDraweeView) findViewById(R.id.activity_photo_details_image);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_photo_details_progressbar);
        mEditBttn.setOnClickListener(view -> mPresenter.onEditButtonClicked());
        mRemoveBttn.setOnClickListener(view -> mPresenter.onRemoveButtonClicked());
        mPresenter.onCreateView(this);
    }

    @Override
    public void updateView(String title, String author, String desc, String date, Uri image, boolean isVisible) {
        mImage.setImageURI(image);
        mTitleTv.setText(title);
        mAuthorTv.setText(author);
        mDescTv.setText(desc);
        mDateTv.setText(date);
        if (isVisible) {
            mEditBttn.setVisibility(View.VISIBLE);
            mRemoveBttn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Activity getActivityContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}