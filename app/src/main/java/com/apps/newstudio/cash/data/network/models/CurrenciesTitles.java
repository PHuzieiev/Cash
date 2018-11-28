package com.apps.newstudio.cash.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class CurrenciesTitles {
    @SerializedName("AED")
    @Expose
    private String aED;
    @SerializedName("AMD")
    @Expose
    private String aMD;
    @SerializedName("AUD")
    @Expose
    private String aUD;
    @SerializedName("AZN")
    @Expose
    private String aZN;
    @SerializedName("BGN")
    @Expose
    private String bGN;
    @SerializedName("BRL")
    @Expose
    private String bRL;
    @SerializedName("BYN")
    @Expose
    private String bYN;
    @SerializedName("CAD")
    @Expose
    private String cAD;
    @SerializedName("CHF")
    @Expose
    private String cHF;
    @SerializedName("CLP")
    @Expose
    private String cLP;
    @SerializedName("CNY")
    @Expose
    private String cNY;
    @SerializedName("CZK")
    @Expose
    private String cZK;
    @SerializedName("DKK")
    @Expose
    private String dKK;
    @SerializedName("DZD")
    @Expose
    private String dZD;
    @SerializedName("EGP")
    @Expose
    private String eGP;
    @SerializedName("EUR")
    @Expose
    private String eUR;
    @SerializedName("GBP")
    @Expose
    private String gBP;
    @SerializedName("GEL")
    @Expose
    private String gEL;
    @SerializedName("HKD")
    @Expose
    private String hKD;
    @SerializedName("HRK")
    @Expose
    private String hRK;
    @SerializedName("HUF")
    @Expose
    private String hUF;
    @SerializedName("ILS")
    @Expose
    private String iLS;
    @SerializedName("INR")
    @Expose
    private String iNR;
    @SerializedName("IQD")
    @Expose
    private String iQD;
    @SerializedName("JPY")
    @Expose
    private String jPY;
    @SerializedName("KGS")
    @Expose
    private String kGS;
    @SerializedName("KRW")
    @Expose
    private String kRW;
    @SerializedName("KWD")
    @Expose
    private String kWD;
    @SerializedName("KZT")
    @Expose
    private String kZT;
    @SerializedName("LBP")
    @Expose
    private String lBP;
    @SerializedName("MDL")
    @Expose
    private String mDL;
    @SerializedName("MXN")
    @Expose
    private String mXN;
    @SerializedName("NOK")
    @Expose
    private String nOK;
    @SerializedName("NZD")
    @Expose
    private String nZD;
    @SerializedName("PKR")
    @Expose
    private String pKR;
    @SerializedName("PLN")
    @Expose
    private String pLN;
    @SerializedName("RON")
    @Expose
    private String rON;
    @SerializedName("RUB")
    @Expose
    private String rUB;
    @SerializedName("SAR")
    @Expose
    private String sAR;
    @SerializedName("SEK")
    @Expose
    private String sEK;
    @SerializedName("SGD")
    @Expose
    private String sGD;
    @SerializedName("THB")
    @Expose
    private String tHB;
    @SerializedName("TJS")
    @Expose
    private String tJS;
    @SerializedName("TRY")
    @Expose
    private String tRY;
    @SerializedName("TWD")
    @Expose
    private String tWD;
    @SerializedName("USD")
    @Expose
    private String uSD;
    @SerializedName("VND")
    @Expose
    private String vND;

    public HashMap<String,String> getAll(){
        HashMap<String,String> map = new HashMap<>();

        map.put("aED",aED);
        map.put("aMD",aMD);
        map.put("aUD",aUD);
        map.put("aZN",aZN);
        map.put("bGN",bGN);
        map.put("bRL",bRL);
        map.put("bYN",bYN);
        map.put("cAD",cAD);
        map.put("cHF",cHF);
        map.put("cLP",cLP);
        map.put("cNY",cNY);
        map.put("cZK",cZK);
        map.put("dKK",dKK);
        map.put("dZD",dZD);
        map.put("eGP",eGP);
        map.put("eUR",eUR);
        map.put("gBP",gBP);
        map.put("gEL",gEL);
        map.put("hKD",hKD);
        map.put("hRK",hRK);
        map.put("hUF",hUF);
        map.put("iLS",iLS);
        map.put("iNR",iNR);
        map.put("iQD",iQD);
        map.put("jPY",jPY);
        map.put("kGS",kGS);
        map.put("kRW",kRW);
        map.put("kWD",kWD);
        map.put("kZT",kZT);
        map.put("lBP",lBP);
        map.put("mDL",mDL);
        map.put("mXN",mXN);
        map.put("nOK",nOK);
        map.put("nZD",nZD);
        map.put("pKR",pKR);
        map.put("pLN",pLN);
        map.put("rON",rON);
        map.put("rUB",rUB);
        map.put("sAR",sAR);
        map.put("sEK",sEK);
        map.put("sGD",sGD);
        map.put("tHB",tHB);
        map.put("tJS",tJS);
        map.put("tRY",tRY);
        map.put("tWD",tWD);
        map.put("uSD",uSD);
        map.put("vND",vND);

        return map;
    }

}
