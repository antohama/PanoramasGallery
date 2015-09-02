package com.training.anton.api;

import retrofit.RestAdapter;

public class PanoramioService {
    // Predefined location, used in case of current location unavailability
    // GooglePlex location
    private static final double LAT = 37.422006;
    private static final double LON = -122.084095;

    private static String mBaseUrl = "http://www.panoramio.com";
    private static double mLat;
    private static double mLon;

    public static void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public static double getLat() {
        return mLat;
    }

    public static void setLat(double lat) {
        mLat = lat;
    }

    public static double getLon() {
        return mLon;
    }

    public static void setLon(double lon) {
        mLon = lon;
    }

    public static PanoramioAPI getService() {
        return new RestAdapter.Builder()
                .setEndpoint(mBaseUrl)
                .build()
                .create(PanoramioAPI.class);
    }

    public static void setPredefinedLocation() {
        mLat = LAT;
        mLon = LON;
    }
}
