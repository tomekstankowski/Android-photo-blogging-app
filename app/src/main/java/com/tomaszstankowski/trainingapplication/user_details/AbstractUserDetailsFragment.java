package com.tomaszstankowski.trainingapplication.user_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.ui.GalleryViewAdapter;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractUserDetailsFragment extends Fragment implements UserDetailsView, GalleryViewAdapter.OnItemClickListener {
    @Inject
    UserDetailsPresenter mPresenter;

    @BindView(R.id.fragment_user_photos_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_user_photos_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.fragment_user_photos_textview_username)
    TextView mUsernameTv;
    @BindView(R.id.fragment_user_photos_textview_photos_count)
    TextView mPhotosCountTv;

    private GalleryViewAdapter mAdapter;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_details, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        mAdapter = new GalleryViewAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        updatePhotosCount();

        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public Serializable getArg(String key) {
        return getArguments() == null ? null : getArguments().getSerializable(key);
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.onPhotoClicked(position);
    }

    @Override
    public void addPhoto(StorageReference image) {
        mAdapter.addItem(image);
        updatePhotosCount();
    }

    @Override
    public void removePhoto(int position) {
        mAdapter.removeItem(position);
        updatePhotosCount();
    }

    @Override
    public void updateUsername(String username) {
        mUsernameTv.setText(username);
    }

    private void updatePhotosCount() {
        mPhotosCountTv.setText(getString(R.string.photos_count) + " " + mAdapter.getItemCount());
    }
}
