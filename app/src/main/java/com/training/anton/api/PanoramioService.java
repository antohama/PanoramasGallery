package com.training.anton.api;

import retrofit.RestAdapter;

public class PanoramioService {
    //Chicago
    public static final double LAT = 41.985844;
    public static final double LONG = -87.655063;
    private static String mBaseUrl = "http://www.panoramio.com";

    public static void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public static PanoramioAPI getService() {
        return new RestAdapter.Builder()
                .setEndpoint(mBaseUrl)
                .build()
                .create(PanoramioAPI.class);
    }
}
