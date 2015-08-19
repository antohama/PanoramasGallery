package com.training.anton.panoramasgallery;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.training.anton.api.model.PanoramaPhoto;
import com.training.anton.api.model.Panoramas;
import com.training.anton.network.NetworkModule;

import java.util.List;

public class PanoramioActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,
        RecyclerAdapter.OnItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkModule networkModule;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        int gridColCount = getResources().getInteger(R.integer.gridColCount);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, gridColCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshlayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(PanoramioActivity.this);

        networkModule = ((PanoramioApplication) getApplicationContext()).getNetworkModule();
        networkModule.makeRequest(this);
    }

    public void responseSuccess(Panoramas panoramas) {
        List<PanoramaPhoto> listPhotos = panoramas.getPhotos();
        Log.d("Obtained list", listPhotos.toString());
        RecyclerAdapter mAdapter = new RecyclerAdapter(this);
        mAdapter.setmItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.updateContent(listPhotos);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showErrorMessage() {
        Snackbar.with(this).text(R.string.network_error_message).type(SnackbarType.MULTI_LINE).duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE).actionLabel(R.string.try_again).actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {
                networkModule.makeRequest(PanoramioActivity.this);
            }
        }).show(this);
    }

    @Override
    public void onRefresh() {
        networkModule.makeRequest(PanoramioActivity.this);
    }

    @Override
    public void onItemClick(View container, PanoramaPhoto panoramaPhoto) {
        Intent showFullPhotoIntent = new Intent(PanoramioActivity.this, FullPhotoActivity.class);
        showFullPhotoIntent.putExtra(FullPhotoActivity.EXTRA_FULL_PHOTO, panoramaPhoto);
        ImageView clickedImageView = (ImageView) container.findViewById(R.id.imageViewInCell);
        Bitmap photo = ((GlideBitmapDrawable) clickedImageView.getDrawable()).getBitmap();
        ActivityOptions options = ActivityOptions.
                makeThumbnailScaleUpAnimation(container, photo, 0, 0);
        startActivity(showFullPhotoIntent, options.toBundle());
    }
}
