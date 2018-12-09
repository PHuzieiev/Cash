package com.apps.newstudio.cash.data.adapters;

import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;

public class RecyclerViewDataOrganizationOrCurrency {

    private OrganizationsEntity mOrganization;
    private CurrenciesEntity mCurrency;

    /**
     * Constructor for RecyclerViewDataOrganizationOrCurrency object
     * @param organization - OrganizationsEntity object
     * @param currency - CurrenciesEntity object
     */
    public RecyclerViewDataOrganizationOrCurrency(OrganizationsEntity organization, CurrenciesEntity currency) {
        mOrganization = organization;
        mCurrency = currency;
    }

    /**
     * Getter for OrganizationsEntity mOrganization
     * @return OrganizationsEntity object
     */
    public OrganizationsEntity getOrganization() {
        return mOrganization;
    }

    /**
     * Getter for CurrenciesEntity mCurrency
     * @return CurrenciesEntity object
     */
    public CurrenciesEntity getCurrency() {
        return mCurrency;
    }
}
