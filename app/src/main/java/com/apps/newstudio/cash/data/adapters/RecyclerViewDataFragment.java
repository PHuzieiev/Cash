package com.apps.newstudio.cash.data.adapters;

import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;

import java.util.Currency;
import java.util.List;

public class RecyclerViewDataFragment {

    private CurrenciesEntity mCurrency;
    private List<OrganizationsEntity> mOrganizations;


    /**
     * Constructor for RecyclerViewDataFragment object
     * @param currency CurrenciesEntity object for mCurrency
     * @param organizations OrganizationsEntity List object for mOrganizations
     */
    public RecyclerViewDataFragment(CurrenciesEntity currency, List<OrganizationsEntity> organizations) {
        mCurrency = currency;
        mOrganizations = organizations;
    }

    /**
     * Getter for CurrenciesEntity object mCurrency
     * @return mCurrency object
     */
    public CurrenciesEntity getCurrency() {
        return mCurrency;
    }

    /**
     * Getter for OrganizationsEntity List object mOrganizations
     * @return mOrganizations object
     */
    public List<OrganizationsEntity> getOrganizations() {
        return mOrganizations;
    }
}
