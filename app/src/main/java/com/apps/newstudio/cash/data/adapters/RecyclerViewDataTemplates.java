package com.apps.newstudio.cash.data.adapters;

public class RecyclerViewDataTemplates {

    private String mId;
    private String mDate;
    private String mCurrencyTitle;
    private String mShortCurrencyTitle;
    private String mOrganizationTitle;
    private String mAction;
    private String mDirection;
    private String mSecondCurrencyTitle;
    private String mFirstValue;
    private String mSecondValue;
    private String mStartValue;
    private String mPurchaseValue;
    private String mSaleValue;

    /**
     * Constructor for RecyclerViewDataTemplates object
     * @param id template id
     * @param date last update of currency
     * @param currencyTitle title of currency
     * @param shortCurrencyTitle short title of currency
     * @param organizationTitle title of organization
     * @param action action for convertation
     * @param direction direction of convertation
     * @param secondCurrencyTitle title for currency with short title
     * @param firstValue value of first field
     * @param secondValue value of second field
     * @param startValue input value for convertation
     * @param purchaseValue purchase value of currency
     * @param saleValue sale value of currency
     */
    public RecyclerViewDataTemplates(String id, String date, String currencyTitle, String shortCurrencyTitle,
                                     String organizationTitle, String action, String direction, String secondCurrencyTitle, String firstValue,
                                     String secondValue, String startValue, String purchaseValue, String saleValue) {
        mId = id;
        mDate = date;
        mCurrencyTitle = currencyTitle;
        mShortCurrencyTitle = shortCurrencyTitle;
        mOrganizationTitle = organizationTitle;
        mAction = action;
        mDirection = direction;
        mSecondCurrencyTitle = secondCurrencyTitle;
        mFirstValue = firstValue;
        mSecondValue = secondValue;
        mStartValue = startValue;
        mPurchaseValue = purchaseValue;
        mSaleValue = saleValue;
    }

    public String getId() {
        return mId;
    }

    public String getDate() {
        return mDate;
    }

    public String getCurrencyTitle() {
        return mCurrencyTitle;
    }

    public String getShortCurrencyTitle() {
        return mShortCurrencyTitle;
    }

    public String getOrganizationTitle() {
        return mOrganizationTitle;
    }

    public String getAction() {
        return mAction;
    }

    public String getDirection() {
        return mDirection;
    }

    public String getSecondCurrencyTitle() {
        return mSecondCurrencyTitle;
    }

    public String getFirstValue() {
        return mFirstValue;
    }

    public String getSecondValue() {
        return mSecondValue;
    }

    public String getStartValue() {
        return mStartValue;
    }

    public String getPurchaseValue() {
        return mPurchaseValue;
    }

    public String getSaleValue() {
        return mSaleValue;
    }

    public void setFirstValue(String firstValue) {
        mFirstValue = firstValue;
    }

    public void setSecondValue(String secondValue) {
        mSecondValue = secondValue;
    }
}
