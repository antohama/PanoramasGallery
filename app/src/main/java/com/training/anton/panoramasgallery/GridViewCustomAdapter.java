package com.training.anton.panoramasgallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.anton.api.model.PanoramaPhoto;

import java.util.Collections;
import java.util.List;

public class GridViewCustomAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<PanoramaPhoto> mPanoramasList;

    public GridViewCustomAdapter(Context context) {
        mContext = context;
        mPanoramasList = Collections.EMPTY_LIST;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPanoramasList.size();
    }

    @Override
    public PanoramaPhoto getItem(int position) {
        return mPanoramasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_cell, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageViewInCell);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textViewInCell);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Filling the grid cell with images obtained from network
        PanoramaPhoto photo = getItem(position);
        Picasso.with(mContext).load(photo.getPhotoURL()).into(viewHolder.imageView);

        viewHolder.textView.setMaxLines(2);
        viewHolder.textView.setText(photo.getPhotoTitle());
        return convertView;
    }

    public void updateList(List<PanoramaPhoto> updatedList){
        mPanoramasList = updatedList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
