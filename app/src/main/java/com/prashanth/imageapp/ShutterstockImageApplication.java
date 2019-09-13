package com.prashanth.imageapp;

import android.app.Application;
import com.prashanth.imageapp.dependencyInjection.AppDaggerGraph;
import com.prashanth.imageapp.dependencyInjection.ApplicationModule;
import com.prashanth.imageapp.dependencyInjection.DaggerAppDaggerGraph;
import com.prashanth.imageapp.dependencyInjection.NetworkDaggerModule;
import com.prashanth.imageapp.dependencyInjection.PresenterModule;
import timber.log.Timber;

public class ShutterstockImageApplication extends Application {

    public static AppDaggerGraph component;

    protected DaggerAppDaggerGraph.Builder daggerComponent(ShutterstockImageApplication application) {
        return DaggerAppDaggerGraph.builder()
                .networkDaggerModule(new NetworkDaggerModule(BuildConfig.SHUTTERSTOCK_URL))
                .presenterModule(new PresenterModule(application))
                .applicationModule(new ApplicationModule(this));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = daggerComponent(this).build();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
