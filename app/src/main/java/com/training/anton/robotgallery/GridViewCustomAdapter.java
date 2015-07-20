package com.training.anton.robotgallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
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

        /* Obsolete. Has been replaced with Volley lib as more effective mechanism
        for fetching images from network.

        new DownloadImageTask(holder.imageViewInCell).execute(url);
         */

       /* Obsolete. Didn't use caching.
       ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.imageViewInCell.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener(){
                    public void onErrorResponse(VolleyError error){
                        holder.imageViewInCell.setImageResource(R.drawable.image_load_error);
                    }
                });
        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(request);*/
    }

    /* Obsolete. Has been replaced with Volley lib as more effective mechanism
    for fetching images from network.

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public DownloadImageTask(ImageView imageView) {
            this.imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                Log.d(urldisplay, "URL");
                InputStream in = new URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }*/
}
