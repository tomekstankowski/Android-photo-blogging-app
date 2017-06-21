package com.tomaszstankowski.trainingapplication.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tomaszstankowski.trainingapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter is supposed to show thumbnails in a grid.
 */

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder> {
    private List<Uri> mImages = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;

    public GalleryViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImage.setImageURI(mImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public void addItem(Uri item) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.gallery_item_imageview);
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
