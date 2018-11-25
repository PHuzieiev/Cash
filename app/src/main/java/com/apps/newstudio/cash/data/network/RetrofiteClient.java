package com.apps.newstudio.cash.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofiteClient {

    private static final String URL = "http://resources.finance.ua";

    /**
     * Creates Retrofit object
     *
     * @return Retrofit
     */
    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

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
