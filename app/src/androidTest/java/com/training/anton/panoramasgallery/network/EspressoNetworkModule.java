package com.training.anton.panoramasgallery.network;

import android.support.test.espresso.contrib.CountingIdlingResource;

import com.training.anton.api.PanoramioAPI;
import com.training.anton.api.PanoramioService;
import com.training.anton.api.model.Panoramas;
import com.training.anton.network.NetworkModule;
import com.training.anton.panoramasgallery.PanoramioActivity;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static com.training.anton.panoramasgallery.EspressoPanoramioApplication.getServer;

public class EspressoNetworkModule extends NetworkModule {
    private final CountingIdlingResource idle;

    public EspressoNetworkModule() {
        idle = new CountingIdlingResource("Count", true);
        registerIdlingResources(idle);
    }

    @Override
    public PanoramioAPI getService() {
        PanoramioService.setBaseUrl(getServer().getUrl("/").toString());
        return PanoramioService.getService();
    }

    @Override
    public void makeRequest(PanoramioActivity panoramioActivity) {
        super.makeRequest(panoramioActivity);
        idle.increment();
    }

    @Override
    public void success(Panoramas panoramas, Response response) {
        super.success(panoramas, response);
        idle.decrement();
    }

    @Override
    public void failure(RetrofitError error) {
        super.failure(error);
        idle.decrement();
    }
}