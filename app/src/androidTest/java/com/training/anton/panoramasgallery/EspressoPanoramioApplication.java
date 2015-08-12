package com.training.anton.panoramasgallery;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.training.anton.network.NetworkModule;
import com.training.anton.panoramasgallery.network.EspressoNetworkModule;

import java.io.IOException;

public class EspressoPanoramioApplication extends PanoramioApplication {
    private EspressoNetworkModule mNetworkModule = new EspressoNetworkModule();
    private static MockWebServer server;

    public static EspressoPanoramioApplication get() {
        Context context = InstrumentationRegistry.getTargetContext().getApplicationContext();
        return (EspressoPanoramioApplication) PanoramioApplication.getApplication(context);
    }

    public static MockWebServer getServer() {
        if (server == null) {
            server = new MockWebServer();
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return server;
    }

    @Override
    public NetworkModule getNetworkModule() {
        return mNetworkModule;
    }

    public void stopServer() {
        try {
            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
