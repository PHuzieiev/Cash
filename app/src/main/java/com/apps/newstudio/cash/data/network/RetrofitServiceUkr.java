package com.apps.newstudio.cash.data.network;

import com.apps.newstudio.cash.data.network.models.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitServiceUkr {

    /**
     * Gets JSON service
     */
    @GET("/ua/public/currency-cash.json")
    Call<MainModel> getMyJSON();
}
