package com.training.anton.panoramasgallery;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.anton.api.model.PanoramaPhoto;

/**
 * Fragment shows full photo after clicking on it in grid view
 */
public class FullPhotoFragment extends DialogFragment {
    private static final String EXTRA_PHOTO_URL = "com.training.anton.panoramasgallery.PHOTO_URL";
    private static final String EXTRA_PHOTO_TITLE = "com.training.anton.panoramasgallery.PHOTO_TITLE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View fragmentView = inflater.inflate(R.layout.fragment_full_view, container, false);
        String fullPhotoURL = getArguments().getString(EXTRA_PHOTO_URL);
        ImageView imageView = (ImageView) fragmentView.findViewById(R.id.full_photo_view);
        TextView textView = (TextView) fragmentView.findViewById(R.id.textInFragment);
        textView.setText(getArguments().getString(EXTRA_PHOTO_TITLE));

        Glide.with(getActivity())
                .load(fullPhotoURL.replace("mw2.google.com/mw-panoramio/photos/medium", "static.panoramio.com/photos/original"))
                .crossFade()
                .into(imageView);
        return fragmentView;
    }

    public static FullPhotoFragment create(PanoramaPhoto photo) {
        Bundle extra = new Bundle();
        extra.putString(EXTRA_PHOTO_URL, photo.getPhotoURL());
        extra.putString(EXTRA_PHOTO_TITLE, photo.getPhotoTitle());
        FullPhotoFragment fragmentFullPhoto = new FullPhotoFragment();
        fragmentFullPhoto.setArguments(extra);
        return fragmentFullPhoto;
    }
}
