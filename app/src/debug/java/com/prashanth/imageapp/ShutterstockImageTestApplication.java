package com.prashanth.imageapp;

import com.prashanth.imageapp.dependencyInjection.DaggerAppDaggerGraph;
import com.prashanth.imageapp.dependencyInjection.NetworkDaggerModule;

public class ShutterstockImageTestApplication extends ShutterstockImageApplication {

    protected DaggerAppDaggerGraph.Builder daggerComponent(ShutterstockImageApplication application) {
        return super.daggerComponent(this)
                .networkDaggerModule(new NetworkDaggerModule("http://localhost:8080/"));
    }

}