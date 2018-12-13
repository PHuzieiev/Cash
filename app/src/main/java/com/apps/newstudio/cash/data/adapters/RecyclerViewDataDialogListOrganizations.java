package com.apps.newstudio.cash.data.adapters;

import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;

public class RecyclerViewDataDialogListOrganizations {

    private boolean mIsChecked;
    private OrganizationsEntity mOrganization;
    private CurrenciesEntity mCurrency;

    /**
     * Constructor for RecyclerViewDataOrganizationOrCurrency object
     *
     * @param mIsChecked - boolean value
     * @param organization - OrganizationsEntity object
     * @param currency     - CurrenciesEntity object
     */
    public RecyclerViewDataDialogListOrganizations(boolean mIsChecked,
                                                   OrganizationsEntity organization, CurrenciesEntity currency) {
        this.mIsChecked = mIsChecked;
        mOrganization = organization;
        mCurrency = currency;
    }

    /**
     * Getter for OrganizationsEntity mOrganization
     *
     * @return OrganizationsEntity object
     */
    public OrganizationsEntity getOrganization() {
        return mOrganization;
    }

    /**
     * Getter for CurrenciesEntity mCurrency
     *
     * @return CurrenciesEntity object
     */
    public CurrenciesEntity getCurrency() {
        return mCurrency;
    }

    /**
     * Setter for boolean mIsChecked
     *
     * @param checked boolean mIsChecked
     */
    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    /**
     * Getter for boolean mIsChecked
     *
     * @return boolean value
     */
    public boolean isChecked() {
        return mIsChecked;
    }
}
