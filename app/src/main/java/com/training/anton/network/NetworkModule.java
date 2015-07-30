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
    private PanoramioActivity mActivity;

    protected PanoramioAPI getService() {
        return PanoramioService.getService();
    }

    public void makeRequest(PanoramioActivity panoramioActivity) {
        mActivity = panoramioActivity;
        getService().getPanoramas("full", "medium", 0, 100, PanoramioService.LONG - 0.005, PanoramioService.LAT - 0.005, PanoramioService.LONG + 0.005, PanoramioService.LAT + 0.005, this);
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
