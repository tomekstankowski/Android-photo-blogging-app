package com.tomaszstankowski.trainingapplication.photo_details;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tomaszstankowski.trainingapplication.R;

/**
 * Activity displaying photo.
 */

public class PhotoDetailsActivity extends AppCompatActivity implements PhotoDetailsView {
    private TextView mAuthorTv;
    private TextView mTitleTv;
    private TextView mDescTv;
    private TextView mDateTv;
    private Button mEditBttn;
    private SimpleDraweeView mImage;
    private ProgressBar mProgressBar;

    private PhotoDetailsPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        mAuthorTv = (TextView) findViewById(R.id.activity_photo_details_textview_author);
        mTitleTv = (TextView) findViewById(R.id.activity_photo_details_textview_title);
        mDescTv = (TextView) findViewById(R.id.activity_photo_details_textview_desc);
        mDateTv = (TextView) findViewById(R.id.activity_photo_details_textview_date);
        mEditBttn = (Button) findViewById(R.id.activity_photo_details_button_edit);
        mImage = (SimpleDraweeView) findViewById(R.id.activity_photo_details_image);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_photo_details_progressbar);
        mEditBttn.setOnClickListener(view -> mPresenter.onEditButtonClicked());
        if (mPresenter == null)
            mPresenter = new PhotoDetailsPresenterImpl();
        mPresenter.onCreateView(this);
    }

    @Override
    public void updateView(String title, String author, String desc, String date, Uri image, boolean isEditable) {
        mImage.setImageURI(image);
        mTitleTv.setText(title);
        mAuthorTv.setText(author);
        mDescTv.setText(desc);
        mDateTv.setText(date);
        if (isEditable)
            mEditBttn.setVisibility(View.VISIBLE);
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
}