package com.training.anton.network;

import android.util.Log;

import com.training.anton.api.PanoramioAPI;
import com.training.anton.api.PanoramioService;
import com.training.anton.api.model.Panoramas;
import com.training.anton.panoramasgallery.PanoramioActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NetworkModule implements Callback<Panoramas> {
    private static final String PHOTOS_SET_FULL = "full";
    private static final String PHOTO_SIZE_SMALL = "small";
    public static final String pathToThumb = "mw2.google.com/mw-panoramio/photos/" + PHOTO_SIZE_SMALL;
    public static final String pathToOriginal = "static.panoramio.com/photos/original";
    private PanoramioActivity mActivity;

    protected PanoramioAPI getService() {
        return PanoramioService.getService();
    }

    public void makeRequest(PanoramioActivity panoramioActivity) {
        mActivity = panoramioActivity;
        int from = 0;
        int to = 100;
        double distance = 0.005;
        getService().getPanoramas(PHOTOS_SET_FULL, PHOTO_SIZE_SMALL, from, to,
                PanoramioService.LONG - distance,
                PanoramioService.LAT - distance,
                PanoramioService.LONG + distance,
                PanoramioService.LAT + distance,
                this);
    }

    @Override
    public void success(Panoramas panoramas, Response response) {
        Log.d("URL of request", response.getUrl());
        mActivity.responseSuccess(panoramas);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("URL of request", error.getUrl());
        error.printStackTrace();
        mActivity.showErrorMessage();
    }
}
