package com.tomaszstankowski.trainingapplication.photo_save;

import android.net.Uri;
import android.os.Bundle;
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
import com.tomaszstankowski.trainingapplication.R;

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
            presenter = new PhotoSavePresenterImpl(this);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_photo_save_progressbar);
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
        Uri uri = presenter.getImageUri(this);
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(photoView.getController())
                .setImageRequest(request)
                .build();
        photoView.setController(controller);
    }

    private void setBackButton() {
        Button button = (Button) findViewById(R.id.activity_photo_save_button_back);
        button.setOnClickListener(view -> presenter.onBackButtonClicked(this));
    }

    private void setSaveButton() {
        Button button = (Button) findViewById(R.id.activity_photo_save_button_save);
        button.setOnClickListener(view -> {
            EditText titleLabel = (EditText) findViewById(R.id.activity_photo_save_edittext_title);
            String title = titleLabel.getText().toString();
            EditText descLabel = (EditText) findViewById(R.id.activity_photo_save_edittext_desc);
            String desc = descLabel.getText().toString();
            presenter.onSaveButtonClicked(this, title, desc);
        });
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
