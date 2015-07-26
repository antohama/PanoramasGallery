package com.training.anton.PanoramasGallery;

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

/**
 * Adapter defines methods to set up the grid view,
 * basing on the ArrayList of robot names.
 */
public class GridViewCustomAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<PanoramaPhoto> mPanoramasList;

    public GridViewCustomAdapter(Context context) {
        mContext = context;
        mPanoramasList = Collections.EMPTY_LIST;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        // Filling the grid cell with images obtained from network
        View cellView = mInflater.inflate(R.layout.grid_cell, null);

        ImageView imageViewInCell = (ImageView) cellView.findViewById(R.id.imageViewInCell);
        PanoramaPhoto photo = getItem(position);
        Picasso.with(mContext).load(photo.getPhotoURL()).into(imageViewInCell);

        TextView textViewInCell = (TextView) cellView.findViewById(R.id.textViewInCell);
        textViewInCell.setMaxLines(2);
        textViewInCell.setText(photo.getPhotoTitle());

        return cellView;
    }

    public void addItems(List<PanoramaPhoto> updatedList){
        mPanoramasList = updatedList;
        mPanoramasList.add(new PanoramaPhoto());
        notifyDataSetChanged();
    }
}
