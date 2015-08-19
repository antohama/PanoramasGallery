package com.training.anton.panoramasgallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.anton.api.model.PanoramaPhoto;

import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View container, PanoramaPhoto panoramaPhoto);
    }

    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private List<PanoramaPhoto> mPanoramasList;
    private int maxThumbWidth;
    private int maxThumbHeight;

    public RecyclerAdapter(Context context) {
        mContext = context;
        mPanoramasList = Collections.EMPTY_LIST;
        maxThumbWidth = context.getResources().getInteger(R.integer.maxThumbWidth);
        maxThumbHeight = context.getResources().getInteger(R.integer.maxThumbHeight);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PanoramaPhoto photo = getItem(i);

        Glide.with(mContext)
                .load(photo.getPhotoURL())
                .override(maxThumbWidth, maxThumbHeight)
                .into(viewHolder.mImageView);
        viewHolder.mTextView.setText(photo.getPhotoTitle());
        viewHolder.itemView.setOnClickListener(new PhotoClickListener(photo));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getPhotoId();
    }

    private PanoramaPhoto getItem(int i) {
        return mPanoramasList.get(i);
    }

    @Override
    public int getItemCount() {
        return mPanoramasList.size();
    }

    public void updateContent(List<PanoramaPhoto> updatedList) {
        mPanoramasList = updatedList;
        notifyDataSetChanged();
    }

    public void setmItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageViewInCell);
            mTextView = (TextView) itemView.findViewById(R.id.textViewInCell);
        }
    }

    private class PhotoClickListener implements View.OnClickListener {
        private final PanoramaPhoto photo;

        private PhotoClickListener(PanoramaPhoto photo) {
            this.photo = photo;
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, photo);
            }
        }

    }
}
