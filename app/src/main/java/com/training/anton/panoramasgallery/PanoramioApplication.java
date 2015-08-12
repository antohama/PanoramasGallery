package com.training.anton.panoramasgallery;

import android.app.Application;
import android.content.Context;

import com.training.anton.network.NetworkModule;

public class PanoramioApplication extends Application {
    private final NetworkModule mNetworkModule = new NetworkModule();

    static PanoramioApplication getApplication(Context context) {
        return (PanoramioApplication) context.getApplicationContext();
    }

    public NetworkModule getNetworkModule() {
        return mNetworkModule;
    }
}
