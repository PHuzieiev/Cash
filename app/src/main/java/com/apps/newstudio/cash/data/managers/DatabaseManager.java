package com.apps.newstudio.cash.data.managers;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecycleViewDataAdapterDialogList;
import com.apps.newstudio.cash.data.network.RetrofitServiceRus;
import com.apps.newstudio.cash.data.network.RetrofitServiceUkr;
import com.apps.newstudio.cash.data.network.RetrofitClient;
import com.apps.newstudio.cash.data.network.models.MainModel;
import com.apps.newstudio.cash.data.network.models.Organization;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntityDao;
import com.apps.newstudio.cash.data.storage.models.DaoSession;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntityDao;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseManager {

    static final String TAG = ConstantsManager.TAG + "Database";

    private DaoSession mDaoSession;
    private OrganizationsEntityDao mOrganizationsEntityDao;
    private CurrenciesEntityDao mCurrenciesEntityDao;
    private List<OrganizationsEntity> mOrganizationsEntities;
    private List<CurrenciesEntity> mCurrenciesEntities;
    private String updateDate;
    private FinalActionSuccess mFinalActionSuccess;
    private FinalActionFailure mFinalActionFailure;


    /**
     * Constructor for DatabaseManager
     * Defines DaoSession, OrganizationsEntityDao, CurrenciesEntityDao,
     * List<OrganizationsEntity>, List<CurrenciesEntity> objects and String updateDat
     */
    public DatabaseManager() {
        mDaoSession = CashApplication.getDaoSession();
        mOrganizationsEntityDao = mDaoSession.getOrganizationsEntityDao();
        mCurrenciesEntityDao = mDaoSession.getCurrenciesEntityDao();

        mOrganizationsEntities = new ArrayList<>();
        mCurrenciesEntities = new ArrayList<>();
    }

    /**
     * Starts updating of Database, runs getMainDataFromNet() function
     *
     * @return true if don't find any Exceptions
     */
    public void updateDataBase(FinalActionSuccess finalActionSuccess, FinalActionFailure finalActionFailure) {
        mFinalActionFailure = finalActionFailure;
        mFinalActionSuccess = finalActionSuccess;
        getMainDataFromNet();
    }

    /**
     * Gets RetrofitServiceUkr, gets data using Retrofit apportunities
     * Adds data from JSON file to List<OrganizationsEntity> and List<CurrenciesEntity>
     */
    private void getMainDataFromNet() {
        RetrofitServiceUkr service = RetrofitClient.getRetrofitServiceUkr();
        Call<MainModel> call = service.getMyJSON();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<Organization> organization = response.body().getOrganizations();
                        updateDate = response.body().getDate();

                        for (Organization org : organization) {
                            mOrganizationsEntities
                                    .add(new OrganizationsEntity(org.getId(),
                                            org.getTitle(), "", org.getTitle(), org.getOrgType(),
                                            org.getPhone(), org.getLink(), updateDate));
                            mCurrenciesEntities.addAll(org.getCurrencies().getAll(org.getId(), updateDate));
                        }

                        HashMap<String, String> currenciesTitles = response.body().getCurrencies().getAll();
                        for (int i = 0; i < mCurrenciesEntities.size(); i++) {
                            if (currenciesTitles.containsKey(mCurrenciesEntities.get(i).getShortTitle())) {
                                String title = currenciesTitles.get(mCurrenciesEntities.get(i).getShortTitle());
                                mCurrenciesEntities.get(i).setTitleUkr(title);
                            }
                        }

                        updateMainDataFromNet();
                    } catch (Exception e) {

                    }
                } else {
                    try {
                        if (mFinalActionFailure != null) {
                            mFinalActionFailure.finalFunctionFailure();
                        }
                    } catch (Exception ex) {

                    }
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                try {
                    if (mFinalActionFailure != null) {
                        mFinalActionFailure.finalFunctionFailure();
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    /**
     * Updates data in  List<OrganizationsEntity> and List<CurrenciesEntity> objects
     * using RetrofitServiceRus
     */
    private void updateMainDataFromNet() {
        RetrofitServiceRus service = RetrofitClient.getRetrofitServiceRus();
        Call<MainModel> call = service.getMyJSON();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<Organization> organization = response.body().getOrganizations();
                        for (Organization org : organization) {
                            for (int i = 0; i < mOrganizationsEntities.size(); i++) {
                                if (mOrganizationsEntities.get(i).getId().equals(org.getId())) {
                                    mOrganizationsEntities.get(i).setTitleRus(org.getTitle());
                                }
                            }
                        }

                        HashMap<String, String> currenciesTitles = response.body().getCurrencies().getAll();
                        for (int i = 0; i < mCurrenciesEntities.size(); i++) {
                            if (currenciesTitles.containsKey(mCurrenciesEntities.get(i).getShortTitle())) {
                                String title = currenciesTitles.get(mCurrenciesEntities.get(i).getShortTitle());
                                mCurrenciesEntities.get(i).setTitleRus(title);
                                switch (mCurrenciesEntities.get(i).getShortTitle()){
                                    case "aED":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.aed_eng));
                                        break;
                                    case "aMD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.amd_eng));
                                        break;
                                    case "aUD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.aud_eng));
                                        break;
                                    case "aZN":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.azn_eng));
                                        break;
                                    case "bGN":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.bgn_eng));
                                        break;
                                    case "bRL":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.brl_eng));
                                        break;
                                    case "bYN":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.byn_eng));
                                        break;
                                    case "cAD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.cad_eng));
                                        break;
                                    case "cHF":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.chf_eng));
                                        break;
                                    case "cLP":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.clp_eng));
                                        break;
                                    case "cNY":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.cny_eng));
                                        break;
                                    case "cZK":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.czk_eng));
                                        break;
                                    case "dKK":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.dkk_eng));
                                        break;
                                    case "dZD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.dzd_eng));
                                        break;
                                    case "eGP":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.egp_eng));
                                        break;
                                    case "eUR":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.eur_eng));
                                        break;
                                    case "gBP":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.gbp_eng));
                                        break;
                                    case "gEL":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.gel_eng));
                                        break;
                                    case "hKD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.hkd_eng));
                                        break;
                                    case "hRK":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.hrk_eng));
                                        break;
                                    case "hUF":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.huf_eng));
                                        break;
                                    case "iLS":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.ils_eng));
                                        break;
                                    case "iNR":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.inr_eng));
                                        break;
                                    case "iQD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.iqd_eng));
                                        break;
                                    case "jPY":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.jpy_eng));
                                        break;
                                    case "kGS":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.kgs_eng));
                                        break;
                                    case "kRW":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.krw_eng));
                                        break;
                                    case "kWD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.kwd_eng));
                                        break;
                                    case "kZT":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.kzt_eng));
                                        break;
                                    case "lBP":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.lbp_eng));
                                        break;
                                    case "mDL":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.mdl_eng));
                                        break;
                                    case "mXN":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.mxn_eng));
                                        break;
                                    case "nOK":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.nok_eng));
                                        break;
                                    case "nZD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.nzd_eng));
                                        break;
                                    case "pKR":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.pkr_eng));
                                        break;
                                    case "pLN":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.pln_eng));
                                        break;
                                    case "rON":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.ron_eng));
                                        break;
                                    case "rUB":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.rub_eng));
                                        break;
                                    case "sAR":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.sar_eng));
                                        break;
                                    case "sEK":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.sek_eng));
                                        break;
                                    case "sGD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.sgd_eng));
                                        break;
                                    case "tHB":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.thb_eng));
                                        break;
                                    case "tJS":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.tjs_eng));
                                        break;
                                    case "tRY":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.try_eng));
                                        break;
                                    case "tWD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.twd_eng));
                                        break;
                                    case "uSD":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.usd_eng));
                                        break;
                                    case "vND":
                                        mCurrenciesEntities.get(i)
                                                .setTitleEng(CashApplication.getContext().getString(R.string.vnd_eng));
                                        break;
                                }
                            }
                        }

                        mCurrenciesEntityDao.insertOrReplaceInTx(mCurrenciesEntities);
                        mOrganizationsEntityDao.insertOrReplaceInTx(mOrganizationsEntities);
                        DataManager.getInstance().getPreferenceManager()
                                .saveString(ConstantsManager.LAST_UPDATE_DATE, response.body().getDate());

                        try {
                            if (mFinalActionSuccess != null) {
                                mFinalActionSuccess.finalFunctionSuccess();
                            }
                        } catch (Exception e) {

                        }

                    } catch (Exception e) {
                        //Error
                    }
                } else {
                    try {
                        if (mFinalActionFailure != null) {
                            mFinalActionFailure.finalFunctionFailure();
                        }
                    } catch (Exception ex) {

                    }
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

                try {
                    if (mFinalActionFailure != null) {
                        mFinalActionFailure.finalFunctionFailure();
                    }
                } catch (Exception ex) {

                }
            }
        });
    }


    /**
     * Gets data from db for list of OrganizationsFragment object, uses filter and search parameters for correct result
     * @return List of OrganizationsEntities object
     */

    public List<OrganizationsEntity> getDataForFragmentOrganization() {
        List<OrganizationsEntity> list;
        String searchParameter = DataManager.getInstance().getPreferenceManager().getOrganizationsSearchParameter().toUpperCase();
        String filterParameter = DataManager.getInstance().getPreferenceManager().getOrganizationsFilterParameter();
        Property property = OrganizationsEntityDao.Properties.TitleEng;
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                property = OrganizationsEntityDao.Properties.TitleEng;
                break;
            case ConstantsManager.LANGUAGE_RUS:
                property = OrganizationsEntityDao.Properties.TitleRus;
                break;
            case ConstantsManager.LANGUAGE_UKR:
                property = OrganizationsEntityDao.Properties.TitleUkr;
                break;
        }
        if (filterParameter.equals("")) {
            list = mDaoSession.queryBuilder(OrganizationsEntity.class)
                    .orderAsc(property)
                    .list();
        } else {
            list = mDaoSession.queryBuilder(OrganizationsEntity.class)
                    .where(new WhereCondition
                            .StringCondition("ID IN (SELECT ORGANIZATION_ID FROM CURRENCIES WHERE TITLE_ENG IN " +
                            filterParameter + ")"))
                    .orderAsc(property)
                    .list();
        }
        if (!searchParameter.equals("")) {
            List<OrganizationsEntity> list_tmp = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {

                switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
                    case ConstantsManager.LANGUAGE_ENG:
                        if (list.get(i).getTitleEng().toUpperCase().contains(searchParameter)) {
                            list_tmp.add(list.get(i));
                        }
                        break;
                    case ConstantsManager.LANGUAGE_RUS:
                        if (list.get(i).getTitleRus().toUpperCase().contains(searchParameter)) {
                            list_tmp.add(list.get(i));
                        }
                        break;
                    case ConstantsManager.LANGUAGE_UKR:
                        if (list.get(i).getTitleUkr().toUpperCase().contains(searchParameter)) {
                            list_tmp.add(list.get(i));
                        }
                        break;
                }
            }
            list = list_tmp;
        }
        return list;
    }

    /**
     * Checks db
     * @return true - db is empty, false - db is full of data
     */
    public boolean isEmptyDatabase() {
        List<OrganizationsEntity> list = mDaoSession.queryBuilder(OrganizationsEntity.class)
                .orderAsc(OrganizationsEntityDao.Properties.TitleUkr)
                .limit(1)
                .list();
        if (list.size() != 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Interface which containes final function for final step in loading data from Internet to db
     */
    public interface FinalActionSuccess {
        public void finalFunctionSuccess();
    }

    /**
     * Interface which containes final function if loading data from Internet to db  finished with error
     */
    public interface FinalActionFailure {
        public void finalFunctionFailure();
    }

    /**
     * Gets data about all titles of currencies from db
     * @return List of RecycleViewDataAdapterDialogList objects
     */
    public List<RecycleViewDataAdapterDialogList> getDataForListDialogCurrencies() {
        List<RecycleViewDataAdapterDialogList> list = new ArrayList<>();
        list.add(new RecycleViewDataAdapterDialogList(true,
                CashApplication.getContext().getString(R.string.dialog_list_filter_first_item_ukr),
                CashApplication.getContext().getString(R.string.dialog_list_filter_first_item_rus),
                CashApplication.getContext().getString(R.string.dialog_list_filter_first_item_eng)));
        HashMap<String, String> map = new HashMap<>();
        List<CurrenciesEntity> listCur = mDaoSession.queryBuilder(CurrenciesEntity.class)
                .list();
        for (CurrenciesEntity c : listCur) {
            if (!map.containsKey(c.getTitleEng())) {
                map.put(c.getTitleEng(), ConstantsManager.EMPTY_STRING_VALUE);
                list.add(new RecycleViewDataAdapterDialogList(false,
                        c.getTitleUkr(), c.getTitleRus(), c.getTitleEng()));
            }
        }
        return list;
    }

    /**
     * Gets data of some organization, uses id parameter for search
     * @param id - organization id in db
     * @return OrganizationsEntity object
     */
    public OrganizationsEntity getOrganizationData(String id){
        return mDaoSession.queryBuilder(OrganizationsEntity.class)
                .where(new WhereCondition
                        .StringCondition("ID = \""+id+"\""))
                .unique();
    }
}
