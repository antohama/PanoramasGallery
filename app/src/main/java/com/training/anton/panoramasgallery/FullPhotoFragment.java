package com.training.anton.panoramasgallery;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Fragment shows full photo after clicking on it in grid view
 */
public class FullPhotoFragment extends DialogFragment {
    private static final String EXTRA_PHOTO_URL = "com.training.anton.robotgallery.PHOTO_URL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_full_view, container, false);
        String fullPhotoURL = getArguments().getString(EXTRA_PHOTO_URL);
        ImageView imageView = (ImageView) fragmentView.findViewById(R.id.full_photo_view);

        Picasso.with(getActivity()).load(fullPhotoURL).into(imageView);
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
