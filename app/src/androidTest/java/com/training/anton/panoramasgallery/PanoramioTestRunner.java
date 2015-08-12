package com.training.anton.panoramasgallery;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

public class PanoramioTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, EspressoPanoramioApplication.class.getName(), context);
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        EspressoPanoramioApplication.get().stopServer();
        super.finish(resultCode, results);
    }
}
