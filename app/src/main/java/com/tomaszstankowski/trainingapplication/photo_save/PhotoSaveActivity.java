package com.tomaszstankowski.trainingapplication.photo_save;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity starts after user captures photo or edits already existing one.
 * He can set title and desc and save photo.
 */

public class PhotoSaveActivity extends AppCompatActivity implements PhotoSaveView {
    @Inject
    PhotoSavePresenter mPresenter;

    @BindView(R.id.activity_photo_save_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.activity_photo_save_edittext_title)
    EditText mTitle;
    @BindView(R.id.activity_photo_save_edittext_desc)
    EditText mDesc;
    @BindView(R.id.activity_photo_save_imageview_photo)
    SimpleDraweeView mImage;
    @BindView(R.id.activity_photo_save_button_back)
    Button mBackButton;
    @BindView(R.id.activity_photo_save_button_save)
    Button mSaveButton;

    @OnClick(R.id.activity_photo_save_button_back)
    public void onBackButtonClicked() {
        mPresenter.onBackButtonClicked();
    }

    @OnClick(R.id.activity_photo_save_button_save)
    public void onSaveButtonClicked() {
        String title = mTitle.getText().toString();
        String desc = mDesc.getText().toString();
        mPresenter.onSaveButtonClicked(title, desc);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_save);
        ButterKnife.bind(this);
        ((App) getApplication()).getMainComponent().inject(this);
        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void updateView(@Nullable String title, @Nullable String desc, @NonNull Uri imageUri, boolean resize) {
        setImage(imageUri, resize);
        if (title != null)
            mTitle.setText(title);
        if (desc != null)
            mDesc.setText(desc);
    }

    @Override
    public Activity getActivityContext() {
        return this;
    }

    private void setImage(Uri uri, boolean resize) {
        if (resize) {
            int width = 50, height = 50;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(mImage.getController())
                    .setImageRequest(request)
                    .build();
            mImage.setController(controller);
        } else
            mImage.setImageURI(uri);
    }

    @Override
    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }
}
