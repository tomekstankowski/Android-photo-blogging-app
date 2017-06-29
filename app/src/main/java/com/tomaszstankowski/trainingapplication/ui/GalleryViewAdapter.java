package com.tomaszstankowski.trainingapplication.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter is supposed to show thumbnails in a grid.
 */

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder> {
    private List<StorageReference> mImages = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private Context mContext;

    public GalleryViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext)
                .using(new FirebaseImageLoader())
                .load(mImages.get(position))
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public void addItem(StorageReference item) {
        mImages.add(item);
        notifyItemInserted(mImages.size() - 1);
    }

    public void removeAllItems() {
        mImages.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mImages.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.gallery_item_imageview);
            itemView.setOnClickListener(view -> {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
