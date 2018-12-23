package com.apps.newstudio.cash.data.network;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String URL = "http://resources.finance.ua";

    /**
     * Creates Retrofit object
     *
     * @return Retrofit
     */
    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build();

    /**
     * Gets RetrofitServiceUkr
     *
     * @return RetrofitServiceUkr
     */
    public static RetrofitServiceUkr getRetrofitServiceUkr() {
        return getRetrofitInstance().create(RetrofitServiceUkr.class);
    }

    /**
     * Gets RetrofitServiceRus
     *
     * @return RetrofitServiceRus
     */
    public static RetrofitServiceRus getRetrofitServiceRus() {
        return getRetrofitInstance().create(RetrofitServiceRus.class);
    }
}
