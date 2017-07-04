package com.tomaszstankowski.trainingapplication.photo_details;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.details.DetailsView;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Activity displaying photo.
 */

public class PhotoDetailsFragment extends Fragment implements PhotoDetailsView {

    @Inject
    PhotoDetailsPresenter mPresenter;

    private DetailsView mParent;

    private Unbinder mUnbinder;

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

    @OnClick(R.id.activity_photo_details_button_edit)
    public void onEditButtonClicked() {
        mPresenter.onEditButtonClicked();
    }

    @OnClick(R.id.activity_photo_details_button_remove)
    public void onRemoveButtonClicked() {
        mPresenter.onRemoveButtonClicked();
    }

    @OnClick(R.id.activity_photo_details_textview_author)
    public void onUserClicked() {
        mPresenter.onUserClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getMainComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_details, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            mParent = (DetailsView) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement DetailsView");
        }
        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void startPhotoSaveView(Map<String, Serializable> args) {
        PhotoSaveActivity.start(this, args);
    }

    @Override
    public void startUserView(Map<String, Serializable> args) {
        args.put(Config.USER_DETAILS_MODE, Config.USER_DETAILS_MODE_DEFAULT);
        mParent.switchPage(DetailsView.Page.USER, args);
    }

    @Override
    public Serializable getArg(String key) {
        return getArguments() == null ? null : getArguments().getSerializable(key);
    }

    @Override
    public void finish() {
        mParent.back();
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