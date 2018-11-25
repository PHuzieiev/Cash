package com.apps.newstudio.cash.data.network;

import com.apps.newstudio.cash.data.network.models.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitServiceRus {

    /**
     * Gets JSON service
     */
    @GET("/ru/public/currency-cash.json")
    Call<MainModel> getMyJSON();
}
