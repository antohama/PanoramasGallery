package com.training.anton.panoramasgallery;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.training.anton.api.PanoramioService;
import com.training.anton.api.model.PanoramaPhoto;
import com.training.anton.api.model.Panoramas;
import com.training.anton.network.NetworkModule;

import java.util.List;

public class PanoramioActivity extends Activity implements
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerAdapter.OnItemClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int FIVE_SEC = 5 * 1000;

    private GoogleApiClient mGoogleApiClient;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkModule networkModule;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildGoogleApiClient();
        setContentView(R.layout.activity_main);
        createRecyclerView();
        createSwipeRefreshLayout();
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
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

    @Override
    public void onConnected(Bundle dataBundle) {
        // Get the most recent location measurement
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (null != mLastLocation) {
            PanoramioService.setLat(mLastLocation.getLatitude());
            PanoramioService.setLon(mLastLocation.getLongitude());
        } else {
            locationUnavailableError("Cannot obtain current location.");
        }
        showPhotos();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        locationUnavailableError("Cannot connect to Google Play Service.");
        showPhotos();
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    private void createRecyclerView() {
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
    }

    private void createSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshlayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(PanoramioActivity.this);
    }

    public void responseSuccess(Panoramas panoramas) {
        List<PanoramaPhoto> listPhotos = panoramas.getPhotos();
        Log.d("Obtained list", listPhotos.toString());
        RecyclerAdapter mAdapter = new RecyclerAdapter(this);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.updateContent(listPhotos);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showPhotos() {
        networkModule = ((PanoramioApplication) getApplicationContext()).getNetworkModule();
        networkModule.makeRequest(this);
    }

    public void networkError() {
        Snackbar.with(this).text(R.string.network_error_message).type(SnackbarType.MULTI_LINE).duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE).actionLabel(R.string.try_again).actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {
                networkModule.makeRequest(PanoramioActivity.this);
            }
        }).show(this);
    }

    private void locationUnavailableError(String reason) {
        // setting predefined location
        PanoramioService.setPredefinedLocation();

        String message = reason + System.getProperty("line.separator") + "We show you a predefined location.";
        final Snackbar snackbar = Snackbar.with(this).text(message).type(SnackbarType.MULTI_LINE).duration(FIVE_SEC);
        snackbar.actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {
                snackbar.dismiss();
            }
        }).show(this);
    }
}
