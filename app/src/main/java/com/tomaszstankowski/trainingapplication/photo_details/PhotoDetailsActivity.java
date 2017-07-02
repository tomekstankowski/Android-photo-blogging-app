package com.tomaszstankowski.trainingapplication.photo_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity displaying photo.
 */

public class PhotoDetailsActivity extends AppCompatActivity implements PhotoDetailsView {

    public static void start(Context context) {
        Intent starter = new Intent(context, PhotoDetailsActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, Map<String, Serializable> extras) {
        Intent starter = new Intent(context, PhotoDetailsActivity.class);
        for (Map.Entry<String, Serializable> e : extras.entrySet()) {
            starter.putExtra(e.getKey(), e.getValue());
        }
        context.startActivity(starter);
    }

    @Inject
    PhotoDetailsPresenter mPresenter;

    @BindView(R.id.activity_photo_details_rootview)
    View mRootView;
    @BindView(R.id.activity_photo_details_textview_author)
    TextView mAuthorTv;
    @BindView(R.id.activity_photo_details_textview_title)
    TextView mTitleTv;
    @BindView(R.id.activity_photo_details_textview_desc)
    TextView mDescTv;
    @BindView(R.id.activity_photo_details_textview_date)
    TextView mDateTv;
    @BindView(R.id.activity_photo_details_button_edit)
    Button mEditBttn;
    @BindView(R.id.activity_photo_details_button_remove)
    Button mRemoveBttn;
    @BindView(R.id.activity_photo_details_image)
    ImageView mImageView;
    @BindView(R.id.activity_photo_details_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.activity_photo_details_toolbar)
    Toolbar mToolbar;

    @OnClick(R.id.activity_photo_details_button_edit)
    public void onEditButtonClicked() {
        mPresenter.onEditButtonClicked();
    }

    @OnClick(R.id.activity_photo_details_button_remove)
    public void onRemoveButtonClicked() {
        mPresenter.onRemoveButtonClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        ButterKnife.bind(this);
        ((App) getApplication()).getMainComponent().inject(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyView();
    }

    @Override
    public void startPhotoSaveView(Map<String, Serializable> args) {
        PhotoSaveActivity.start(this, args);
    }

    @Override
    public Serializable getArg(String key) {
        Intent intent = getIntent();
        return intent != null ? intent.getSerializableExtra(key) : null;
    }

    @Override
    public void updatePhotoView(String title, String desc, String date, StorageReference image) {
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(image)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .into(mImageView);
        mTitleTv.setText(title);
        mDescTv.setText(desc);
        mDateTv.setText(date);
    }

    @Override
    public void updateAuthorView(String author, boolean isAuthor) {
        mAuthorTv.setText(author);
        if (isAuthor) {
            mEditBttn.setVisibility(View.VISIBLE);
            mRemoveBttn.setVisibility(View.VISIBLE);
        }
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
    public void showMessage(Message message) {
        switch (message) {
            case LOAD_ERROR:
                Snackbar.make(mRootView, R.string.load_error, Snackbar.LENGTH_LONG).show();
                break;
            case REMOVE_ERROR:
                Snackbar.make(mRootView, R.string.remove_error, Snackbar.LENGTH_LONG).show();
                break;
        }

    }
}