package com.apps.newstudio.cash.data.managers;

import android.content.Context;
import android.util.Log;

import com.apps.newstudio.cash.data.network.RetrofitServiceRus;
import com.apps.newstudio.cash.data.network.RetrofitServiceUkr;
import com.apps.newstudio.cash.data.network.RetrofiteClient;
import com.apps.newstudio.cash.data.network.models.MainModel;
import com.apps.newstudio.cash.data.network.models.Organization;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntityDao;
import com.apps.newstudio.cash.data.storage.models.DaoSession;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntityDao;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;
import com.apps.newstudio.cash.utils.InternetConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

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
        RetrofitServiceUkr service = RetrofiteClient.getRetrofitServiceUkr();
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
                            if (currenciesTitles.containsKey(mCurrenciesEntities.get(i).getTitleEng())) {
                                String title = currenciesTitles.get(mCurrenciesEntities.get(i).getTitleEng());
                                mCurrenciesEntities.get(i).setTitleUkr(title);
                            }
                        }

                        updateMainDataFromNet();
                    } catch (Exception e) {

                    }
                }else{
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
        RetrofitServiceRus service = RetrofiteClient.getRetrofitServiceRus();
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
                            if (currenciesTitles.containsKey(mCurrenciesEntities.get(i).getTitleEng())) {
                                String title = currenciesTitles.get(mCurrenciesEntities.get(i).getTitleEng());
                                mCurrenciesEntities.get(i).setTitleRus(title);
                            }
                        }

                        mCurrenciesEntityDao.insertOrReplaceInTx(mCurrenciesEntities);
                        mOrganizationsEntityDao.insertOrReplaceInTx(mOrganizationsEntities);
                        DataManager.getInstance().getPreferenceManager()
                                .saveString(ConstantsManager.LAST_UPDATE_DATE,response.body().getDate());

                        try {
                            if (mFinalActionSuccess != null) {
                                mFinalActionSuccess.finalFunctionSuccess();
                            }
                        } catch (Exception e) {

                        }

                    } catch (Exception e) {
                        //Error
                    }
                }else{
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

    /*public void test() {
        List<OrganizationsEntity> list = mDaoSession.queryBuilder(OrganizationsEntity.class)
                .orderAsc(OrganizationsEntityDao.Properties.TitleUkr)
                .limit(2)
                .list();

        Log.d(TAG, list.size() + "");

        for (OrganizationsEntity org : list) {
            List<CurrenciesEntity> cur=org.getCurrencies();
            Log.d(TAG, org.getId()+" "+org.getTitleUkr()+" "+org.getOrgType()+" "+org.getLink()+" "+org.getPhone()+" "+org.getDate());
            for(CurrenciesEntity c : cur){
                Log.d(TAG, c.getTitleUkr()+" "+c.getTitleRus()+" "+c.getTitleEng().toUpperCase()+" "+c.getAsk()+" "+c.getBid());
            }
            Log.d(TAG,"-------------------");
        }
    }
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


    public interface FinalActionSuccess {
        public void finalFunctionSuccess();
    }

    public interface FinalActionFailure {
        public void finalFunctionFailure();
    }
}
