package com.training.anton.panoramasgallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.training.anton.api.model.PanoramaPhoto;
import com.training.anton.network.NetworkModule;

public class FullPhotoActivity extends Activity {
    public static final String EXTRA_FULL_PHOTO = "com.training.anton.panoramasgallery.FULL_PHOTO";
    Activity activityContext;

    public FullPhotoActivity() {
        activityContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo);

        PanoramaPhoto photo = getIntent().getParcelableExtra(EXTRA_FULL_PHOTO);
        ImageView imageView = (ImageView) findViewById(R.id.full_photo_image);
        Glide.with(activityContext)
                .load(photo.getPhotoURL().replace(NetworkModule.pathToThumb, NetworkModule.pathToOriginal))
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        findViewById(R.id.image_loading_text).setVisibility(View.INVISIBLE);
                        TextView textPhotoDimensions = (TextView) findViewById(R.id.photo_dimensions);
                        textPhotoDimensions.setText(resource.getIntrinsicWidth() + " * " + resource.getIntrinsicHeight());
                    }

                });
        TextView textPhotoDescription = (TextView) findViewById(R.id.photo_description);
        textPhotoDescription.setText(photo.getPhotoTitle());

        TextView textPhotoAuthor = (TextView) findViewById(R.id.photo_author);
        textPhotoAuthor.setText(photo.getPhotoAuthor());

        TextView textPhotoDate = (TextView) findViewById(R.id.photo_date);
        textPhotoDate.setText(photo.getUploadDate());
    }
}
