package com.training.anton.panoramasgallery;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Fragment shows full photo after clicking on it in grid view
 */
public class FullPhotoFragment extends DialogFragment {
    private static final String EXTRA_PHOTO_URL = "com.training.anton.panoramasgallery.PHOTO_URL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_full_view, container, false);
        String fullPhotoURL = getArguments().getString(EXTRA_PHOTO_URL);
        ImageView imageView = (ImageView) fragmentView.findViewById(R.id.full_photo_view);
        TextView textView = (TextView) fragmentView.findViewById(R.id.textInFragment);
        textView.setText("Width: " + getArguments().getInt("width") + "; height: "  + getArguments().getInt("height"));

        Picasso.with(getActivity()).load(fullPhotoURL.replace("mw2.google.com/mw-panoramio/photos/medium", "static.panoramio.com/photos/original")).into(imageView);
        return fragmentView;
    }

    public static FullPhotoFragment create(String fullPhotoURL) {
        Bundle extra = new Bundle();
        extra.putString(EXTRA_PHOTO_URL, fullPhotoURL);
        FullPhotoFragment fragmentFullPhoto = new FullPhotoFragment();
        fragmentFullPhoto.setArguments(extra);
        return fragmentFullPhoto;
    }
}
