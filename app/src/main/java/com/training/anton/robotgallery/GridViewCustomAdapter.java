package com.training.anton.robotgallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Adapter defines methods to set up the grid view,
 * basing on the ArrayList of robot names.
 */
public class GridViewCustomAdapter extends BaseAdapter {

    String BASEURL = "http://www.robohash.com/";

    Context mContext;
    LayoutInflater mInflater;
    ArrayList<String> mRobotList;

    private Drawable robotImage;
    private ProgressDialog pDialog;

    public GridViewCustomAdapter(Context context, ArrayList<String> robotList) {
        mContext = context;
        mRobotList = robotList;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRobotList.size();
    }

    @Override
    public String getItem(int position) {
        return mRobotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getUrl(String name) {
        return BASEURL + name + ".png";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Filling the grid cell with images obtained from network

        View cellView = mInflater.inflate(R.layout.grid_cell, null);

        NetworkImageView imageViewInCell = (NetworkImageView) cellView.findViewById(R.id.imageViewInCell);
        imageViewInCell.setDefaultImageResId(R.drawable.default_image);
        imageViewInCell.setErrorImageResId(R.drawable.error_image);

        String robotName = getItem(position);

        TextView textViewInCell = (TextView) cellView.findViewById(R.id.textViewInCell);
        textViewInCell.setText(robotName);

        ImageLoader mImageLoader = RequestQueueSingleton.getInstance(mContext.getApplicationContext()).getImageLoader();
        imageViewInCell.setImageUrl(getUrl(robotName), mImageLoader);

        return cellView;
    }
}
