package com.apps.newstudio.cash.data.network.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainModel {

    @SerializedName("sourceId")
    @Expose
    private String sourceId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("organizations")
    @Expose
    private ArrayList<Organization> organizations = null;
    @SerializedName("currencies")
    @Expose
    private CurrenciesTitles currencies;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }

    public CurrenciesTitles getCurrencies() {
        return currencies;
    }

    public void setCurrencies(CurrenciesTitles currencies) {
        this.currencies = currencies;
    }

}