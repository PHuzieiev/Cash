package com.apps.newstudio.cash.data.network.models;

import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Currencies {

    @SerializedName("EUR")
    @Expose
    private EUR eUR;
    @SerializedName("RUB")
    @Expose
    private RUB rUB;
    @SerializedName("USD")
    @Expose
    private USD uSD;
    @SerializedName("AED")
    @Expose
    private AED aED;
    @SerializedName("AMD")
    @Expose
    private AMD aMD;
    @SerializedName("AUD")
    @Expose
    private AUD aUD;
    @SerializedName("AZN")
    @Expose
    private AZN aZN;
    @SerializedName("BGN")
    @Expose
    private BGN bGN;
    @SerializedName("BRL")
    @Expose
    private BRL bRL;
    @SerializedName("BYN")
    @Expose
    private BYN bYN;
    @SerializedName("CAD")
    @Expose
    private CAD cAD;
    @SerializedName("CHF")
    @Expose
    private CHF cHF;
    @SerializedName("CLP")
    @Expose
    private CLP cLP;
    @SerializedName("CNY")
    @Expose
    private CNY cNY;
    @SerializedName("CZK")
    @Expose
    private CZK cZK;
    @SerializedName("DKK")
    @Expose
    private DKK dKK;
    @SerializedName("EGP")
    @Expose
    private EGP eGP;
    @SerializedName("GBP")
    @Expose
    private GBP gBP;
    @SerializedName("GEL")
    @Expose
    private GEL gEL;
    @SerializedName("HKD")
    @Expose
    private HKD hKD;
    @SerializedName("HRK")
    @Expose
    private HRK hRK;
    @SerializedName("HUF")
    @Expose
    private HUF hUF;
    @SerializedName("ILS")
    @Expose
    private ILS iLS;
    @SerializedName("INR")
    @Expose
    private INR iNR;
    @SerializedName("JPY")
    @Expose
    private JPY jPY;
    @SerializedName("KRW")
    @Expose
    private KRW kRW;
    @SerializedName("KWD")
    @Expose
    private KWD kWD;
    @SerializedName("KZT")
    @Expose
    private KZT kZT;
    @SerializedName("LBP")
    @Expose
    private LBP lBP;
    @SerializedName("MDL")
    @Expose
    private MDL mDL;
    @SerializedName("MXN")
    @Expose
    private MXN mXN;
    @SerializedName("NOK")
    @Expose
    private NOK nOK;
    @SerializedName("NZD")
    @Expose
    private NZD nZD;
    @SerializedName("PLN")
    @Expose
    private PLN pLN;
    @SerializedName("RON")
    @Expose
    private RON rON;
    @SerializedName("SAR")
    @Expose
    private SAR sAR;
    @SerializedName("SEK")
    @Expose
    private SEK sEK;
    @SerializedName("SGD")
    @Expose
    private SGD sGD;
    @SerializedName("THB")
    @Expose
    private THB tHB;
    @SerializedName("TRY")
    @Expose
    private TRY tRY;
    @SerializedName("TWD")
    @Expose
    private TWD tWD;
    @SerializedName("VND")
    @Expose
    private VND vND;
    @SerializedName("DZD")
    @Expose
    private DZD dZD;
    @SerializedName("IQD")
    @Expose
    private IQD iQD;
    @SerializedName("KGS")
    @Expose
    private KGS kGS;
    @SerializedName("PKR")
    @Expose
    private PKR pKR;
    @SerializedName("TJS")
    @Expose
    private TJS tJS;

    public List<CurrenciesEntity> getAll(String organizationId, String date) {
        List<CurrenciesEntity> result = new ArrayList<>();

        try {
            result.add(new CurrenciesEntity("eUR"+organizationId, organizationId, "", "", "eUR", eUR.getAsk(), eUR.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("rUB"+organizationId, organizationId, "", "", "rUB", rUB.getAsk(), rUB.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("uSD"+organizationId, organizationId, "", "", "uSD", uSD.getAsk(), uSD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("aED"+organizationId, organizationId, "", "", "aED", aED.getAsk(), aED.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("aMD"+organizationId, organizationId, "", "", "aMD", aMD.getAsk(), aMD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("aUD"+organizationId, organizationId, "", "", "aUD", aUD.getAsk(), aUD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("aZN"+organizationId, organizationId, "", "", "aZN", aZN.getAsk(), aZN.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("bGN"+organizationId, organizationId, "", "", "bGN", bGN.getAsk(), bGN.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("bRL"+organizationId, organizationId, "", "", "bRL", bRL.getAsk(), bRL.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("bYN"+organizationId, organizationId, "", "", "bYN", bYN.getAsk(), bYN.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("cAD"+organizationId, organizationId, "", "", "cAD", cAD.getAsk(), cAD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("cHF"+organizationId, organizationId, "", "", "cHF", cHF.getAsk(), cHF.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("cLP"+organizationId, organizationId, "", "", "cLP", cLP.getAsk(), cLP.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("cNY"+organizationId, organizationId, "", "", "cNY", cNY.getAsk(), cNY.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("cZK"+organizationId, organizationId, "", "", "cZK", cZK.getAsk(), cZK.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("dKK"+organizationId, organizationId, "", "", "dKK", dKK.getAsk(), dKK.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("eGP"+organizationId, organizationId, "", "", "eGP", eGP.getAsk(), eGP.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("gBP"+organizationId, organizationId, "", "", "gBP", gBP.getAsk(), gBP.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("gEL"+organizationId, organizationId, "", "", "gEL", gEL.getAsk(), gEL.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("hKD"+organizationId, organizationId, "", "", "hKD", hKD.getAsk(), hKD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("hRK"+organizationId, organizationId, "", "", "hRK", hRK.getAsk(), hRK.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("hUF"+organizationId, organizationId, "", "", "hUF", hUF.getAsk(), hUF.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("iLS"+organizationId, organizationId, "", "", "iLS", iLS.getAsk(), iLS.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("iNR"+organizationId, organizationId, "", "", "iNR", iNR.getAsk(), iNR.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("jPY"+organizationId, organizationId, "", "", "jPY", jPY.getAsk(), jPY.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("kRW"+organizationId, organizationId, "", "", "kRW", kRW.getAsk(), kRW.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("kWD"+organizationId, organizationId, "", "", "kWD", kWD.getAsk(), kWD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("kZT"+organizationId, organizationId, "", "", "kZT", kZT.getAsk(), kZT.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("lBP"+organizationId, organizationId, "", "", "lBP", lBP.getAsk(), lBP.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("mDL"+organizationId, organizationId, "", "", "mDL", mDL.getAsk(), mDL.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("mXN"+organizationId, organizationId, "", "", "mXN", mXN.getAsk(), mXN.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("nOK"+organizationId, organizationId, "", "", "nOK", nOK.getAsk(), nOK.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("nZD"+organizationId, organizationId, "", "", "nZD", nZD.getAsk(), nZD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("pLN"+organizationId, organizationId, "", "", "pLN", pLN.getAsk(), pLN.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("rON"+organizationId, organizationId, "", "", "rON", rON.getAsk(), rON.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("sAR"+organizationId, organizationId, "", "", "sAR", sAR.getAsk(), sAR.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("sEK"+organizationId, organizationId, "", "", "sEK", sEK.getAsk(), sEK.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("sGD"+organizationId, organizationId, "", "", "sGD", sGD.getAsk(), sGD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("tHB"+organizationId, organizationId, "", "", "tHB", tHB.getAsk(), tHB.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("tRY"+organizationId, organizationId, "", "", "tRY", tRY.getAsk(), tRY.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("tWD"+organizationId, organizationId, "", "", "tWD", tWD.getAsk(), tWD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("vND"+organizationId, organizationId, "", "", "vND", vND.getAsk(), vND.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("dZD"+organizationId, organizationId, "", "", "dZD", dZD.getAsk(), dZD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("iQD"+organizationId, organizationId, "", "", "iQD", iQD.getAsk(), iQD.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("kGS"+organizationId, organizationId, "", "", "kGS", kGS.getAsk(), kGS.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("pKR"+organizationId, organizationId, "", "", "pKR", pKR.getAsk(), pKR.getBid(), date));
        } catch (Exception e) {/*Not find*/}     
        try {                                    
            result.add(new CurrenciesEntity("tJS"+organizationId, organizationId, "", "", "tJS", tJS.getAsk(), tJS.getBid(), date));
        } catch (Exception e) {/*Not find*/}
        return result;
    }


}