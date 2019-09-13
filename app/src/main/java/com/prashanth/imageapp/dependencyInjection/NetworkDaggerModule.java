package com.prashanth.imageapp.dependencyInjection;

import android.app.Application;
import android.util.Base64;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prashanth.imageapp.BuildConfig;
import com.prashanth.imageapp.network.ShutterstockAPI;
import com.prashanth.imageapp.utils.Constants;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkDaggerModule {

    private String url;

    private static final int TIMEOUT = 10;

    public NetworkDaggerModule(String url) {
        this.url = url;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 30 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        HttpLoggingInterceptor debugInterceptor = new HttpLoggingInterceptor();
        debugInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        client.followRedirects(true);
        client.followSslRedirects(true);
        client.retryOnConnectionFailure(true);
        client.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.addInterceptor(chain -> {
            Request request = chain.request();
            String authorization = BuildConfig.CLIENT_ID + ":" + BuildConfig.CLIENT_SECRET;
            request = request.newBuilder()
                    .addHeader(Constants.AUTHORIZATION,
                            Constants.BASIC + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP))
                    .build();
            return chain.proceed(request);
        });
        if (BuildConfig.DEBUG) {
            client.addInterceptor(debugInterceptor);
        }
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitBuilder(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    ShutterstockAPI provideShutterstockAPI(Retrofit retrofit) {
        return retrofit.create(ShutterstockAPI.class);
    }

}