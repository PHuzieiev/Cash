package com.apps.newstudio.cash.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LBP {

    @SerializedName("ask")
    @Expose
    private String ask;
    @SerializedName("bid")
    @Expose
    private String bid;

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

}